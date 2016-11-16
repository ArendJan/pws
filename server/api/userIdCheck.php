<?php
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

function retour($vale){
  return "{\"check\":\"" . $vale . "\"}";
}

//Alles wat nodig is require_once
require_once("include/checkUserId.php");
require_once("include/log.php");

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);
$userId = $data['UserId'];


if(checkUserId($userId)){
  $output = retour("y");

}else{
  $output = retour("n");

}
echo $output;

logging(basename($_SERVER['PHP_SELF']),$output, $userId);
 ?>
