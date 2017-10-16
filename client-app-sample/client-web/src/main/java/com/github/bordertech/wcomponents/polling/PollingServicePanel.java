package com.github.bordertech.wcomponents.polling;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.task.TaskFuture;
import com.github.bordertech.wcomponents.task.TaskManager;
import com.github.bordertech.wcomponents.task.TaskManagerFactory;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This panel is used to load data via a threaded polling action and polling AJAX.
 * <p>
 * Expected {@link #setServiceCriteria(Object)} to be set with the appropriate id to be loaded.
 * </p>
 * <p>
 * The successful polling result will be set as the bean available to the panel. The content of the panel will only be
 * displayed if the polling action was successful. If the polling action fails, then the error message will be displayed
 * along with a retry button.
 * </p>
 * <p>
 * Any caching of polling actions is expected to be handled at the lower level (eg service layer caching).
 * </p>
 *
 * @param <S> the polling criteria type
 * @param <T> the polling result type
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class PollingServicePanel<S, T> extends PollingPanel implements PollableService<S, T> {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(PollingServicePanel.class);

	/**
	 * The TaskManager implementation.
	 */
	private static final TaskManager TASK_MANAGER = TaskManagerFactory.getInstance();

	/**
	 * Start polling manually button.
	 */
	private final WButton startButton = new WButton("Start");

	private final WDiv contentResultHolder = new WDiv() {
		@Override
		protected void preparePaintComponent(final Request request) {
			super.preparePaintComponent(request);
			if (!isInitialised()) {
				handleInitResultContent(request);
				setInitialised(true);
			}
		}
	};

	/**
	 * Messages.
	 */
	private final WMessages messages = new WMessages(true);

	/**
	 * Retry load.
	 */
	private final WButton retryButton = new WButton("Retry");

	/**
	 * Default constructor.
	 */
	public PollingServicePanel() {
		this(174);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param delay the AJAX polling delay
	 */
	public PollingServicePanel(final int delay) {
		this(delay, false);
	}

	/**
	 * Construct polling panel.
	 *
	 * @param delay the AJAX polling delay
	 * @param manualStart true if start polling with manual start button action
	 */
	public PollingServicePanel(final int delay, final boolean manualStart) {
		super(delay);

		WDiv holder = getHolder();

		messages.setMargin(new Margin(0, 0, 3, 0));
		holder.add(messages);
		holder.add(retryButton);
		holder.add(startButton);
		holder.add(contentResultHolder);

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
		contentResultHolder.setVisible(false);
	}

	public final WDiv getContentResultHolder() {
		return contentResultHolder;
	}

	/**
	 * @return true if start polling manually with the start button.
	 */
	@Override
	public boolean isManualStart() {
		return getComponentModel().manualStart;
	}

	/**
	 *
	 * @param manualStart true if start polling manually with the start button
	 */
	@Override
	public void setManualStart(final boolean manualStart) {
		getOrCreateComponentModel().manualStart = manualStart;
	}

	/**
	 * @param criteria the id for the record
	 */
	@Override
	public void setServiceCriteria(final S criteria) {
		getOrCreateComponentModel().criteria = criteria;
	}

	/**
	 * @return the id for the record
	 */
	@Override
	public S getServiceCriteria() {
		return getComponentModel().criteria;
	}

	/**
	 * Initiliase the result content.
	 *
	 * @param request the request being processed
	 */
	protected void handleInitResultContent(final Request request) {
		// Do Nothing
	}

	/**
	 * The messages for the panel.
	 *
	 * @return the messages for the panel
	 */
	protected WMessages getMessages() {
		return messages;
	}

	protected void handleErrorMessage(final String msg) {
		handleErrorMessage(Arrays.asList(msg));
	}

	protected void handleErrorMessage(final List<String> msgs) {
		for (String msg : msgs) {
			getMessages().error(msg);
		}
	}

	/**
	 * @return the retry button.
	 */
	@Override
	public WButton getRetryButton() {
		return retryButton;
	}

	/**
	 * @return the start button
	 */
	@Override
	public WButton getStartButton() {
		return startButton;
	}

	/**
	 * @return the polling result, or null if not processed successfully yet
	 */
	@Override
	public T getServiceResult() {
		return (T) getHolder().getBean();
	}

	/**
	 * Start loading data.
	 */
	@Override
	public void doStartLoading() {

		if (!isManualStart()) {
			startButton.setVisible(false);
		}

		// Check not started
		if (getPollingStatus() != PollingStatus.NOT_STARTED) {
			return;
		}

		// Check we have a criteria
		final S criteria = getServiceCriteria();
		if (criteria == null) {
			handleErrorMessage("No criteria set for polling action.");
			return;
		}

		// Check cache
		Object result = handleCheckServiceCache(criteria);
		if (result != null) {
			handleResult(result);
			return;
		}

		// Start Async call
		handleStartServiceAction(criteria);

	}

	/**
	 * Retry the polling action.
	 */
	@Override
	public void doRetry() {
		doRefreshContent();
		if (isManualStart()) {
			doStartLoading();
		}
	}

	/**
	 * Reset to start load again.
	 */
	@Override
	public void doRefreshContent() {
		S criteria = getServiceCriteria();
		if (criteria == null) {
			return;
		}
		handleClearServiceCache(criteria);
		getHolder().reset();
		setPollingStatus(PollingStatus.NOT_STARTED);
		clearFuture();
	}

	@Override
	public void doManuallyLoadResult(final S criteria, final T result) {
		if (result == null || criteria == null) {
			return;
		}
		getHolder().reset();
		startButton.setVisible(false);
		setServiceCriteria(criteria);
		handleResult(result);
	}

	@Override
	protected void handleInitContent(final Request request) {
		super.handleInitContent(request);
		if (!isManualStart()) {
			doStartLoading();
		}
	}

	/**
	 * A hook to be able to cache the result.
	 *
	 * @param criteria the service criteria
	 * @param result the service result
	 */
	protected void handleCacheServiceResult(final S criteria, final Object result) {
		// Do nothing
	}

	/**
	 * A hook to be able to check the service cache for the result before starting an async call.
	 *
	 * @param criteria the service criteria
	 * @return null or the service result
	 */
	protected Object handleCheckServiceCache(final S criteria) {
		return null;
	}

	/**
	 * Clear the cache if necessary (eg Service Layer).
	 *
	 * @param criteria the service criteria
	 */
	protected void handleClearServiceCache(final S criteria) {
		// Do nothing
	}

	/**
	 * @param criteria the criteria for the polling action
	 */
	protected void handleStartServiceAction(final S criteria) {
		// Start polling action
		try {
			handleAsyncServiceAction(criteria);
		} catch (Exception e) {
			handleResult(e);
			return;
		}

		// Start polling
		doStartPolling();
	}

	/**
	 * Stopped polling and view has been reloaded.
	 */
	@Override
	protected void handleStoppedPolling() {
		// Get the Future for the polling action
		TaskFuture<ResultHolder> future = getFuture();
		if (future == null) {
			// Stop polling as future must have expired
			handleResult(new PollingException("Future has expired so polling result not available"));
			return;
		}

		// Extract the result from the future
		Object result;
		try {
			ResultHolder holder = future.get();
			result = holder.getResult();
		} catch (Exception e) {
			result = e;
		}
		// Clear future
		clearFuture();
		handleResult(result);
	}

	/**
	 * Handle the result from the polling action.
	 *
	 * @param pollingResult the polling action result
	 * @return the polling status
	 */
	protected PollingStatus handleResult(final Object pollingResult) {
		// Cache Result
		handleCacheServiceResult(getServiceCriteria(), pollingResult);
		// Exception message
		final PollingStatus status;
		if (pollingResult instanceof Exception) {
			Exception excp = (Exception) pollingResult;
			handleExceptionResult(excp);
			// Log error
			LOG.error("Error loading data. " + excp.getMessage());
			status = PollingStatus.ERROR;
		} else {
			// Successful Result
			T result = (T) pollingResult;
			handleSuccessfulResult(result);
			status = PollingStatus.COMPLETE;
		}
		setPollingStatus(PollingStatus.ERROR);
		return status;
	}

	/**
	 * Handle an exception occurred.
	 *
	 * @param excp the exception that occurred
	 */
	protected void handleExceptionResult(final Exception excp) {
		List<String> msgs = extractExceptionMessages("Error loading data. ", excp);
		handleErrorMessage(msgs);
		retryButton.setVisible(true);
	}

	/**
	 * Handle the result. Default behaviour is to set the result as the bean for the content.
	 *
	 * @param result the polling action result
	 */
	protected void handleSuccessfulResult(final T result) {
		// Set the result as the bean
		getHolder().setBean(result);
		contentResultHolder.setVisible(true);
	}

	/**
	 * Extract the exception messages from the polling exception.
	 *
	 * @param prefix the message prefix
	 * @param exception the exception to setup messages
	 * @return the error messages
	 */
	protected List<String> extractExceptionMessages(final String prefix, final Exception exception) {
		return Arrays.asList(prefix + exception.getMessage());
	}

	/**
	 * Check the future holds the result.
	 *
	 * @return true if have result and need to stop polling
	 */
	@Override
	protected boolean checkForStopPolling() {
		// Stop polling if future null (must have expired) or is done
		TaskFuture<ResultHolder> future = getFuture();
		return future == null || future.isDone();
	}

	/**
	 * Handle the polling action.
	 *
	 * @param criteria the criteria for the polling action
	 * @throws PollingException the polling exception
	 */
	protected void handleAsyncServiceAction(final S criteria) throws PollingException {

		clearFuture();

		final ResultHolder result = new ResultHolder();
		Runnable task = new Runnable() {
			@Override
			public void run() {
				handleExecuteServiceAction(criteria, result);
			}
		};
		try {
			TaskFuture future = TASK_MANAGER.submit(task, result);
			// Save the future
			setFuture(future);
		} catch (Exception e) {
			throw new PollingException("Could not start thread to process polling action. " + e.getMessage());
		}
	}

	/**
	 *
	 * @param criteria the service criteria
	 * @param result the result holder
	 */
	protected void handleExecuteServiceAction(final S criteria, final ResultHolder result) {
		try {
			final ServiceAction<S, T> action = getServiceAction();
			if (action == null) {
				throw new PollingException("No polling service action has been defined.");
			}
			T resp = action.service(criteria);
			result.setResult(resp);
		} catch (PollingException e) {
			result.setResult(e);
		} catch (Exception e) {
			PollingException excp = new PollingException("Error calling service." + e.getMessage(), e);
			result.setResult(excp);
		}
	}

	/**
	 * @return the polling action future object
	 */
	protected TaskFuture<ResultHolder> getFuture() {
		return getComponentModel().future;
	}

	/**
	 * @param future the polling action future to save
	 */
	protected void setFuture(final TaskFuture<ResultHolder> future) {
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
	 *
	 * @return the service call action
	 */
	@Override
	public ServiceAction<S, T> getServiceAction() {
		return getComponentModel().pollingServiceModel;
	}

	/**
	 * Do the actual polling action (eg Service call).
	 * <p>
	 * As this method is called by a different thread, do not put any logic or functionality that needs the user
	 * context.
	 * </p>
	 *
	 * @param serviceModel the service call action
	 */
	@Override
	public void setServiceAction(final ServiceAction<S, T> serviceModel) {
		getOrCreateComponentModel().pollingServiceModel = serviceModel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingServiceModel<S, T> newComponentModel() {
		return new PollingServiceModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingServiceModel<S, T> getOrCreateComponentModel() {
		return (PollingServiceModel) super.getOrCreateComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected PollingServiceModel<S, T> getComponentModel() {
		return (PollingServiceModel) super.getComponentModel();
	}

	/**
	 * This model holds the state information.
	 *
	 * @param <S> the criteria type
	 * @param <T> the service action
	 */
	public static class PollingServiceModel<S, T> extends PollingModel {

		/**
		 * Record id.
		 */
		private S criteria;

		/**
		 * Holds the reference to the future for the polling action.
		 */
		private TaskFuture<ResultHolder> future;

		/**
		 * Flag if start polling manually with the start button.
		 */
		private boolean manualStart;

		/**
		 * Service Model.
		 */
		private ServiceAction<S, T> pollingServiceModel;
	}

}
