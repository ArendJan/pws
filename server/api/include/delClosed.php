<?php

function delClosed($code, $userId){
  require_once("log.php");
  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  try {
    $countstmt = $conn->prepare("SELECT closed FROM products WHERE barcode = ? AND userId = ?");
    $countstmt->execute(array($code, $userId));
  }
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $code, $userId, $e);
    die();
  }

  $ding = $countstmt->fetch();

  $closed = $ding['Closed'];

    if($closed > 0) {
      //Doe -1 bij closed product
      $delstmt = $conn->prepare("UPDATE products SET closed = closed - 1 WHERE barcode = ? AND userId = ?");
    } else {
      die("Ammount = 0 or < 0 (Which is weird)");
    }
    try{
      $delstmt->execute(array($code, $userId));
    }
    catch (PDOException $e){
      errorLogging(basename($_SERVER['PHP_SELF']), $code, $userId, $e);
      die();
    }

}
?>
