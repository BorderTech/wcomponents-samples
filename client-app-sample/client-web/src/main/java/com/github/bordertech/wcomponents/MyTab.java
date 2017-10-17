package com.github.bordertech.wcomponents;

/**
 * Keep Lazy tabs that are open as Visible during AJAX operations.
 *
 * @author Jonathan Austin
 * @since 1.0.0
 */
public class MyTab extends WTab {

	public MyTab(final WComponent component, final String label, final WTabSet.TabMode mode) {
		super(component, label, mode);
	}

	public MyTab(final WComponent component, final String label, final WTabSet.TabMode mode, final char accessKey) {
		super(component, label, mode, accessKey);
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		if (isLazy()) {
			if (AjaxHelper.getCurrentOperation() == null) {
				setRendered(null);
			}
			super.preparePaintComponent(request);
			if (isOpen()) {
				setRendered(Boolean.TRUE);
			}
			getContent().setVisible(isOpen() || isRendered());
		} else {
			super.preparePaintComponent(request);
		}
	}

	protected boolean isRendered() {
		Boolean rendered = (Boolean) getAttribute("wc-rendered");
		return rendered == null ? false : rendered;
	}

	protected void setRendered(final Boolean rendered) {
		setAttribute("wc-rendered", rendered);
	}

	protected boolean isLazy() {
		return getMode() == WTabSet.TabMode.LAZY;
	}

}
