<?php 

// Read the json formatted text file and output it.
$string = file_get_contents("uploads/statistics.json");
echo $string;

?>