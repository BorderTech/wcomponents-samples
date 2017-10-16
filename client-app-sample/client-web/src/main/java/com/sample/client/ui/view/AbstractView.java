package com.sample.client.ui.view;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.Container;
import com.github.bordertech.wcomponents.Input;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.layout.ColumnLayout;
import com.github.bordertech.wcomponents.validation.ValidatingAction;
import com.sample.client.model.ClientSummary;
import com.sample.client.services.exception.ServiceException;
import com.sample.client.ui.application.ClientApp;
import com.sample.client.ui.common.ClientWMessages;
import com.sample.client.ui.common.Constants;
import com.sample.client.ui.common.ViewMode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Abstract view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public abstract class AbstractView<T> extends WSection implements MessageContainer {

	/**
	 * The logger instance for this class.
	 */
	private static final Log LOG = LogFactory.getLog(AbstractView.class);

	/**
	 * Main controller.
	 */
	private final ClientApp app;

	/**
	 * Messages for this view.
	 */
	private final WMessages messages = new ClientWMessages();

	/**
	 * @param title the view title
	 * @param app the client app.
	 */
	public AbstractView(final String title, final ClientApp app) {
		super(title);
		this.app = app;

		WPanel content = getContent();

		// Messages
		content.add(messages);
	}

	/**
	 * Setup the view content.
	 */
	protected abstract void setupContent();

	/**
	 * @param detail the bean to be displayed
	 */
	public void setDetail(final T detail) {
		getContent().setBean(detail);
	}

	/**
	 * @return the bean being displayed.
	 */
	public T getDetail() {
		return (T) getContent().getBean();
	}

	public void setViewMode(final ViewMode viewMode) {
		getOrCreateComponentModel().viewMode = viewMode;
		if (viewMode == ViewMode.Readonly) {
			doMakeReadOnly(getContent());
		}
	}

	public ViewMode getViewMode() {
		return getComponentModel().viewMode;
	}

	private void doMakeReadOnly(final WComponent component) {
		if (component instanceof Input) {
			((Input) component).setReadOnly(true);
		}

		if (component instanceof Container) {
			for (WComponent child : ((Container) component).getChildren()) {
				doMakeReadOnly(child);
			}
		}
	}

	/**
	 * Setup the action buttons
	 */
	protected void setupButtons() {

		WPanel content = getContent();
		WPanel panel = new WPanel(WPanel.Type.FEATURE);
		panel.setMargin(Constants.NORTH_MARGIN_LARGE);
		panel.setLayout(new ColumnLayout(new int[]{50, 50}, new ColumnLayout.Alignment[]{ColumnLayout.Alignment.LEFT, ColumnLayout.Alignment.RIGHT}));

		content.add(panel);

		WContainer left = new WContainer();
		WContainer right = new WContainer();
		panel.add(left);
		panel.add(right);

		// Cancel
		WButton cancelButton = new WButton("Cancel") {
			@Override
			public boolean isVisible() {
				return getViewMode() == ViewMode.Create || getViewMode() == ViewMode.Update;
			}
		};
		cancelButton.setCancel(true);
		cancelButton.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				getApp().showSearch();
			}
		});
		left.add(cancelButton);

		// Back
		WButton backButton = new WButton("Back") {
			@Override
			public boolean isVisible() {
				return getViewMode() == ViewMode.Readonly;
			}
		};
		backButton.setAction(new Action() {
			@Override
			public void execute(ActionEvent event) {
				getApp().showSearch();
			}
		});
		left.add(backButton);

		// Save
		WButton saveButton = new WButton() {
			@Override
			public boolean isVisible() {
				return getViewMode() == ViewMode.Create || getViewMode() == ViewMode.Update;
			}

			@Override
			public String getText() {
				return getViewMode() == ViewMode.Create ? "Create" : "Update";
			}
		};
		saveButton.setAction(new ValidatingAction(getMessages().getValidationErrors(), content) {
			@Override
			public void executeOnValid(final ActionEvent event) {
				doSaveAction();
			}
		});
		right.add(saveButton);

		content.setDefaultSubmitButton(saveButton);

	}

	protected void doSaveAction() {
		doUpdateDetailBean();
		T bean = getDetail();
		try {
			if (getViewMode() == ViewMode.Create) {
				doCreateServiceCall(bean);
				getApp().showSearch();
			} else {
				doUpdateServiceCall(bean);
				ClientSummary summary = getSummary(bean);
				getApp().showSearchWithUpdate(summary);
			}
		} catch (Exception e) {
			String msg = "Error calling service. " + e.getMessage();
			LOG.error(msg, e);
			getMessages().error(msg);
		}
	}

	protected void doUpdateDetailBean() {
		WebUtilities.updateBeanValue(getContent());
	}

	protected abstract void doCreateServiceCall(final T bean) throws ServiceException;

	protected abstract void doUpdateServiceCall(final T bean) throws ServiceException;

	protected abstract ClientSummary getSummary(final T bean);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}

	/**
	 *
	 * @return the main controller
	 */
	public ClientApp getApp() {
		return app;
	}

	@Override
	protected ViewModel newComponentModel() {
		return new ViewModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ViewModel getComponentModel() {
		return (ViewModel) super.getComponentModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ViewModel getOrCreateComponentModel() {
		return (ViewModel) super.getOrCreateComponentModel();
	}

	/**
	 * Holds the extrinsic state information of a WApplication.
	 */
	public static class ViewModel extends SectionModel {

		private ViewMode viewMode;
	}

}
