//
// Demonstrates using highcharts. Highcharts requires a license so provide the
// highcharts library and adjust the path to the library in the define
// statement below.
//
define(["js/lib/highcharts-5.0.0.js", "wc/ajax/ajax"], function (charts, ajax) {

    function FruitChart(url, id, type)
    {
	var chart = new Highcharts.Chart({
	    chart: {
		renderTo: id,
		type: type,
		events: {
		    load: requestData
		}
	    },
	    title: {
		text: 'Fruit Consumption'
	    },
	    xAxis: {
		categories: ['Apples', 'Bananas', 'Oranges']
	    },
	    yAxis: {
		title: {
		    text: 'Fruit eaten'
		}
	    }
	});

	function requestData()
	{
	    var callback = function (data) {
		var series = JSON.parse(data);
		series.forEach(function (next) {
		    chart.addSeries(next);
		});
	    };
	    ajax.simpleRequest({url: url, callback: callback});
	}

    }

    return function (url, containerId, type) {
	new FruitChart(url, containerId, type);
    };

});

