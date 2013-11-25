<!doctype html>  
<html lang="en">  
<head>  
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
	<title>Management Interface</title>  
	<meta name="author" content="Joshua Bergsma, Remco de Bruin, Melinda de Roo, Arjen Pander, Jeffrey Harders, Yme van der Graaf">  
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  
	<meta name="HandheldFriendly" content="true">  
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">  
	<link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Simonetta:400,900|Balthazar">  
	<link rel="stylesheet" type="text/css" href="css/styles.css">  
	<link rel="stylesheet" type="text/css" href="css/responsive.css">  
	<!--[if lt IE 9]>  
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>  
		<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>  
	<![endif]--> 
  
	<!--Load the AJAX API-->
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>

	<!-- Grab Google CDN's jQuery. fall back to local if necessary -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
	<script>!window.jQuery && document.write(unescape('%3Cscript src="js/libs/jquery-1.4.2.js"%3E%3C/script%3E'))</script>

	<!--Load ColumnChart script-->
	<script src="js/functions.js"></script>
</head>
<body>
	<div id="wrapper" itemscope itemtype="http://schema.org/Product">
		<header class="clearfix">  
			<div id="info">
				<h1><span itemprop="name">Containing</span></h1>  
				<h2><span itemprop="description">Omschrijving..</span></h2>  
			</div>
		</header>
		<section id="ColumnChart">
			<div id="chart_div"></div>
		</section>		
	</div>
</body>
</html>