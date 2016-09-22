<?php
function checkUserId($userId){

  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  if(empty($userId){
    return false;
  } else {
    $userIdStmt = $conn->prepare("SELECT * FROM users WHERE userId = :userId");
    $userIdStmt->bindParam(':userId', $userId);
    $check = $userIdStmt->fetchColumn();
    if($check == 0){
      return false;
    } elseif($check > 0){
      return true;
    }
  }
}
 ?>
