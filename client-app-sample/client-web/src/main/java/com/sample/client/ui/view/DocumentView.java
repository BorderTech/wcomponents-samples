package com.sample.client.ui.view;

import com.github.bordertech.didums.Didums;
import com.github.bordertech.taskmaster.service.ResultHolder;
import com.github.bordertech.taskmaster.service.ServiceAction;
import com.github.bordertech.taskmaster.service.ServiceException;
import com.github.bordertech.taskmaster.service.ServiceHelper;
import com.github.bordertech.wcomponents.Action;
import com.github.bordertech.wcomponents.ActionEvent;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.MenuSelectContainer;
import com.github.bordertech.wcomponents.MessageContainer;
import com.github.bordertech.wcomponents.MutableContainer;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.SimpleBeanBoundTableModel;
import com.github.bordertech.wcomponents.Size;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WButton;
import com.github.bordertech.wcomponents.WDateField;
import com.github.bordertech.wcomponents.WImage;
import com.github.bordertech.wcomponents.WMenu;
import com.github.bordertech.wcomponents.WMenuItem;
import com.github.bordertech.wcomponents.WMessages;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WSection;
import com.github.bordertech.wcomponents.WTabSet;
import com.github.bordertech.wcomponents.WTable;
import com.github.bordertech.wcomponents.WTableColumn;
import com.github.bordertech.wcomponents.WText;
import com.github.bordertech.wcomponents.addons.common.WDiv;
import com.github.bordertech.wcomponents.addons.common.relative.WLibTab;
import com.github.bordertech.wcomponents.layout.ColumnLayout;
import com.sample.client.model.DocumentContent;
import com.sample.client.model.DocumentDetail;
import com.sample.client.services.ClientServicesHelper;
import com.sample.client.ui.application.ClientApp;
import com.sample.client.ui.common.ClientWMessages;
import com.sample.client.ui.common.Constants;
import com.sample.client.ui.util.ClientServicesHelperFactory;
import com.sample.client.ui.view.polling.PollingLauncher;
import com.sample.client.ui.view.polling.PollingViewer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.cache.Cache;

