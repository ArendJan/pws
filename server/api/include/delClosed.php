<?php

function delClosed($code, $userId){
  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();
  echo "Barcode: $code ";

  $countstmt = $conn->prepare("SELECT closed FROM products WHERE barcode = ? AND userId = ?");
  $countstmt->execute(array($code, $userId));
  $ding = $countstmt->fetch();

  $closed = $ding['Closed'];

    if($closed > 0) {
      echo "Ammount > 0!";
      //Doe -1 bij closed product
      $delstmt = $conn->prepare("UPDATE products SET closed = closed - 1 WHERE barcode = ? AND userId = ?");
    } else {
      die("Ammount = 0 or < 0 (Which is weird)");
    }
  $delstmt->execute(array($code, $userId));
}
?>
