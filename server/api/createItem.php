<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include_once('../php/start.php');
require_once("include/checkUserId.php");

$conn = db();

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);

$userId = $data['UserId'];

require_once("include/log.php");
logging(basename($_SERVER['PHP_SELF']),$_POST['JSON'],$userId);

if (!isset($data["Barcode"]) || empty($data["Barcode"])){
  die("You have to add the barcode of the item!");
}
if (!isset($data["Title"]) || empty($data["Title"])){
  die("You have to add the title of the item!");
}

$barcode = $data["Barcode"];
$title = $data["Title"];


if (checkUserId($userId) == false){
  die ("You forgot your UserId, or gave an invalid UserId!");
}



$stmt = $conn->prepare("INSERT INTO `products` (`ID`, `userId`, `barcode`, `description`, `ammount`, `open`, `closed`) VALUES (NULL, ?, ?, ?, '0', '0', '0');");
$stmt->execute(array($userId, $barcode, $title));

$last_id = $conn->lastInsertId('products');
$arr = array('Id' => $last_id);
echo json_encode($arr);

?>
