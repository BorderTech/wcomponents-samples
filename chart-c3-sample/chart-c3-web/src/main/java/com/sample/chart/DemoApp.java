package com.sample.chart;

import com.github.bordertech.wcomponents.ContentAccess;
import com.github.bordertech.wcomponents.HeadingLevel;
import com.github.bordertech.wcomponents.Margin;
import com.github.bordertech.wcomponents.Request;
import com.github.bordertech.wcomponents.UIContextHolder;
import com.github.bordertech.wcomponents.WAjaxControl;
import com.github.bordertech.wcomponents.WApplication;
import com.github.bordertech.wcomponents.WContent;
import com.github.bordertech.wcomponents.WDropdown;
import com.github.bordertech.wcomponents.WFieldLayout;
import com.github.bordertech.wcomponents.WHeading;
import com.github.bordertech.wcomponents.WPanel;
import com.github.bordertech.wcomponents.WTemplate;
import com.github.bordertech.wcomponents.template.TemplateRendererFactory;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Demonstrate using C3/D3 charts.
 *
 * @author Jonathan Austin
 */
public class DemoApp extends WApplication {

	private final WContent jsonData = new WContent();

	private final WTemplate configC3Script = new WTemplate("/hbs/configC3.hbs", TemplateRendererFactory.TemplateEngine.HANDLEBARS);

	private final WTemplate configChartScript = new WTemplate("/hbs/configChart.hbs", TemplateRendererFactory.TemplateEngine.HANDLEBARS);

	private final WDropdown drpType = new WDropdown(Arrays.asList("bar", "line", "pie"));

	private final WPanel chartPanel = new WPanel(WPanel.Type.FEATURE);

	/**
	 * Construct application.
	 */
	public DemoApp() {

		// C3 CSS
		addCssUrl("js/lib/c3-0.4.11.css");

		// C3 Config script
		add(configC3Script);

		// Data for the chart
		jsonData.setContentAccess(new JsonContentAccess() {
			@Override
			public String getJson() {
				return createChartData();
			}
		});
		add(jsonData);

		WPanel root = new WPanel();
		root.setMargin(new Margin(24));
		add(root);
		root.add(new WHeading(HeadingLevel.H1, "WComponents C3 Charting Example"));

		// Dropdown that changes the chart type
		WFieldLayout layout = new WFieldLayout(WFieldLayout.LAYOUT_STACKED);
		root.add(layout);
		layout.addField("Chart type", drpType);

		// Panel that holds the chart
		chartPanel.setMargin(new Margin(24));
		root.add(chartPanel);
		chartPanel.add(configChartScript);

		// AJAX to refresh the chart type
		add(new WAjaxControl(drpType, chartPanel));
	}

	@Override
	protected void preparePaintComponent(final Request request) {
		super.preparePaintComponent(request);
		if (!isInitialised()) {
			String baseUrl = UIContextHolder.getCurrent().getEnvironment().getHostFreeBaseUrl();
			configC3Script.addParameter("baseUrl", baseUrl);
			setInitialised(true);
		}
		setupChartConfig();
	}

	/**
	 * Setups the JSON data for the chart (this could be a service call).
	 *
	 * @return the JSON data
	 */
	private String createChartData() {
		// This JSON data could come from a service call
		return "[{\"name\": \"Jane\", \"data\": [1, 0, 4]}, {\"name\": \"John\", \"data\": [5, 7, 3]}]";
	}

	/**
	 * Setup the handlebars template (ie javascript) to config the chart.
	 */
	private void setupChartConfig() {
		// Setup the template parameters
		configChartScript.addParameter("dataUrl", jsonData.getUrl());
		configChartScript.addParameter("chartType", (String) drpType.getSelected());
		configChartScript.addParameter("chartPanelId", chartPanel.getId());
	}

	/**
	 * Provides JSON as the content.
	 */
	public abstract static class JsonContentAccess implements ContentAccess {

		/**
		 * @return the JSON string as array of bytes
		 */
		@Override
		public byte[] getBytes() {
			String json = getJson();
			return json == null ? null : json.getBytes(StandardCharsets.UTF_8);
		}

		/**
		 * @return the JSON description
		 */
		@Override
		public String getDescription() {
			return "chartdata";
		}

		/**
		 * @return the JSON mime type
		 */
		@Override
		public String getMimeType() {
			return "application/json";
		}

		/**
		 * @return the JSON String
		 */
		public abstract String getJson();
	}

}
