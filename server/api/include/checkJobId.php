<?php
function checkJobId($jobId){

  require_once("log.php");
  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  $params = array('jobId' => $jobId);
  $params = json_encode($params);


  if(empty($jobId)){
    return false;
  } else {
    try {
      $checkStmt = $conn->prepare("SELECT * FROM jobs WHERE ID = ?");
      $checkStmt->execute(array($jobId));
    }
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
