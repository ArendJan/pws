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
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], "", "No _POST['JSON']");
  die;
}

$data = json_decode($_POST['JSON'],true);

if (checkUserId($_POST['UserId']) == false){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], "", "Forgot userId, or invalid userId");
  die;
}

$userId = $data['UserId'];

logging(basename($_SERVER['PHP_SELF']),$_POST['JSON'],$userId);

if (!isset($data["Barcode"]) || empty($data["Barcode"])){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, "You forgot a barcode");
  die;
}
$barcode = $data["Barcode"];

if (!isset($data["Title"]) || empty($data["Title"])){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, "You forgot a title");
  die;
}

$title = $data["Title"];

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
