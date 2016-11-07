<?php

include_once('../../php/start.php');
$conn = db();

function log($script, $params){
  $logstmt = $conn->prepare("INSERT INTO log (time, script, params) VALUES (?,?,?)");
  $logstmt->execute(array(date('Y-m-d H:i:s'),$script,$params));
}

 ?>
