package com.sample.client.ui.view;

import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.ContentAccess;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.SimpleBeanBoundTableModel;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WContent;
import com.github.bordertech.wcomponents.WDateField;
import com.github.bordertech.wcomponents.WDiv;
import com.github.bordertech.wcomponents.WFigure;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WImage;
import com.github.bordertech.wcomponents.WLink;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WPopup;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WTabSet;
import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.WTableColumn;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.WebUtilities;
import com.github.bordertech.wcomponents.polling.PollingException;
import com.github.bordertech.wcomponents.polling.ServiceAction;
import com.github.bordertech.wcomponents.task.TaskManager;
import com.github.bordertech.wcomponents.task.TaskManagerFactory;
import com.sample.client.model.DocumentContent;
import com.sample.client.model.DocumentDetail;
import com.sample.client.services.ClientServicesHelper;
import com.sample.client.ui.application.ClientApp;
import com.sample.client.ui.common.ClientWMessages;
import com.sample.client.ui.common.Constants;
import com.sample.client.ui.util.CacheServiceUtil;
import com.sample.client.ui.util.ClientServicesHelperFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Document view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DocumentView extends WSection implements MessageContainer {

	private static final ClientServicesHelper CLIENT_SERVICES = ClientServicesHelperFactory.getInstance();

	private static final TaskManager TASK_MANAGER = TaskManagerFactory.getInstance();

	private final ClientApp app;

	private final WMessages messages = new ClientWMessages();

	private final WMenu menu = new WMenu();

	private final WDiv ajaxPanel = new WDiv();

	private final WTable table = new WTable();

	/**
	 * @param app the client app.
	 */
	public DocumentView(final ClientApp app) {
		super("Documents");
		this.app = app;

		WPanel content = getContent();

		// Menu
		content.add(menu);
		setupMenu();

		// Messages
		content.add(messages);

		// Table
		content.add(table);
		table.setMargin(Constants.NORTH_MARGIN_LARGE);
		setupTable();

		// AJAX
		content.add(ajaxPanel);

		// Ids
		setIdName("docv");
		setNamingContext(true);

	}

	private void setupMenu() {
		WMenuItem item = new WMenuItem("Back");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				app.showSearch();
			}
		});
		menu.add(item);

		item = new WMenuItem("Reset");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doReset();
			}
		});
		menu.add(item);

	}

	/**
	 * Setup the document table.
	 */
	private void setupTable() {
		table.setIdName("dtbl");
		table.setSearchAncestors(false);

		// Setup laucnher link for the table column
		PollingLauncher launcherLink = new PollingLauncher() {
			@Override
			protected void handleInitContent(final Request request) {
				DocumentDetail doc = (DocumentDetail) getBean();
				setServiceCriteria(doc);
				super.handleInitContent(request); //To change body of generated methods, choose Tools | Templates.
			}
		};
		launcherLink.setManualStart(true);
		launcherLink.setVisible(true);
		launcherLink.getStartButton().setRenderAsLink(true);

		table.setMargin(Constants.SOUTH_MARGIN_LARGE);
		table.addColumn(new WTableColumn("ID", new WText()));
		table.addColumn(new WTableColumn("Description", new WText()));
		table.addColumn(new WTableColumn("Submitted", new WDateField()));
		table.addColumn(new WTableColumn("Link", launcherLink));
		table.setStripingType(WTable.StripingType.ROWS);
		table.setNoDataMessage("No documents found.");
		table.setSelectAllMode(WTable.SelectAllType.CONTROL);
		table.setSelectMode(WTable.SelectMode.MULTIPLE);
		table.setSortMode(WTable.SortMode.DYNAMIC);

		SimpleBeanBoundTableModel model = new SimpleBeanBoundTableModel(new String[]{"documentId", "description", "submitDate", "."});
		model.setSelectable(true);
		model.setComparator(0, SimpleBeanBoundTableModel.COMPARABLE_COMPARATOR);
		model.setComparator(1, SimpleBeanBoundTableModel.COMPARABLE_COMPARATOR);
		model.setComparator(2, SimpleBeanBoundTableModel.COMPARABLE_COMPARATOR);
		table.setTableModel(model);

		// Select Documents
		WButton selButton = new WButton("Launch selected documents");
		selButton.setAjaxTarget(ajaxPanel);
		selButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleLaunch();
			}
		});
		table.addAction(selButton);
		table.addActionConstraint(selButton, new WTable.ActionConstraint(1, 0, true, "Please select at least one document to view."));

		// Select Documents
		selButton = new WButton("View selected on page");
		selButton.setAjaxTarget(ajaxPanel);
		selButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleShowOnPage();
			}
		});
		table.addAction(selButton);
