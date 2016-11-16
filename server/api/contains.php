<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

//Alles wat nodig is require_once
require_once("include/log.php");
require_once('../php/start.php');
require_once("include/checkUserId.php");

$conn = db();

$return_arr = array();

if (!isset($_POST['JSON'])){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], "", "No _POST['JSON']");
  die;
}

$data = json_decode($_POST['JSON'],true);

logging(basename($_SERVER['PHP_SELF']),$_POST['JSON'],$userId);

if (!isset($data["Sort"]) || $data["Sort"] == ""){
  $sort = "opened+closed";
} else {
  $sort = $data["Sort"];
}

if (checkUserId($_POST['UserId']) == false){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], "", "Forgot userId, or invalid userId");
  die;
}

$userId = $data['UserId'];

if($sort == "everything"){
  try{
    $stmt = $conn->prepare('SELECT * FROM products WHERE userId = ?');
    $stmt->execute(array($userId));
  }
  //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
    die;
  }
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $closed = $row['closed'];
    $open = $row['open'];
    $amount = $open + $closed;

    //echo $row['description'] . ":<br>Ammount: " . $row['ammount'] . "<br>Closed: " . $closed . "<br>Opened: " . $row['open'] . "<br><br>";

    $row_array['Id'] = intval($row['ID']);
    $row_array['Name'] = $row['description'];
    $row_array['Barcode'] = strval($row['barcode']);
    $row_array['Ammount'] = $amount;
    $row_array['Closed'] = intval($row['closed']);
    $row_array['Open'] = intval($row['open']);

    array_push($return_arr,$row_array);
  }
}elseif ($sort == "opened") {
  try{
    $stmt = $conn->prepare('SELECT * FROM products WHERE open > 0 AND userId = ?');
    $stmt->execute(array($userId));
  }
  //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
    die;
  }
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {

    //echo $row['description'] . ":<br>Opened: " . $row['open'] . "<br><br>";
    $row_array['Id'] = intval($row['ID']);
    $row_array['Name'] = $row['description'];
    $row_array['Barcode'] = strval($row['barcode']);
    $row_array['Open'] = intval($row['open']);

    array_push($return_arr,$row_array);
  }
}elseif ($sort == "closed") {
  try{
    $stmt = $conn->prepare('SELECT * FROM products WHERE closed > 0 AND userId = ?');
    $stmt->execute(array($userId));
  }
  //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
    die;
  }
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {

    //echo $row['description'] . ":<br>Closed: " . $closed . "<br><br>";
    $row_array['Id'] = intval($row['ID']);
    $row_array['Name'] = $row['description'];
    $row_array['Barcode'] = strval($row['barcode']);
    $row_array['Closed'] = intval($row['closed']);

    array_push($return_arr,$row_array);
  }
}elseif ($sort == "opened+closed")  {
  try{
    $stmt = $conn->prepare('SELECT * FROM products WHERE closed > 0 OR open > 0 AND userId = ?');
    $stmt->execute(array($userId));
  }
  //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
    die;
  }
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {

    //echo $row['description'] . ":<br>Closed " . $closed . "<br>Open: " . $row['open'] . "<br><br>";

    $row_array['Id'] = intval($row['ID']);
    $row_array['Name'] = $row['description'];
    $row_array['Barcode'] = strval($row['barcode']);
    $row_array['Closed'] = intval($row['closed']);
    $row_array['Open'] = intval($row['open']);

    array_push($return_arr,$row_array);
  }
}

echo json_encode($return_arr);
?>
