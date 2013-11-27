// Load the Visualization API and the corechart package.
google.load("visualization", "1", {packages:["corechart"]});

$(function () 
{      
    // Set a callback to run when the Google Visualization API is loaded.
	google.setOnLoadCallback(drawChart);
			
	// Callback that creates and populates a data table,
	// instantiates the column chart, passes in the data and
	// draws it.
	function drawChart() 
	{		
		// Get the jsonData		
		var jsonData = $.ajax 
		(
			{
				url: "getData.php",
				dataType:"json",
				async: false
			}
		).responseText;

		// Create our data table out of JSON data loaded from server.
		var data = new google.visualization.DataTable(jsonData);

		// Get the lastmodifiedTime
		var hAxisTitle = $.ajax
		(
			{
				url: "getTime.php",
				dataType:"string",
				async: false
			}
		).responseText;
		
		// Set chart options
		var options = 
		{	
			'width': '100%',
			'height': 500,
			animation:
			{
				duration: 1000,
				easing: 'out',
			},
			hAxis: 
			{
				title: 	hAxisTitle, 
				titleTextStyle: 
				{
					color: 'red'
				}
			}
		};

		// Instantiate and draw our chart, passing in options.
		var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
		chart.draw(data, options);
		
		// Make the chart responsive.
		function resizeHandler() 
		{
			chart.draw(data, options);
		}
		if (window.addEventListener) 
		{
			window.addEventListener('resize', resizeHandler, false);
		}
		else if (window.attachEvent) 
		{
			window.attachEvent('onresize', resizeHandler);
		}
		
		// Renew the chart every 10 seconds.
		setInterval(drawChart, 10000);
	}
});