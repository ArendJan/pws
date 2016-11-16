<?php

function logging($script, $params, $userId){

  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  $logstmt = $conn->prepare("INSERT INTO logging (time, script, params, userId) VALUES (?,?,?,?)");
  $logstmt->execute(array(date('Y-m-d H:i:s'),$script,$params,$userId));
}

function errorLogging($script, $params, $userId, $e){

  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  $logstmt = $conn->prepare("INSERT INTO logging (time, script, params, userId, error) VALUES (?,?,?,?,?)");
  $logstmt->execute(array(date('Y-m-d H:i:s'),$script,$params,$userId,$e));

  echo "An error occured! $e"
}

 ?>
