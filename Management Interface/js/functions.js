// Load the Visualization API and the corechart package.
google.load("visualization", "1", {packages:["corechart"]});
		
$(function () {		

	// Set a callback to run when the Google Visualization API is loaded.
	google.setOnLoadCallback(drawChart);

	
	// Here we need to add import container data
	// function getData()
	// ..
	// .
			
	// Callback that creates and populates a data table,
	// instantiates the pie chart, passes in the data and
	// draws it.
	function drawChart() 
	{
		// Create the data table.
		var data = google.visualization.arrayToDataTable
					([
						['Platforms', 'Containers'],
						['Trein',  1000],
						['Vrachtauto',  2170],
						['Zeeschip',  660],
						['Binnenvaartschip',  1030],
						['Opslag', 100]
					]);

		// Set chart options
		var options = 
						{	
							'width': '100%',
							'height': 500,						
							title: 'Aantal Containers',
							hAxis: 
							{
								title: 'Platformen', 
								titleTextStyle: 
								{
									color: 'red'
								}
							}
						};

		// Instantiate and draw our chart, passing in some options.
		var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
		chart.draw(data, options);
		
		function resizeHandler () {
        chart.draw(data, options);
		}
		if (window.addEventListener) {
			window.addEventListener('resize', resizeHandler, false);
		}
		else if (window.attachEvent) {
			window.attachEvent('onresize', resizeHandler);
		}
	}
});