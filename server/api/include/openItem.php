<?php

function openItem($code, $userId){
  require_once("log.php");
  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  $params = array('code' => $code, 'userId' => $userId);
  $params = json_encode($params);

  try {
    $openstmt = $conn->prepare("UPDATE products SET open = open + 1 WHERE barcode = ? AND userId = ?");
    $openstmt->execute(array($code, $userId));
  }
  catch(PDOException $e)
  {
    errorLogging(basename($_SERVER['PHP_SELF']), $params, $userId, $e);
    die();
  }
}
?>
