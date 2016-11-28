<?php
function addItem($code, $userId){

  //Alles wat nodig is require_once
  require_once("log.php");
  require_once(dirname(__FILE__)."/../../php/start.php");
  require_once(dirname(__FILE__)."/getItem.php");

  $conn = db();

  //Functie parameteres doorgooien naar JSON voor in DB
  $params = array('code' => $code, 'userId' => $userId);
  $params = json_encode($params);

  //Check of product al bestaat
  try{
    $checkstmt = $conn->prepare("SELECT barcode FROM products WHERE barcode = ? AND userId = ?");
    $checkstmt->execute(array($code, $userId));
  }
  //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $params, $userId, $e);
    die();
  }

  if($checkstmt->rowCount() > 0){
    //Doe +1 bij aantal van product
    try{
      $upstmt = $conn->prepare("UPDATE products SET closed = closed + 1 WHERE barcode = ? AND userID = ?");
      $upstmt->execute(array($code, $userId));
    }
    catch (PDOException $e){
      errorLogging(basename($_SERVER['PHP_SELF']), $params, $userId, $e);
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
      errorLogging(basename($_SERVER['PHP_SELF']), $params, $userId, $e);
      die();
    }
  }
  $GLOBALS['doLog'] = "y";
}
?>
