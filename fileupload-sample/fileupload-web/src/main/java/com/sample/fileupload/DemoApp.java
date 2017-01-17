package com.sample.fileupload;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WApplication;
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
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.layout.ColumnLayout;
import com.github.bordertech.wcomponents.util.HtmlClassProperties;
import java.awt.Dimension;
import java.util.Date;

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

	/**
	 * Construct.
	 */
	public DemoApp() {

		// Header
		final WPanel header = new WPanel(WPanel.Type.HEADER);
		add(header);
		header.add(new WHeading(HeadingLevel.H1, "Fileupload Sample"));

		// Detail
		WPanel detail = new WPanel();
		add(detail);

		// Messages
		detail.add(messages);

		WPanel split = new WPanel();
		split.setMargin(new Margin(12));
		split.setLayout(new ColumnLayout(new int[]{50, 50}, 12, 0));
		split.setHtmlClass(HtmlClassProperties.RESPOND);
		detail.add(split);

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
		left.add(layout);

		final WMultiFileWidget widget = new WMultiFileWidget();
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

		final WImage image = new WEditableImage(widget) {
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

		final WFigure imageHolder = new WFigure(image, "") {
			@Override
			public boolean isHidden() {
				return image.getImageUrl() == null;
			}
		};
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

		// File changed action (removed from list)
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
			}
		});

		right.add(new WAjaxControl(widget, imageHolder));

	}

}
