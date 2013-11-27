<?php

// Read the file last modified time and output it.
$filename = 'uploads/statistics.json';
if (file_exists($filename)) 
{
	echo "Laatste update: " . date ("d-m-Y H:i:s.", filemtime($filename));
}
else
{
	echo "File is missing...";
}

?>