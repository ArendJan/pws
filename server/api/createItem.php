<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

//Alles wat nodig is require_once
require_once("include/log.php");
require_once('../php/start.php');
require_once("include/checkUserId.php");

$conn = db();

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);

$userId = $data['UserId'];

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


try{
  $stmt = $conn->prepare("INSERT INTO `products` (`ID`, `userId`, `barcode`, `description`, `ammount`, `open`, `closed`) VALUES (NULL, ?, ?, ?, '0', '0', '0');");
  $stmt->execute(array($userId, $barcode, $title));
}
//Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
catch (PDOException $e){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
  die;
}

$last_id = $conn->lastInsertId('products');
$arr = array('Id' => $last_id);
echo json_encode($arr);

?>
