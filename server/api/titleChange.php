<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

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

if (!isset($data["Title"]) || $data['Title'] == ""){
  die("Please provide a title");
}

  $title = $data["Title"];

if (!checkUserId($userId)){
  die('You forgot your userId, or you gave an invalid userId!');
}
require_once(dirname(__FILE__)."/../php/start.php");
$conn = db();

$open2stmt = $conn->prepare("UPDATE products SET description = ? WHERE barcode = ? AND userId = ?");
$open2stmt->execute(array($title, $code, $userId));

require_once(dirname(__FILE__)."/include/returnItem.php");
echo returnItem($barcode);

 ?>
