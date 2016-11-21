<?php

function openItem($code, $userId){

  //Alles wat nodig is require_once
  require_once("log.php");
  require_once(dirname(__FILE__)."/../../php/start.php");

  $conn = db();

  //Functie parameteres doorgooien naar JSON voor in DB
  $params = array('code' => $code, 'userId' => $userId);
  $params = json_encode($params);

  try {
    $openstmt = $conn->prepare("UPDATE products SET open = open + 1 WHERE barcode = ? AND userId = ?");
    $openstmt->execute(array($code, $userId));
  }
  //Wanneer er een error komt met de query, komt dit in de erroLogging tabel dmv de functie errorLogging in log.php
  catch(PDOException $e)
  {
    errorLogging(basename($_SERVER['PHP_SELF']), $params, $userId, $e);
    die();
  }
  $GLOBALS['doLog'] = "y";
}
?>
