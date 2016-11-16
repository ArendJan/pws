<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

//Alles wat nodig is require_once
require_once('../php/start.php');
require_once("include/checkUserId.php");
require_once("include/checkJobId.php");

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

if (checkUserId($_POST['UserId']) == false){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], "", "Forgot userId, or invalid userId");
  die;
}

require_once("include/log.php");
logging(basename($_SERVER['PHP_SELF']),$_POST['JSON'],$userId);

if (!isset($data["Status"]) || $data["Status"] == ""){
  $status = "done";
} else {
  $status = $data["Status"];
}

if ($status == "new" || $status == "done"){
  if (checkUserId($_POST['UserId']) == false){
    errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], "", "Forgot userId, or invalid userId");
    die;
  }

  $userId = $data['UserId'];

  if (checkJobId($jobId) == false){
    errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, "Forgot jobId, or invalid jobId");
    die;
  }

  $jobId = $data['JobId'];

try{
  $upstmt = $conn->prepare('UPDATE jobs SET status = ? WHERE userId = ? AND ID = ?');
  $upstmt->execute(array($status, $userId, $jobId));
}
//Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
catch (PDOException $e){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
  die;
}

} else {
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, "Invalid status");
  die;
}

$arr = array('JobId' => $jobId, 'Status' => $status);
echo json_encode($arr);

?>
