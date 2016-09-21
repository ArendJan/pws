<?php

require_once("include/newItem.php");
require_once("include/delItem.php");
require_once("include/openItem.php");

$data = json_decode($_POST['JSON'],true);

$code = $data["barcode"];
$action = $data["action"];
$userId = $data['userId'];

if($action == "add"){
  echo "Adding Product";
  addItem($code,$userId);
} elseif ($action == "del") {
  echo "Removing Product";
  del($code);
} elseif ($action == "open") {
  echo "Opening Product";
  upate($code);
}

 ?>
