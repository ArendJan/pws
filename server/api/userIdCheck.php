<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

function retour($vale){
  echo "{\"check\":\"" . $vale . "\"}"; 
}

require_once("include/checkUserId.php");
if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);
$userId = $data['UserId'];


if(checkUserId($userId)){
  retour("y");
  $output = "y";
}else{
  retour("n");
  $output = "n";
}

require_once("include/log.php");

logging(basename($_SERVER['PHP_SELF']),$output, $userId);
 ?>