<?php
function checkJobId($jobId){

  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  if(empty($jobId)){
    return false;
  } else {
    $checkStmt = $conn->prepare("SELECT * FROM jobs WHERE ID = ?");
    $checkStmt->execute(array($jobId));
    if($checkStmt->rowCount() == 1){
      return true;
    } else{
      return false;
    }
  }
}
 ?>
