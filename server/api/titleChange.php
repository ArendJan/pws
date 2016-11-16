<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

//Alles wat nodig is require_once
require_once("include/checkUserId.php");
require_once("include/log.php");

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);

$userId = $data['UserId'];

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
