<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

//Alles wat nodig is require_once
require_once('../php/start.php');
require_once("include/checkUserId.php");
require_once("include/log.php");

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

$json_array = array();

try{
  $stmt = $conn->prepare('SELECT * FROM logging WHERE userId = ? ORDER BY ID DESC ');
  $stmt->execute(array($userId));
}
//Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
catch (PDOException $e){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
  die;
}

$result = $stmt -> fetchAll();
foreach( $result as $row ) {

  $row_array['ID'] = $row['ID'];
  $row_array['Time'] = $row['time'];
  $row_array['Script'] = $row['script'];
  $row_array['Params'] = json_decode($row['params']);
  $row_array["UserId"] = $row["userId"];
  if($row["script"]=="markJob.php"){
    $jobId2 = $row_array["Params"]->JobId;

    $stmt2 = $conn->prepare('SELECT * FROM jobs WHERE userId = ? AND ID = ?');
    if ($stmt2->execute(array($userId, $jobId2))) {

      $rowX = $stmt2->fetch();


        $job = array();
        $job["ID"] = $rowX["ID"];
        $job["Type"] = $rowX["type"];
        $job["Barcode"] = $rowX["barcode"];
        $job["Text"] = $rowX["text"];
        $job["List"] = $rowX["list"];
        $job["Status"] = $rowX["status"];
        //print_r
        //print_r($rowX);
        $row_array["JobDetails"] = $job;
        //print_r($job);

    }else {

    }
  }
  if($row["script"]=="itemChange.php"){
    $barcode = $row_array["Params"]->Barcode;

    $stmt2 = $conn->prepare('SELECT * FROM products WHERE userId = ? AND barcode = ?');
    if ($stmt2->execute(array($userId, $barcode))) {

      $rowX = $stmt2->fetch();


        $item = array();
        $item["Title"] = $rowX["description"];
        $item["Barcode"] = $rowX["barcode"];
        $item["Open"] = $rowX["open"];
        $item["Closed"] = $rowX["closed"];
        $item["ID"] = $rowX["ID"];
        //print_r
        //print_r($rowX);
        $row_array["ItemDetails"] = $item;
        //print_r($job);

    }else {

    }
  }
  array_push($json_array,$row_array);

}

echo json_encode($json_array);
?>
