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

if (checkUserId($data['UserId']) == false){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], "", "Forgot userId, or invalid userId");
  die;
}

$userId = $data['UserId'];

logging(basename($_SERVER['PHP_SELF']),$_POST['JSON'],$userId);

if (!isset($data["Type"]) || empty($data["Type"])){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, "No Type");
  die;
}
$type = $data["Type"];

if (!isset($data["Barcode"]) || empty($data["Barcode"])){
  $barcode = "";
} else{
  $barcode = $data["Barcode"];
}

if (!isset($data["Text"]) || empty($data["Text"])){
  $text = "";
} else{
  $text = $data["Text"];
}

if (!isset($data["Items"]) || empty($data["Items"])){
  $list = "";
} else{
  $list = json_encode($data["Items"]);
}

try{
  $stmt = $conn->prepare("INSERT INTO jobs (userId, type, barcode, text, list, status) VALUES (?,?,?,?,?,?)");
  $stmt->execute(array($userId, $type, $barcode, $text, $list, "new"));
}
//Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
catch (PDOException $e){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
  die;
}

$last_id = $conn->lastInsertId('jobs');
$arr = array('JobId' => $last_id);
echo json_encode($arr);

?>
