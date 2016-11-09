<?php

function logging($script, $params, $userId){

  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  $logstmt = $conn->prepare("INSERT INTO logging (time, script, params, userId) VALUES (?,?,?,?)");
  $logstmt->execute(array(date('Y-m-d H:i:s'),$script,$params,$userId));
}

 ?>
