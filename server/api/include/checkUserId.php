<?php
function checkUserId($userId){
  require_once("log.php");
  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  if(empty($userId)){
    return false;
  } else {
    try{
      $checkStmt = $conn->prepare("SELECT * FROM users WHERE userId = ?");
      $checkStmt->execute(array($userId));
    }
    catch (PDOException $e){
      errorLogging(basename($_SERVER['PHP_SELF']), "", $userId, $e);
      die();
    }
    if($checkStmt->rowCount() == 1){
      return true;
    } else{
      return false;
    }
  }
}
 ?>
