package com.sample.fileupload;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxHelper;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WProgressBar;
import com.github.bordertech.wcomponents.WText;
import com.sample.fileupload.tasks.TaskFuture;
import com.sample.fileupload.tasks.TaskManager;
import com.sample.fileupload.tasks.TaskManagerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This panel is used to load data via a threaded service call and polling AJAX.
 * <p>
 * Expected {@link #setRecordId(Object)} to be set with the appropriate id to be loaded.
 * </p>
 * <p>
 * The successful service response will be set as the bean available to the panel. The content of the panel will only be
 * displayed if the service call was successful. If the service call fails, then the error message will be displayed
 * along with a retry button.
 * </p>
 * <p>
 * Any caching of service calls is expected to be handled at the services layer.
 * </p>
 *
 * @param <T> the service response type
 * @param <R> the record id type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractPollingPanel<T, R> extends WPanel {

	/**
	 * Status of the Panel and if the service has been called successfully.
	 */
	public enum PanelStatus {
		/**
		 * Service not called yet.
		 */
		NOT_STARTED,
		/**
		 * Service being called.
		 */
		PROCESSING,
		/**
		 * Service call had an error.
		 */
		ERROR,
		/**
		 * Service called successfully.
		 */
		COMPLETE
	}

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(AbstractPollingPanel.class);

	/**
	 * The TaskManager implementation.
	 */
	private static final TaskManager TASK_MANAGER = TaskManagerFactory.getInstance();

	/**
	 * Start polling manually button.
	 */
	private final WButton startButton = new WButton("Start");

	/**
	 * Root container that can be reset to refresh the panel.
	 */
	private final WContainer root = new WContainer() {
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				if (!isManualStart()) {
					doStartLoading();
				}
				setInitialised(true);
			}
		}
	};

	/**
	 * Flag if start polling manually with the start button.
	 */
	private final boolean manualStart;

	/**
	 * Messages.
	 */
	private final WMessages messages = new WMessages(true);

	/**
	 * Retry load.
	 */
	private final WButton retryButton = new WButton("Retry");

	/**
	 * Container to hold the panel detail.
	 */
	private final WContainer content = new WContainer() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				doInitContent(request);
				setInitialised(true);
			}
		}
	};

	/**
	 * The container that holds the AJAX poller.
	 */
	private final WContainer pollingContainer = new WContainer();

	private final WText pollingText = new WText() {
		@Override
		public String getText() {
			return getPollingText();
		}
	};

	private final WProgressBar pollingProgressBar = new WProgressBar(100);

	private final WText progressBarScript = new WText() {
		@Override
		protected void preparePaintComponent(final Request request) {
			if (!isInitialised()) {
				setText(buildProgressBarScript());
				setInitialised(true);
			}
		}
	};

	/**
	 * The container that holds the AJAX poller.
	 */
	private final WPanel ajaxPollingPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return true;
		}
	};

	/**
	 * AJAX poller.
	 */
	private final WAjaxControl ajaxPolling = new WAjaxControl(null, ajaxPollingPanel);

	/**
	 * AJAX control to reload whole panel.
	 */
	private final WAjaxControl ajaxReload = new WAjaxControl(null, this) {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			// Reloading
			if (AjaxHelper.isCurrentAjaxTrigger(this)) {
				// Stop polling
				pollingContainer.setVisible(false);
			}
		}
	};

	/**
	 * Construct polling panel.
	 *
	 * @param delay the AJAX polling delay
	 */
	public AbstractPollingPanel(final int delay) {
		this(null, delay, false);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param context the naming context
	 * @param delay the AJAX polling delay
	 */
	public AbstractPollingPanel(final String context, final int delay) {
		this(context, delay, false);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param context the naming context
	 * @param delay the AJAX polling delay
	 * @param manualStart true if start polling with manual start button action
	 */
	public AbstractPollingPanel(final String context, final int delay, final boolean manualStart) {
		this.manualStart = manualStart;

		// Start button sits above the root element so it does not get reset when retrying or refreshing the content
		add(startButton);
		startButton.setVisible(manualStart);

		// Dont allow the search for the bean to go above this panel
		root.setSearchAncestors(false);
		add(root);

		// AJAX polling details
		ajaxPolling.setDelay(delay);
		ajaxPolling.setLoadOnce(true);
		ajaxReload.setLoadOnce(true);
		ajaxReload.setDelay(10);
		progressBarScript.setEncodeText(false);

		// AJAX Container
		root.add(pollingContainer);
		pollingContainer.add(pollingText);
		pollingContainer.add(pollingProgressBar);
		pollingContainer.add(progressBarScript);
		pollingContainer.add(ajaxPollingPanel);
		ajaxPollingPanel.add(ajaxPolling);
		ajaxPollingPanel.add(ajaxReload);

		messages.setMargin(new Margin(0, 0, 3, 0));
		root.add(messages);
		root.add(retryButton);
		root.add(content);

		// Manual Start load
		startButton.setAjaxTarget(this);
		startButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doStartLoading();
				startButton.setVisible(false);
			}
		});

		// Retry load
		retryButton.setAjaxTarget(this);
		retryButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doRetry();
			}
		});

		// Set default visibility
		retryButton.setVisible(false);
		pollingContainer.setVisible(false);
		content.setVisible(false);
		ajaxReload.setVisible(false);

		// Context
		if (context != null) {
			setIdName(context);
			setNamingContext(true);
			// IDs
			messages.setIdName("msgs");
			retryButton.setIdName("btnRetry");
			ajaxPollingPanel.setIdName("pollingPanel");
			ajaxPolling.setIdName("ajaxPoll");
			ajaxReload.setIdName("ajaxReload");
			startButton.setIdName("btnStart");
		}

	}

	/**
	 * The container used to hold the panel detail.
	 *
	 * @return the panel content holder
	 */
	public WContainer getContent() {
		return content;
	}

	/**
	 * The messages for the panel.
	 *
	 * @return the messages for the panel
	 */
	public WMessages getMessages() {
		return messages;
	}

	/**
	 * @param text the text displayed while polling
	 */
	public void setPollingText(final String text) {
		getOrCreateComponentModel().pollingText = text;
	}

	/**
	 * @return the text displayed while polling
	 */
	public String getPollingText() {
		return getComponentModel().pollingText;
	}

	/**
	 * @param recordId the id for the record
	 */
	public void setRecordId(final R recordId) {
		getOrCreateComponentModel().recordId = recordId;
	}

	/**
	 * @return the id for the record
	 */
	public R getRecordId() {
		return getComponentModel().recordId;
	}

	/**
	 * @param panelStatus the panel status
	 */
	protected void setPanelStatus(final PanelStatus panelStatus) {
		getOrCreateComponentModel().panelStatus = panelStatus;
	}

	/**
	 * @return the panel status
	 */
	public PanelStatus getPanelStatus() {
		return getComponentModel().panelStatus;
	}

	/**
	 * @return the retry button.
	 */
	public WButton getRetryButton() {
		return retryButton;
	}

	/**
	 * @return true if manual start via the start button
	 */
	public boolean isManualStart() {
		return manualStart;
	}

	/**
	 * @return the start button
	 */
	public WButton getStartButton() {
		return startButton;
	}

	/**
	 * @return the service response, or null if not called successfully
	 */
	public T getServiceResponse() {
		return (T) root.getBean();
	}

	/**
	 * Start loading data.
	 */
	public void doStartLoading() {

		// Check not started
		if (getPanelStatus() != PanelStatus.NOT_STARTED) {
			return;
		}

		// Check we have a record id
		final R recordId = getRecordId();
		if (recordId == null) {
			getMessages().error("No id set for service call.");
			return;
		}

		handleStartServiceCall(recordId);
	}

	/**
	 * Retry the service call.
	 */
	public void doRetry() {
		doRefreshContent();
		if (isManualStart()) {
			doStartLoading();
		}
	}

	/**
	 * Reset to start load again.
	 */
	public void doRefreshContent() {
		R recordId = getRecordId();
		if (recordId == null) {
			return;
		}
		handleClearServiceCache();
		root.reset();
		setPanelStatus(PanelStatus.NOT_STARTED);
		clearFuture();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		// Check if Polling
		if (isPollingTarget()) {
			checkForResult();
		}
	}

	/**
	 * Called when initializing the content of the panel after a response from the service has been received.
	 *
	 * @param request the request being processed
	 */
	protected void doInitContent(final Request request) {
		// Do nothing
	}

	/**
	 * Do the actual service call.
	 * <p>
	 * As this method is called by a different thread, do not put any logic or functionality that needs the user
	 * context.
	 * </p>
	 *
	 * @param recordId the id for the record
	 * @return the response
	 * @throws PollingServiceException a service exception occurred
	 */
	protected abstract T doServiceCall(final R recordId) throws PollingServiceException;

	/**
	 * Clear the service level cache if necessary.
	 */
	protected void handleClearServiceCache() {
		// Do nothing
	}

	/**
	 * @param recordId the id for the record
	 */
	protected void handleStartServiceCall(final R recordId) {
		// Start service call
		try {
			handleAsyncServiceCall(recordId);
		} catch (Exception e) {
			handleResult(e);
			return;
		}

		// Start polling
		handleStartPolling();
	}

	/**
	 * Start polling.
	 */
	protected void handleStartPolling() {
		// Start AJAX polling
		setPanelStatus(PanelStatus.PROCESSING);
		pollingContainer.setVisible(true);
		ajaxPolling.reset();
		ajaxReload.reset();
	}

	/**
	 * Stop polling.
	 */
	protected void handleStopPolling() {
		ajaxPolling.setVisible(false);
		ajaxReload.setVisible(true);
	}

	/**
	 * Handle the result from the service call.
	 *
	 * @param result the service response
	 */
	protected void handleResult(final Object result) {
		// Exception message
		if (result instanceof Exception) {
			Exception excp = (Exception) result;
			extractExceptionMessages("Error loading data. ", getMessages(), excp);
			retryButton.setVisible(true);
			// Log error
			LOG.error("Error loading data. " + excp.getMessage());
			// Status
			setPanelStatus(PanelStatus.ERROR);
		} else {
			// Result
			T response = (T) result;
			// Handle response
			handleSuccessfulServiceResponse(response);
			content.setVisible(true);
			// Status
			setPanelStatus(PanelStatus.COMPLETE);
		}
	}

	/**
	 * Handle the response. Default behaviour is to set the response as the bean for the content.
	 *
	 * @param response the service response
	 */
	protected void handleSuccessfulServiceResponse(final T response) {
		// Set the response as the bean
		root.setBean(response);
	}

	/**
	 * Extract the exception messages from the service exception.
	 *
	 * @param prefix the message prefix
	 * @param messages the message component
	 * @param exception the exception to setup messages
	 */
	protected void extractExceptionMessages(final String prefix, final WMessages messages, final Exception exception) {
		messages.error(prefix + exception.getMessage());
	}

	/**
	 * @return true if polling and is the current AJAX trigger.
	 */
	protected boolean isPollingTarget() {
		return isPolling() && AjaxHelper.isCurrentAjaxTrigger(ajaxPolling);
	}

	/**
	 * @return true if currently polling
	 */
	protected boolean isPolling() {
		return pollingContainer.isVisible();
	}

	/**
	 * Check the future holds the result.
	 */
	protected void checkForResult() {

		// Get the Future for the service call
		TaskFuture<ServiceResultHolder> future = getFuture();
		if (future == null) {
			// Stop polling as future must have expired
			handleResult(new PollingServiceException("Future has expired so service result not available"));
			handleStopPolling();
			return;
		}

		// Check if finished processing
		if (!future.isDone()) {
			return;
		}

		// Extract the result from the future
		Object result;
		try {
			ServiceResultHolder holder = future.get();
			result = holder.getResult();
		} catch (Exception e) {
			result = e;
		}
		// Clear future
		clearFuture();
		handleResult(result);
		// Stop polling
		handleStopPolling();
	}

	/**
	 * Handle the service call.
	 *
	 * @param recordId the record id
	 * @throws PollingServiceException the service exception
	 */
	protected void handleAsyncServiceCall(final R recordId) throws PollingServiceException {

		clearFuture();

		final ServiceResultHolder result = new ServiceResultHolder();
		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					T resp = doServiceCall(recordId);
					result.setResult(resp);
				} catch (Exception e) {
					PollingServiceException excp = new PollingServiceException(e.getMessage(), e);
					result.setResult(excp);
				}
			}
		};
		try {
			TaskFuture future = TASK_MANAGER.submit(task, result);
			// Save the future
			setFuture(future);
		} catch (Exception e) {
			throw new PollingServiceException("Could not start thread to call service. " + e.getMessage(), e);
		}
	}

	/**
	 * @return the service call future object
	 */
	protected TaskFuture<ServiceResultHolder> getFuture() {
		return getComponentModel().future;
	}

	/**
	 * @param future the service future to save
	 */
	protected void setFuture(final TaskFuture<ServiceResultHolder> future) {
		getOrCreateComponentModel().future = future;
	}

	/**
	 * Cancel and clear the future if there is one already running.
	 */
	protected void clearFuture() {
		TaskFuture future = getFuture();
		if (future != null) {
			if (!future.isDone() && !future.isCancelled()) {
				future.cancel(true);
			}
			getOrCreateComponentModel().future = null;
		}
	}

	/**
	 * @return the script to step the progress bar
	 */
	protected String buildProgressBarScript() {
		StringBuilder script = new StringBuilder();
		script.append("<script type='text/javascript'>");
		script.append("function startStepProgressBar() {");
		script.append("  var elem = document.getElementById('").append(pollingProgressBar.getId()).append("');");
		script.append("  window.setInterval(stepProgressBar, 250, elem);");
		script.append("}");
		script.append("function stepProgressBar(bar) {");
		script.append("   if (bar.value > 99) { bar.value = 0; }");
		script.append("   bar.value++;");
		script.append("}");
		script.append("window.setTimeout(startStepProgressBar, 1000);");
		script.append("</script>");
		return script.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingPanelModel<R> newComponentModel() {
		return new PollingPanelModel<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingPanelModel<R> getOrCreateComponentModel() {
		return (PollingPanelModel<R>) super.getOrCreateComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingPanelModel<R> getComponentModel() {
		return (PollingPanelModel<R>) super.getComponentModel();
	}

	/**
	 * This model holds the state information.
	 *
	 * @param <R> the record id type
	 */
	public static class PollingPanelModel<R> extends PanelModel {

		/**
		 * Record id.
		 */
		private R recordId;

		/**
		 * Panel status.
		 */
		private PanelStatus panelStatus = PanelStatus.NOT_STARTED;

		/**
		 * Polling text.
		 */
		private String pollingText = "Loading....";

		/**
		 * Holds the reference to the future for the service call.
		 */
		private TaskFuture<ServiceResultHolder> future;
	}

	/**
	 * Service result holder to use in the Future.
	 */
	public static class ServiceResultHolder {

		private Object result;

		/**
		 * @return the service result
		 */
		public Object getResult() {
			return result;
		}

		/**
		 * @param result the service result
		 */
		public void setResult(final Object result) {
			this.result = result;
		}

	}

	/**
	 * Exception processing polling service.
	 */
	public static class PollingServiceException extends Exception {

		/**
		 * @param msg exception message
		 */
		public PollingServiceException(final String msg) {
			super(msg);
		}

		/**
		 * @param msg exception message
		 * @param throwable original exception
		 */
		public PollingServiceException(final String msg, final Throwable throwable) {
			super(msg, throwable);
		}

	}

}
