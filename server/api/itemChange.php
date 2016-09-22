<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
require_once("include/newItem.php");

require_once("include/delItem.php");

require_once("include/openItem.php");

require_once("include/checkUserId.php");

$data = json_decode($_POST['JSON'],true);

$code = $data["barcode"];
$action = $data["action"];
$userId = $data['userId'];

if (checkUserId($userId) == true){
  if($action == "add"){
    echo "Adding Product";
    addItem($code,$userId);
  } elseif ($action == "del") {
    echo "Removing Product";
    delItem($code,$userId);
  } elseif ($action == "open") {
    echo "Opening Product";
    openItem($code,$userId);
  }else{
    echo "You forgot your action!";
  }

} else{
  echo "You forgot your userId, or you gave an invalid userId!";
}


 ?>
