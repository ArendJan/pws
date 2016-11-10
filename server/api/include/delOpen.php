<?php

function delOpen($code, $userId){

  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();
  echo "Barcode: $code ";

  $countstmt = $conn->prepare("SELECT open FROM products WHERE barcode = ? AND userId = ?");
  $countstmt->execute(array($code, $userId));
  $ding = $countstmt->fetch();

  $open = $ding['Open'];

  if ($open > 0){
    //Doe -1 bij open product
    $delstmt = $conn->prepare("UPDATE products SET open = open - 1 WHERE barcode = ? AND userId = ?");
  } else {
      die("Open = 0 or < 0 (Which is weird)");
  }
  $delstmt->execute(array($code, $userId));
}
?>
