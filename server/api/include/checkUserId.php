<?php
function checkUserId($userId){

  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  if(empty($userId)){
    return false;
  } else {
    $checkStmt = $conn->prepare("SELECT * FROM users WHERE userId = :userId");
    $checkStmt->bindParam(':userId', $userId);
    $checkStmt->execute();
    if($checkStmt->rowCount() == 1){
      return true;
    } else{
      return false;
    }
  }
}
 ?>
