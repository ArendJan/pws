<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

//Alles wat nodig is require_once
require_once("include/checkUserId.php");
require_once("include/log.php");

if (!isset($_POST['JSON'])){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], "", "No _POST['JSON']");
  die;
}

$data = json_decode($_POST['JSON'],true);

if (checkUserId($_POST['UserId']) == false){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], "", "Forgot userId, or invalid userId");
  die;
}

if (checkUserId($_POST['UserId']) == false){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], "", "Forgot userId, or invalid userId");
  die;
}

$userId = $data['UserId'];

logging(basename($_SERVER['PHP_SELF']),$_POST['JSON'],$userId);

if (!isset($data["Barcode"])){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, "Forgot a barcode");
  die;
} else {
  $code = $data["Barcode"];
}

if (!isset($data["Title"]) || $data['Title'] == ""){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, "Forgot a title");
  die;
}

  $title = $data["Title"];

require_once(dirname(__FILE__)."/../php/start.php");
$conn = db();

try{
  $open2stmt = $conn->prepare("UPDATE products SET description = ? WHERE barcode = ? AND userId = ?");
  $open2stmt->execute(array($title, $code, $userId));
}
//Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
catch (PDOException $e){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
  die;
}

require_once(dirname(__FILE__)."/include/returnItem.php");
echo returnItem($barcode);

 ?>
