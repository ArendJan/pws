<?php

function errorLogging($script, $params, $userId, $e){

  require_once(dirname(__FILE__)."/../../php/start.php");

  //Functie parameteres doorgooien naar JSON voor in DB
  $params = json_encode(array('script' => $script, 'params' => $params, '$userId' => $userId));

  //Functie parameteres doorgooien naar JSON voor in error return
  $error = json_encode(array('script' => $script, 'params' => $params, 'userId' => $userId,  'error' => $e));

  $conn = db();

  try{
    $logstmt = $conn->prepare("INSERT INTO errorLogging (time, script, params, userId, error) VALUES (?,?,?,?,?)");
    $logstmt->execute(array(date('Y-m-d H:i:s'),$script,$params,$userId,$e));
  }
  //Hier niet errorLogging want anders oneindige loop
  catch (PDOException $e){
    die ("An error occured during error logging:S $e");
  }

  $arr = array('error' => $e);
  echo json_encode($arr);
}

function logging($script, $params, $userId){

  //Alles wat nodig is require_once
  require_once(dirname(__FILE__)."/../../php/start.php");

  $conn = db();

  try{
    $logstmt = $conn->prepare("INSERT INTO logging (time, script, params, userId) VALUES (?,?,?,?)");
    $logstmt->execute(array(date('Y-m-d H:i:s'),$script,$params,$userId));
  }
  catch(PDOException $e){
    //errorLogging(basename($_SERVER['PHP_SELF']), $params, $userId, $e);
    die ("You have an error $e");
  }
}
?>
