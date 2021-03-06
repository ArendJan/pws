<?php

function delOpen($code, $userId){

  //Alles wat nodig is require_once
  require_once("log.php");
  require_once(dirname(__FILE__)."/../../php/start.php");

  $conn = db();

  //Functie parameteres doorgooien naar JSON voor in DB
  $params = array('code' => $code, 'userId' => $userId);
  $params = json_encode($params);

try{
  $countstmt = $conn->prepare("SELECT open FROM products WHERE barcode = ? AND userId = ?");
  $countstmt->execute(array($code, $userId));
}
//Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
catch (PDOException $e){
  errorLogging(basename($_SERVER['PHP_SELF']), $params, $userId, $e);
  die();
}
  $ding = $countstmt->fetch();

  $open = $ding['open'];

  if ($open > 0){
    //Doe -1 bij open product
    $delstmt = $conn->prepare("UPDATE products SET open = open - 1 WHERE barcode = ? AND userId = ?");
  } else {
      errorLogging(basename($_SERVER['PHP_SELF']), $params, $userId, "Open = 0 or < 0");
      die();
  }
  try{
    $delstmt->execute(array($code, $userId));
  }
  catch (PDOException $e){
    errorLogging(basename($_SERVER['PHP_SELF']), $params, $userId, $e);
    die();
  }
  $GLOBALS['doLog'] = "y";
}
?>
