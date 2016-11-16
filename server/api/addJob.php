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

if (!isset($data["Type"]) || empty($data["Type"])){
  die("You have to add the type of the job!");
}
$type = $data["Type"];
$barcode = $data["Barcode"];
$text = $data["Text"];
$list = $data["List"];

if (checkUserId($userId) == false){
  die ("You forgot your UserId, or gave an invalid UserId!");
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
