<?php
function checkJobId($jobId){

  //Alles wat nodig is require_once
  require_once("log.php");
  require_once(dirname(__FILE__)."/../../php/start.php");

  $conn = db();

  //Functie parameteres doorgooien naar JSON voor in DB
  $params = array('jobId' => $jobId);
  $params = json_encode($params);


  if(empty($jobId)){
    return false;
  } else {
    try {
      $checkStmt = $conn->prepare("SELECT * FROM jobs WHERE ID = ?");
      $checkStmt->execute(array($jobId));
    }
    //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
    catch(PDOException $e){
      errorLogging(basename($_SERVER['PHP_SELF']), $params, "", $e);
      die;
    }
    if($checkStmt->rowCount() == 1){
      return true;
    } else{
      return false;
    }
  }
}
?>
