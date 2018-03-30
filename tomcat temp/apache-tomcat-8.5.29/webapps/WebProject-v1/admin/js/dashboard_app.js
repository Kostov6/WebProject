// Welcome to the RazorFlow Dashbord Quickstart. Simply copy this "dashboard_quickstart"
// to somewhere in your computer/web-server to have a dashboard ready to use.
// This is a great way to get started with RazorFlow with minimal time in setup.
// However, once you're ready to go into deployment consult our documentation on tips for how to 
// maintain the most stable and secure 

StandaloneDashboard(function(db){
	// YOU CAN DELETE THE ENTIRE CONTENTS OF THIS FUNCTION AND CUSTOMIZE
	// AS PER YOUR REQUIREMENT. 
	// These components are simply here to give you a quick introduction of how RazorFlow Works

	db.setDashboardTitle ("My Dashboard");

	// Add a chart to the dashboard. This is a simple chart with no customization.
	var chart = new ChartComponent();
	chart.setCaption("Sales");
	chart.setDimensions (6, 6);	
	chart.setLabels (["1", "2", "3","4"]);
	chart.addSeries ([43, 66, 79,73]);
	db.addComponent (chart);

	// You can add multiple charts to the same dashboard. In fact you can add many 
	// different types of components. Check out the docs at razorflow.com/docs 
	// to read about all the types of components.
	// 
	// This is another chart with additional parameters passed to "addSeries" to 
	// make customizations like change it to a line chart, and add "$" to indicate currency
	var chart2 = new ChartComponent();
	chart2.setCaption("Shadowverse deck values");
	chart2.setDimensions (6, 6);	
	chart2.setLabels (["1", "2", "3" , "4", "5", "6" , "7", "8", "9" , "10", "11", "12" , "13", "14", "15" ]);
	chart2.addSeries ([0, 53, 64, 75, 80, 80, 77, 74, 76, 73, 69, 65, 61, 58, 56], {
		numberPrefix: "",
		seriesDisplayType: "line"
	});
	db.addComponent (chart2);
});