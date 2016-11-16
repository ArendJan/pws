<?php
function addItem($code, $userId){

  require_once("log.php");
  require_once(dirname(__FILE__)."/../../php/start.php");
  require_once(dirname(__FILE__)."/getItem.php");
  $conn = db();

  //Check of product al bestaat
  try{
    $checkstmt = $conn->prepare("SELECT barcode FROM products WHERE barcode = ? AND userId = ?");
    $checkstmt->execute(array($code, $userId));  $checkstmt = $conn->prepare("SELECT barcode FROM products WHERE barcode = ? AND userId = ?");
      $checkstmt->execute(array($code, $userId));
  }
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $code, $userId, $e);
    die();
  }

  if($checkstmt->rowCount() > 0){
    //Doe +1 bij aantal van product
    try{
      $upstmt = $conn->prepare("UPDATE products SET closed = closed + 1 WHERE barcode = ? AND userID = ?");
      $upstmt->execute(array($code, $userId));
    }
    catch (PDOException $e){
      errorLogging(basename($_SERVER['PHP_SELF']), $code, $userId, $e);
      die();
    }
  } else {
    //Voeg product toe
      $desc = getTags($code);
    try {
      $addstmt = $conn->prepare("INSERT INTO products (barcode, closed, description, userId) VALUES (?,1,?,?)");
      $addstmt->execute(array($code,$desc,$userId));
    }
    catch(PDOException $e)
    {
      errorLogging(basename($_SERVER['PHP_SELF']), $code, $userId, $e);
      die();
    }
  }
}
?>
