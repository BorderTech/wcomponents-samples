package com.github.bordertech.wcomponents;

import com.github.bordertech.wcomponents.servlet.WebXmlRenderContext;

/**
 * A DIV that can be an AJAX Target and Subordinate Target.
 *
 * @author jonathan
 */
public class WDiv extends WContainer implements AjaxTarget, SubordinateTarget {

	@Override
	protected void beforePaint(final RenderContext renderContext) {
		XmlStringBuilder xml = ((WebXmlRenderContext) renderContext).getWriter();

		xml.appendTagOpen("div");
		xml.appendAttribute("id", getId());
		xml.appendOptionalAttribute("class", getHtmlClass());
		xml.appendOptionalAttribute("hidden", isHidden(), "true");
		xml.appendClose();
	}

	@Override
	protected void afterPaint(final RenderContext renderContext) {
		XmlStringBuilder xml = ((WebXmlRenderContext) renderContext).getWriter();
		xml.appendEndTag("div");
	}

	@Override
	protected DivModel newComponentModel() {
		return new DivModel();
	}

	@Override
	protected DivModel getComponentModel() {
		return (DivModel) super.getComponentModel();
	}

	@Override
	protected DivModel getOrCreateComponentModel() {
		return (DivModel) super.getOrCreateComponentModel();
	}

	public static class DivModel extends BeanAndProviderBoundComponentModel {
	}
}
