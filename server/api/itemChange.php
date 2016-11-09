<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

require_once("include/log.php");
logging(basename($_SERVER['PHP_SELF']),$_POST['JSON']);

require_once("include/newItem.php");

require_once("include/delItem.php");
require_once("include/delOpen.php");
require_once("include/delClosed.php");

require_once("include/openItem.php");

require_once("include/checkUserId.php");

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);

$userId = $data['UserId'];

require_once("include/log.php");
logging(basename($_SERVER['PHP_SELF']),$_POST['JSON'],$userId);

if (!isset($data["Barcode"])){
  die("You have to post your barcode!");
} else {
  $code = $data["Barcode"];
}

if (!isset($data["Action"]) || $data['Action'] == ""){
  $action = "add";
} else {
  $action = $data["Action"];
}

if (checkUserId($userId) == false){
  die('You forgot your userId, or you gave an invalid userId!');
}
  if($action == "add"){
    echo "Adding Product";
    addItem($code,$userId);
  } else if ($action == "del") {
    echo "Removing Product";
    delItem($code,$userId);
  } else if ($action == "open") {
    echo "Opening Product";
    openItem($code,$userId);
  } else if ($action == "delOpen") {
    echo "Deleting Open Product";
    delOpen($code,$userId);
  } else if ($action == "delClosed") {
    echo "Deleting Closed Product";
    delClosed($code,$userId);
  }else{
    echo "Not a correct action";
  }

 ?>
