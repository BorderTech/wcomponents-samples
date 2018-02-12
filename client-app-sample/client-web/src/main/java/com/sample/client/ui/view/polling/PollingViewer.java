package com.sample.client.ui.view.polling;

import com.github.bordertech.taskmaster.service.ResultHolder;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.WContent;
import com.github.bordertech.wcomponents.WFigure;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WLink;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.addons.common.WDiv;
import com.github.bordertech.wcomponents.addons.polling.PollingServicePanel;
import com.github.bordertech.wcomponents.addons.polling.PollingStartType;
import com.sample.client.model.DocumentContent;
import com.sample.client.model.DocumentDetail;
import com.sample.client.ui.view.DocumentView;
import javax.cache.Cache;

/**
 * Polling panel that automatically loads document content.
 */
public class PollingViewer extends PollingServicePanel<DocumentDetail, DocumentContent> {

	private final WContent content = new WContent();

	public PollingViewer() {
		this(null);
	}

	public PollingViewer(final DocumentDetail document) {
		setServiceCriteria(document);
		getContentResultHolder().add(content);
		// Service action
		setServiceAction(DocumentView.RETREIVE_DOC_ACTION);
		// AUTO Start
		setStartType(PollingStartType.AUTOMATIC);
	}

	@Override
	protected void handleInitResultContent(final Request request) {
		super.handleInitResultContent(request);
		// Document Content
		DocumentContent doc = getServiceResult().getResult();
		// Setup the viewing option (based on MIME Type)
		WDiv holder = getContentResultHolder();
		String mimeType = doc.getMimeType();
		String title = doc.getDocumentId() + "-" + doc.getFilename();
		content.setContentAccess(new CachedContentWrapper(doc.getFilename(), doc.getMimeType(), doc.getDocumentId()));
		content.setCacheKey(title);
		if (mimeType.startsWith("image")) {
			// Put the Image in a Link
			WLink link = new WLink() {
				@Override
				public String getUrl() {
					return content.getUrl();
				}

				@Override
				public String getImageUrl() {
					return content.getUrl();
				}
			};
			link.setText(doc.getFilename());
			link.setTargetWindowName(title);
			link.setImage(title);
			WFigure figure = new WFigure(link, title);
			holder.add(figure);
		} else if (mimeType.startsWith("application/pdf")) {
			// IFrame - PDF
			holder.add(new WHeading(HeadingLevel.H2, title));
			WText txt = new WText() {
				@Override
				public String getText() {
					StringBuilder html = new StringBuilder();
					html.append("<iframe src=\"");
					html.append(WebUtilities.encodeUrl(content.getUrl()));
					html.append("\"");
					html.append(" style=\"width:100%; height: 50em\" ");
					html.append("></iframe>");
					return html.toString();
				}
			};
			txt.setEncodeText(false);
			holder.add(txt);
		} else {
			// Link to content
			WLink link = new WLink() {
				@Override
				public String getUrl() {
					return content.getUrl();
				}
			};
			link.setText(doc.getFilename());
			link.setTargetWindowName(title);
			holder.add(link);
		}
	}

	@Override
	public String getServiceCacheKey() {
		return getServiceCriteria().getDocumentId();
	}

	@Override
	protected Cache<String, ResultHolder> getServiceCache() {
		return DocumentView.CACHE;
	}

}
