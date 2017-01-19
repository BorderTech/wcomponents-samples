package com.sample.fileupload;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.AjaxTarget;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WComponent;
import com.github.bordertech.wcomponents.WContainer;
import com.github.bordertech.wcomponents.WEditableImage;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WFigure;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WImage;
import com.github.bordertech.wcomponents.WImageEditor;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WMultiFileWidget;
import com.github.bordertech.wcomponents.WMultiFileWidget.FileWidgetUpload;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.layout.ColumnLayout;
import com.github.bordertech.wcomponents.util.HtmlClassProperties;
import com.github.bordertech.wcomponents.validation.Diagnostic;
import com.github.bordertech.wcomponents.validation.ValidatingAction;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Multifile upload with AJAX trigger.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DemoApp extends WApplication {

	private static final int DEFAULT_IMAGE_WIDTH = 300;
	private static final int DEFAULT_IMAGE_HEIGHT = 400;
	private static final String OVERLAY_URL = "images/overlay.png";

	private final WMessages messages = new WMessages();
	private final WMultiFileWidget widget = new WMultiFileWidget() {
		@Override
		protected void validateComponent(final List<Diagnostic> diags) {
			super.validateComponent(diags);
			if (!checkAllValid()) {
				diags.add(createErrorDiagnostic("Not all files are valid"));
			}
		}

		@Override
		protected void doHandleUploadRequest(final Request request) {
			super.doHandleUploadRequest(request);
			if (isNewUpload()) {
				int idx = getFiles().size() - 1;
				FileWidgetUpload upload = getFiles().get(idx);
				ValidatingServicePanel panel = new ValidatingServicePanel();
				validatingContainer.add(panel);
				panel.setRecordId(upload);
				panel.setPollingText(" Validating file [" + upload.getFile().getFileName() + "].");
				panel.doStartLoading();
			}
		}
	};

	private final WImage image = new WEditableImage(widget) {
		@Override
		public String getImageUrl() {
			String fileId = (String) getAttribute("image-fileid");
			if (fileId != null) {
				// Get the url each time to allow for step count in the URL
				return widget.getFileUrl(fileId);
			}
			return null;
		}
	};

	private final WFigure imageHolder = new WFigure(image, "") {
		@Override
		public boolean isHidden() {
			return image.getImageUrl() == null;
		}
	};

	private final WPanel ajaxPanel = new WPanel() {
		@Override
		public boolean isHidden() {
			return validatingContainer.getChildCount() == 0;
		}
	};
	private final WAjaxControl ajaxWidget = new WAjaxControl(widget, new AjaxTarget[]{imageHolder, ajaxPanel});

	private final WContainer validatingContainer = new WContainer();

	/**
	 * Construct.
	 */
	public DemoApp() {

		// Header
		final WPanel header = new WPanel(WPanel.Type.HEADER);
		add(header);
		header.add(new WHeading(HeadingLevel.H1, "Fileupload Sample"));

		// Detail
		WSection section = new WSection("Upload");
		section.setMargin(new Margin(12));
		add(section);

		WPanel content = section.getContent();
		// Messages
		content.add(messages);

		WPanel split = new WPanel();
		split.setLayout(new ColumnLayout(new int[]{50, 50}, 12, 0));
		split.setHtmlClass(HtmlClassProperties.RESPOND);
		split.setMargin(new Margin(12, 0, 12, 0));
		content.add(split);

		WContainer left = new WContainer();
		WContainer right = new WContainer();
		split.add(left);
		split.add(right);

		// Footer
		final WPanel footer = new WPanel(WPanel.Type.FOOTER);
		add(footer);
		footer.add(new WText(new Date().toString()));

		// Left Column
		WFieldLayout layout = new WFieldLayout(WFieldLayout.LAYOUT_STACKED);
		layout.setMargin(new Margin(0, 0, 6, 0));
		left.add(layout);

		widget.setMandatory(true);
		widget.setUseThumbnails(true);
		widget.setDropzone(split);
		widget.setFileTypes(new String[]{"image/*"});

		layout.addField("Upload", widget);

		final WImageEditor editor = new WImageEditor();
		editor.setUseCamera(true);
		editor.setOverlayUrl(OVERLAY_URL);
		editor.setSize(new Dimension(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT));

		widget.setEditor(editor);
		left.add(editor);

		// Right Column
		final WPanel contentPanel = new WPanel();
		right.add(contentPanel);

		contentPanel.add(new WHeading(HeadingLevel.H2, "File View"));

		contentPanel.add(imageHolder);

		// File AJAX action (ie selected)
		widget.setFileAjaxAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				String fileId = (String) event.getActionObject();
				FileWidgetUpload file = widget.getFile(fileId);
				String url = widget.getFileUrl(fileId);
				image.reset();
				image.setAlternativeText(file.getFile().getDescription());
				image.setImageUrl(url);
				image.setAttribute("image-fileid", fileId);

				if (imageHolder.getDecoratedLabel() != null) {
					if (!"".equals(image.getAlternativeText())) {
						imageHolder.getDecoratedLabel().setBody(
								new WText(image.getAlternativeText()));
					} else {
						imageHolder.getDecoratedLabel().setBody(new WText("Unnamed Image."));
					}
				}
			}
		});

		// File changed action (added/removed from list)
		widget.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				String fileId = (String) image.getAttribute("image-fileid");
				if (fileId != null) {
					// Check file id still in list
					FileWidgetUpload file = widget.getFile(fileId);
					if (file == null) {
						contentPanel.reset();
					}
				}
				checkPanels();
			}
		});

		// AJAX Validation
		left.add(ajaxPanel);
		ajaxPanel.add(validatingContainer);
		ajaxPanel.add(ajaxWidget);

		validatingContainer.setNamingContext(true);
		validatingContainer.setIdName("val");

		WPanel buttonPanel = new WPanel(WPanel.Type.FEATURE);
		buttonPanel.setMargin(new Margin(12, 0, 6, 0));
		ColumnLayout.Alignment[] align = new ColumnLayout.Alignment[]{ColumnLayout.Alignment.LEFT, ColumnLayout.Alignment.RIGHT};
		buttonPanel.setLayout(new ColumnLayout(new int[]{50, 50}, align, 12, 0));
		buttonPanel.setHtmlClass(HtmlClassProperties.RESPOND);
		content.add(buttonPanel);

		// Reset Button
		WButton resetButton = new WButton("Reset");
		resetButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				DemoApp.this.reset();
			}
		});
		buttonPanel.add(resetButton);

		// Validation Button
		WButton button = new WButton("Validate");
		buttonPanel.add(button);

		button.setAction(new ValidatingAction(messages.getValidationErrors(), layout) {
			@Override
			public void executeOnValid(final ActionEvent event) {
				messages.success("OK.");
			}
		});

	}

	/**
	 * Check if any validating panels need to be removed (ie file deleted from list).
	 */
	private void checkPanels() {
		if (validatingContainer.getChildCount() == 0) {
			return;
		}
		List<FileWidgetUpload> files = widget.getFiles();
		for (int i = validatingContainer.getChildCount(); i > 0; i--) {
			ValidatingServicePanel panel = (ValidatingServicePanel) validatingContainer.getChildAt(i - 1);
			if (!files.contains(panel.getRecordId())) {
				validatingContainer.remove(panel);
			}
		}
	}

	/**
	 * @return true if all the files are valid
	 */
	private boolean checkAllValid() {
		for (WComponent panel : validatingContainer.getChildren()) {
			ValidatingServicePanel validating = (ValidatingServicePanel) panel;
			if (!validating.isValidFile()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Polling service that validates the file.
	 */
	public static class ValidatingServicePanel extends AbstractPollingPanel<ValidatingResult, FileWidgetUpload> {

		private final WMessages messages = new WMessages(true);

		/**
		 * Construct the panel.
		 */
		public ValidatingServicePanel() {
			super(null, 267, true);

			getContent().add(messages);
			getStartButton().setVisible(false);
			setMargin(new Margin(0, 0, 6, 0));
		}

		/**
		 * @return true if file is valid
		 */
		public boolean isValidFile() {
			return getPanelStatus() == PanelStatus.COMPLETE && messages.getErrorMessages().isEmpty();
		}

		/**
		 * Setup the messages for validating the file.
		 *
		 * @param response the service response
		 */
		@Override
		protected void handleSuccessfulServiceResponse(final ValidatingResult response) {
			FileWidgetUpload file = getRecordId();
			if (response.getMessages().isEmpty()) {
				messages.info("File [" + file.getFile().getFileName() + "] is valid");
			} else {
				for (String msg : response.getMessages()) {
					messages.error("File [" + file.getFile().getFileName() + "] is not valid");
					messages.error(msg);
				}
			}
		}

		@Override
		protected ValidatingResult doServiceCall(final FileWidgetUpload recordId) throws PollingServiceException {

			// Pretend Service Calls
			String fileName = recordId.getFile().getFileName();
			ValidatingResult result = new ValidatingResult();

			if (fileName.contains("bad")) {
				try {
					Thread.currentThread().sleep(5000);
				} catch (Exception e) {

				}
				result.addMessage("Bad file name");
			} else if (fileName.contains("long")) {
				try {
					Thread.currentThread().sleep(10000);
				} catch (Exception e) {

				}
			} else if (fileName.contains("error")) {
				try {
					Thread.currentThread().sleep(3000);
				} catch (Exception e) {

				}
				throw new PollingServiceException("Exception validating file");
			}
			return result;
		}

	}

	public static class ValidatingResult {

		private List<String> messages;

		public void addMessage(final String message) {
			if (messages == null) {
				messages = new ArrayList<>();
			}
			messages.add(message);
		}

		public List<String> getMessages() {
			if (messages == null) {
				return Collections.EMPTY_LIST;
			} else {
				return messages;
			}
		}
	}

}
