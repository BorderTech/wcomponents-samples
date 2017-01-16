//
// Define "FruitChart" that creates the chart.
//
define(["d3", "c3", "wc/ajax/ajax"], function (d3, c3, ajax) {

    function FruitChart(url, id, type)
    {
	var chart = c3.generate({
	    bindto: id,
	    data: {
		columns: [],
		type: type
	    },
	    axis: {
		x: {
		    type: 'category',
		    categories: ['Apples', 'Bananas', 'Oranges'],
		    label: 'Fruit eaten'
		},
		y: {
		    label: 'How much eaten'
		}
	    },
	    oninit: requestData
	});

	function requestData()
	{
	    var callback = function (data) {
			var series, cols, item, c3Struct;
			cols = [];
			// Change the JSON data into the C3 column data structure
			var series = JSON.parse(data);
			series.forEach(function (next) {
				// Get the array of data
				item = next.data;
				// Put the name used for the data at the start
				item.unshift(next.name);
				// Add to columns
				cols.push(item);
			});
			// Create the c3 Columns Structure
			c3Struct = {
				columns: cols
			};
			// Load data into chart
			chart.load(c3Struct);
	    };
	    ajax.simpleRequest({url: url, callback: callback});
	}

    }

    return function (url, containerId, type) {
		new FruitChart(url, containerId, type);
    };

});

