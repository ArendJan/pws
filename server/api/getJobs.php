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

if (!isset($data["Status"]) || $data["Status"] == ""){
  $status = "new";
} else {
  $status = $data["Status"];
}

if (!isset($data["Type"]) || $data["Type"] == ""){
  $type = "all";
} else {
  $type = $data["Type"];
}

$json_array = array();

if (checkUserId($userId) == false){
  logging(basename($_SERVER['PHP_SELF']),$_POST['JSON'],$userId);
  die ("You forgot your UserId, or gave an invalid UserId!");
}

//Different statements if Type or Status is empty

if ($status == "new" && $type == "all"){
  try{
    $stmt = $conn->prepare('SELECT * FROM jobs WHERE userId = ? AND status = ?');
    $stmt->execute(array($userId, "new"));
  }
  //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
    die;
  }
} elseif ($status == "new"){
  try{
    $stmt = $conn->prepare('SELECT * FROM jobs WHERE userId = ? AND type = ? AND status = ?');
    $stmt->execute(array($userId, $type, "new"));
  }
  //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
    die;
  }
} elseif ($type == "all") {
  try{
    $stmt = $conn->prepare('SELECT * FROM jobs WHERE userId = ? AND status = ?');
    $stmt->execute(array($userId, $status));
  }
  //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
    die;
  }
}  else {
  try{
    $stmt = $conn->prepare('SELECT * FROM jobs WHERE userId = ? AND type = ? AND status = ?');
    $stmt->execute(array($userId, $type, $status));
  }
  //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
    die;
  }
}

$result = $stmt -> fetchAll();
foreach( $result as $row ) {

  switch ($type) {

    case "barcode":
    //echo "Type: " . $row['type'] . "<br>Code: " . $row['qrcode'] . "<br>Status: " . $row['status'] . "<br>JobId: " . $row['ID'] . "<br><br>";
    $row_array['Type'] = $row['type'];
    $row_array['Code'] = $row['barcode'];
    $row_array['Status'] = $row['status'];
    $row_array['JobId'] = $row['ID'];
    array_push($json_array,$row_array);
    break;

    case "text":
    //echo "Type: " . $row['type'] . "<br>Text: " . $row['text'] . "<br>Status: " . $row['status'] . "<br>JobId: " . $row['ID'] . "<br><br>";
    $row_array['Type'] = $row['type'];
    $row_array['Text'] = $row['text'];
    $row_array['Status'] = $row['status'];
    $row_array['JobId'] = $row['ID'];
    array_push($json_array,$row_array);
    break;

    case "list":
    break;

    case "reboot":
    $row_array['Type'] = $row['type'];
    $row_array['Status'] = $row['status'];
    $row_array['JobId'] = $row['ID'];
    array_push($json_array,$row_array);
    break;

    case "shutdown":
    $row_array['Type'] = $row['type'];
    $row_array['Status'] = $row['status'];
    $row_array['JobId'] = $row['ID'];
    array_push($json_array,$row_array);
    break;

    case "update":
    $row_array['Type'] = $row['type'];
    $row_array['Status'] = $row['status'];
    $row_array['JobId'] = $row['ID'];
    array_push($json_array,$row_array);
    break;

    case "all":
    $row_array['Type'] = $row['type'];
    $row_array['Code'] = $row['barcode'];
    $row_array['Text'] = $row['text'];
    $row_array['List'] = $row['list'];
    $row_array['Status'] = $row['status'];
    $row_array['JobId'] = $row['ID'];
    array_push($json_array,$row_array);
  }
}

echo json_encode($json_array);
?>