//		table.addActionConstraint(selButton, new WTable.ActionConstraint(1, 0, true, "Please select at least one document to view."));

		// Clear Cache
		selButton = new WButton("Clear Cache");
		selButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleClearCache();
				doReset();
			}
		});
		table.addAction(selButton);

	}

	/**
	 * Refresh results. Remove results from the cache.
	 */
	public void doReset() {
		reset();
	}

	public void doHandleClearCache() {
		for (DocumentDetail doc : getDocuments()) {
			CacheServiceUtil.clearResult(doc.getDocumentId());
		}
	}

	public void doHandleLaunch() {
		ajaxPanel.reset();
		// TODO Can replace with a WRepeater
		for (DocumentDetail selected : orderSelectedDocuments()) {
			PollingLauncher launcher = new PollingLauncher(selected);
			launcher.popupOnly();
			ajaxPanel.add(launcher);
		}
	}

	public void doHandleShowOnPage() {
		ajaxPanel.reset();

		// Create the tab set
		final WTabSet tabSet = new WTabSet(WTabSet.TabSetType.LEFT);
		tabSet.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				int current = tabSet.getActiveIndex();
				doHandleTabChanged(current);
			}
		});
		ajaxPanel.add(tabSet);

		// Save the ordered documents
		ArrayList<DocumentDetail> docs = orderSelectedDocuments();
		setSelectedDocs(docs);

		// Start loading of second document
		doHandleTabChanged(0);

		// Setup tabs
		int idx = 1;
		for (DocumentDetail selected : docs) {
			WPanel panel = new WPanel();
			int nameIdx = selected.getResourcePath().lastIndexOf("/");
			String tabName = nameIdx == -1 ? selected.getResourcePath() : selected.getResourcePath().substring(nameIdx + 1);
			if (idx++ == 1) {
				tabSet.addTab(panel, tabName, WTabSet.TabMode.LAZY, '1');
			} else {
				tabSet.addTab(panel, tabName, WTabSet.TabMode.LAZY);
			}
			panel.add(new PollingViewer(selected));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WMessages getMessages() {
		return messages;
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			try {
				List<DocumentDetail> docs = CLIENT_SERVICES.retrieveClientDocuments("dummyId");
				if (docs != null && !docs.isEmpty()) {
					setDocuments(docs);
					table.sort(0, true);
				}
			} catch (Exception e) {
				messages.error("Could not load documents. " + e.getMessage());
			}
			setInitialised(true);
		}
	}

	private void setDocuments(final List<DocumentDetail> docs) {
		table.setBean(docs);
	}

	private List<DocumentDetail> getDocuments() {
		List<DocumentDetail> docs = (List<DocumentDetail>) table.getBean();
		return docs == null ? Collections.EMPTY_LIST : docs;
	}

	private void doHandleTabChanged(final int current) {
		List<DocumentDetail> docs = getSelectedDocs();
		if (docs == null || docs.isEmpty()) {
			return;
		}
		int prev = getPrevIndex();
		int load = -1;
		if (current == docs.size() - 1) {
			// At end (less 1)
			load = current - 1;
		} else if (current > prev) {
			// Moving forward
			load = current + 1;
		} else {
			// Move backwards
			load = current - 1;
		}
		// Save current
		setPrevIndex(current);
		// Check if we can preload a document
		if (load > -1 && load < docs.size()) {
			DocumentDetail doc = docs.get(load);
			preLoadDocument(doc.getDocumentId());
		}
	}

	private void preLoadDocument(final String documentId) {

		// Already loaded
		if (CacheServiceUtil.getResult(documentId) != null) {
			return;
		}

		Runnable task = new Runnable() {
			@Override
			public void run() {
				try {
					if (!CacheServiceUtil.isProcessing(documentId)) {
						CacheServiceUtil.startProcessing(documentId);
						DocumentContent content = CLIENT_SERVICES.retrieveDocument(documentId);
						CacheServiceUtil.setResult(documentId, content);
					}
				} catch (Exception e) {
					CacheServiceUtil.setResult(documentId, e);
				}
			}
		};
		try {
			TASK_MANAGER.submit(task, null);
		} catch (Exception e) {
			// LOG Error
		}
	}

	private List<DocumentDetail> getSelectedDocs() {
		List<DocumentDetail> selected = (List<DocumentDetail>) ajaxPanel.getAttribute("docs");
		return selected == null ? Collections.EMPTY_LIST : selected;
	}

	private void setSelectedDocs(final ArrayList<DocumentDetail> docs) {
		ajaxPanel.setAttribute("docs", docs);
	}

	private int getPrevIndex() {
		Integer prev = (Integer) ajaxPanel.getAttribute("prev");
		return prev == null ? -1 : prev;
	}

	private void setPrevIndex(final Integer prev) {
		ajaxPanel.setAttribute("prev", prev);
	}

	private ArrayList<DocumentDetail> orderSelectedDocuments() {

		// Get all the documents
		List<DocumentDetail> docs = getDocuments();

		// Put the documents in the sort order (if required)
		if (table.isSorted()) {
			int[] sortIdx = table.getTableModel().sort(table.getSortColumnIndex(), table.isSortAscending());
			List<DocumentDetail> sorted = new ArrayList<>(docs.size());
			for (int idx : sortIdx) {
				sorted.add(docs.get(idx));
			}
			docs = sorted;
		}

		// Build a list of the selected docs in the sort order
		ArrayList<DocumentDetail> sorted = new ArrayList<>();
		Set<DocumentDetail> selected = (Set<DocumentDetail>) table.getSelectedRows();
		for (DocumentDetail doc : docs) {
			if (selected.contains(doc)) {
				sorted.add(doc);
				if (selected.size() == sorted.size()) {
					break;
				}
			}
		}
		return sorted;
	}

	private static class ContentWrapper implements ContentAccess {

		private final DocumentContent content;

		public ContentWrapper(final DocumentContent content) {
			this.content = content;
		}

		@Override
		public byte[] getBytes() {
			return content.getBytes();
		}

		@Override
		public String getDescription() {
			return content.getFilename();
		}

		@Override
		public String getMimeType() {
			return content.getMimeType();
		}
	}

	private static class PollingLauncher extends PollingCachePanel<DocumentDetail, DocumentContent> {

		private final WContent content = new WContent();
		private final WPopup popup = new WPopup() {
			@Override
			public String getUrl() {
				return content.getUrl();
			}
		};
		// Link to content (after retrieved result)
		private final WLink link = new WLink() {
			@Override
			public String getUrl() {
				return content.getUrl();
			}
		};

		public PollingLauncher() {
			this(null);
		}

		public PollingLauncher(final DocumentDetail document) {
			setServiceCriteria(document);

			getContentResultHolder().add(content);
			getContentResultHolder().add(popup);
			getContentResultHolder().add(link);

			// The service action
			setServiceAction(new ServiceAction<DocumentDetail, DocumentContent>() {
				@Override
				public DocumentContent service(final DocumentDetail criteria) throws PollingException {
					try {
						return CLIENT_SERVICES.retrieveDocument(criteria.getDocumentId());
					} catch (Exception e) {
						throw new PollingException("Error loading document [" + criteria.getDocumentId() + "]. " + e.getMessage(), e);
					}
				}
			});
		}

		public void popupOnly() {
			link.setVisible(false);
		}

		@Override
		protected void handleInitContent(final Request request) {
			super.handleInitContent(request);
			DocumentDetail doc = getServiceCriteria();
			getStartButton().setText(doc.getResourcePath());
		}

		@Override
		protected void handleInitResultContent(final Request request) {
			super.handleInitResultContent(request);
			DocumentContent doc = getServiceResult();
			String key = doc.getDocumentId() + "-" + doc.getFilename();
			content.setContentAccess(new ContentWrapper(doc));
			content.setCacheKey(key);
			popup.setTargetWindow(key);
			popup.setVisible(true);
			link.setText(getStartButton().getText());
		}

		@Override
		protected String getCacheKey(final DocumentDetail criteria) {
			return criteria.getDocumentId();
		}

	}

	private static class PollingViewer extends PollingCachePanel<DocumentDetail, DocumentContent> {

		private final WContent content = new WContent();

		public PollingViewer() {
			this(null);
		}

		public PollingViewer(final DocumentDetail document) {
			setServiceCriteria(document);
			getContentResultHolder().add(content);

			// The service action
			setServiceAction(new ServiceAction<DocumentDetail, DocumentContent>() {
				@Override
				public DocumentContent service(final DocumentDetail criteria) throws PollingException {
					try {
						return CLIENT_SERVICES.retrieveDocument(criteria.getDocumentId());
					} catch (Exception e) {
						throw new PollingException("Error loading document [" + criteria.getDocumentId() + "]. " + e.getMessage(), e);
					}
				}
			});

		}

		@Override
		protected void handleInitResultContent(final Request request) {
			super.handleInitResultContent(request);

			// Document Content
			DocumentContent doc = getServiceResult();

			// Setup the viewing option (based on MIME Type)
			WDiv holder = getContentResultHolder();

			String mimeType = doc.getMimeType();
			String title = doc.getDocumentId() + "-" + doc.getFilename();

			content.setContentAccess(new ContentWrapper(doc));
			content.setCacheKey(title);

			if (mimeType.startsWith("image")) {
				// Image
				WImage image = new WImage() {
					@Override
					public String getImageUrl() {
						return content.getUrl();
					}
				};
				WFigure figure = new WFigure(image, title);
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
		protected String getCacheKey(final DocumentDetail criteria) {
			return criteria.getDocumentId();
		}

	}
}