/**
 * Document view.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class DocumentView extends WSection implements MessageContainer {

	private static final ServiceHelper SERVICE_HELPER = Didums.getService(ServiceHelper.class);

	public static final Cache<String, ResultHolder> CACHE = SERVICE_HELPER.getResultHolderCache("sample-docs-cache");

	public static final ServiceAction<DocumentDetail, DocumentContent> RETREIVE_DOC_ACTION = new ServiceAction<DocumentDetail, DocumentContent>() {
		@Override
		public DocumentContent service(final DocumentDetail document) {
			try {
				return CLIENT_SERVICES.retrieveDocument(document.getDocumentId());
			} catch (Exception e) {
				throw new ServiceException("Error retrieveing document [" + document + "]. " + e.getMessage(), e);
			}
		}
	};

	private static final ClientServicesHelper CLIENT_SERVICES = ClientServicesHelperFactory.getInstance();

	private final ClientApp app;

	private final WMessages messages = new ClientWMessages();

	private final WMenu mainMenu = new WMenu();

	private final WMenu viewMenu = new WMenu();

	private final WDiv ajaxPanel = new WDiv();

	private final WDiv onPagePanel = new WDiv();

	private final WPanel viewPanel = new WPanel();

	private final WTable table = new WTable();

	public enum ViewType {
		TAB("icons/ui-tab-content-vertical-icon.png", "tabs view ", 'T'),
		COL("icons/ui-split-panel-vertical-icon.png", "column view", 'C'),
		SPLIT("icons/ui-split-panel-icon.png", "split view", 'S');

		ViewType(final String url, final String desc, final char accessKey) {
			this.url = url;
			this.desc = desc;
			this.accessKey = accessKey;
		}

		private final String url;
		private final String desc;
		private final char accessKey;

		public String getUrl() {
			return url;
		}

		public String getDesc() {
			return desc;
		}

		public char getAcccessKey() {
			return accessKey;
		}
	}

	/**
	 * @param app the client app.
	 */
	public DocumentView(final ClientApp app) {
		super("Documents");
		this.app = app;

		WPanel content = getContent();

		// Menu
		content.add(mainMenu);
		setupMainMenu();

		// Messages
		content.add(messages);

		// Table
		content.add(table);
		table.setMargin(Constants.NORTH_MARGIN_LARGE);
		setupTable();

		// AJAX
		content.add(ajaxPanel);
		ajaxPanel.add(onPagePanel);
		onPagePanel.add(viewMenu);
		onPagePanel.add(viewPanel);
		setupViewMenu();

		viewPanel.setMargin(new Margin(Size.LARGE, Size.ZERO, Size.SMALL, Size.ZERO));
		// Default Visibility
		onPagePanel.setVisible(false);

		// Ids
		setIdName("docv");
		setNamingContext(true);

	}

	private void setupMainMenu() {
		WMenuItem item = new WMenuItem("Back");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				app.showSearch();
			}
		});
		mainMenu.add(item);

		item = new WMenuItem("Reset");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doReset();
			}
		});
		mainMenu.add(item);

		// Clear Cache
		item = new WMenuItem("Clear Cache");
		item.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleClearCache();
				doReset();
			}
		});
		mainMenu.add(item);
	}

	private void setupViewMenu() {

		viewMenu.setSelectionMode(MenuSelectContainer.SelectionMode.SINGLE);

		// View type
		for (ViewType type : ViewType.values()) {
			final ViewType viewtype = type;
			WMenuItem item = new WMenuItem("") {
				@Override
				public boolean isSelected() {
					return Objects.equals(viewtype, getCurrentViewType());
				}
			};
			item.setToolTip(type.getDesc());
			item.setAccessKey(type.getAcccessKey());
			WImage image = new WImage();
			image.setImageUrl(type.getUrl());
			item.getDecoratedLabel().setHead(image);
			item.setAction(new Action() {
				@Override
				public void execute(final ActionEvent event) {
					doHandleViewMenuAction(viewtype);
				}
			});
			viewMenu.add(item);
			onPagePanel.add(new WAjaxControl(item, viewPanel));
		}

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
			protected void handleInitPollingPanel(final Request request) {
				DocumentDetail doc = (DocumentDetail) getBean();
				setServiceCriteria(doc);
				super.handleInitPollingPanel(request);
			}
		};
		launcherLink.setVisible(true);

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
		selButton.setAccessKey('L');
		selButton.setAjaxTarget(ajaxPanel);
		selButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleLaunch();
			}
		});
		table.addAction(selButton);
		table.addActionConstraint(selButton, new WTable.ActionConstraint(1, 0, true, "Please select at least one document to launch."));

		// Select Documents
		selButton = new WButton("View selected on page");
		selButton.setAccessKey('V');
		selButton.setAjaxTarget(ajaxPanel);
		selButton.setAction(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				doHandleShowOnPage();
			}
		});
		table.addAction(selButton);
		table.addActionConstraint(selButton, new WTable.ActionConstraint(1, 0, true, "Please select at least one document to view."));

	}

	@Override
	public WMessages getMessages() {
		return messages;
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			try {
				// Dummy service to load the documents tables
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

	/**
	 * Refresh results. Remove results from the cache.
	 */
	protected void doReset() {
		reset();
	}

	/**
	 * Clear the Cache
	 */
	protected void doHandleClearCache() {
		for (DocumentDetail doc : getDocuments()) {
			CACHE.remove(doc.getDocumentId());
		}
	}

	protected void doHandleLaunch() {
		ajaxPanel.reset();
		// TODO Can replace with a WRepeater
		for (DocumentDetail selected : orderSelectedDocuments()) {
			PollingLauncher launcher = new PollingLauncher(selected);
			launcher.popupOnly();
			ajaxPanel.add(launcher);
		}
	}

	protected void setCurrentViewType(final ViewType type) {
		setAttribute("viewType", type);
	}

	protected ViewType getCurrentViewType() {
		ViewType type = (ViewType) getAttribute("viewType");
		return type == null ? ViewType.TAB : type;
	}

	protected void doHandleViewMenuAction(final ViewType type) {
		// Check if changed
		if (Objects.equals(type, getCurrentViewType())) {
			return;
		}
		setCurrentViewType(type);
		setupView();
	}

	protected void doHandleShowOnPage() {
		ajaxPanel.reset();
		// Save the ordered documents (on the AJAX Panel)
		ArrayList<DocumentDetail> docs = orderSelectedDocuments();
		setSelectedDocs(docs);

		// Setup the view
		onPagePanel.setVisible(true);
		setupView();
	}

	protected void setupView() {
		viewPanel.reset();
		switch (getCurrentViewType()) {
			case TAB:
				setupTabView();
				break;
			case COL:
				addSelectedToContainer(viewPanel);
				break;
			case SPLIT:
				viewPanel.setLayout(new ColumnLayout(new int[]{50, 50}, Size.MEDIUM, Size.MEDIUM));
				addSelectedToContainer(viewPanel);
				break;
		}
	}

	protected void addSelectedToContainer(final MutableContainer container) {
		for (DocumentDetail selected : getSelectedDocs()) {
			WPanel panel = new WPanel();
			panel.setMargin(new Margin(Size.ZERO, Size.ZERO, Size.LARGE, Size.ZERO));
			container.add(panel);
			panel.add(new PollingViewer(selected));
		}
	}

	protected void setupTabView() {
		// Create the tab set
		final WTabSet tabSet = new WTabSet(WTabSet.TabSetType.LEFT);
		tabSet.setActionOnChange(new Action() {
			@Override
			public void execute(final ActionEvent event) {
				int current = tabSet.getActiveIndex();
				doHandleTabChanged(current);
			}
		});
		viewPanel.add(tabSet);

		// Start loading of second document
		doHandleTabChanged(0);

		// Setup tabs
		int idx = 1;
		for (DocumentDetail selected : getSelectedDocs()) {
			WPanel panel = new WPanel();
			int nameIdx = selected.getResourcePath().lastIndexOf("/");
			String tabName = nameIdx == -1 ? selected.getResourcePath() : selected.getResourcePath().substring(nameIdx + 1);
			boolean cached = CACHE.containsKey(selected.getDocumentId());
			WTabSet.TabMode mode = cached ? WTabSet.TabMode.CLIENT : WTabSet.TabMode.LAZY;
			if (idx++ == 1) {
				WLibTab tab = new WLibTab(panel, tabName, mode, 'D');
				tabSet.add(tab);
			} else {
				WLibTab tab = new WLibTab(panel, tabName, mode);
				tabSet.add(tab);
			}
			panel.add(new PollingViewer(selected));
		}
	}

	private void doHandleTabChanged(final int current) {
		List<DocumentDetail> docs = getSelectedDocs();
		if (docs == null || docs.isEmpty()) {
			return;
		}
		int prev = getPrevIndex();
		int load;
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
			// Retrieve Document (Will only start if it is not in the cache)
			SERVICE_HELPER.handleAsyncServiceCall(CACHE, doc.getDocumentId(), doc, RETREIVE_DOC_ACTION);
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

	private void setDocuments(final List<DocumentDetail> docs) {
		table.setBean(docs);
	}

	private List<DocumentDetail> getDocuments() {
		List<DocumentDetail> docs = (List<DocumentDetail>) table.getBean();
		return docs == null ? Collections.EMPTY_LIST : docs;
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

}
