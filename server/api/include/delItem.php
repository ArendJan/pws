<?php

function delItem($code, $userId){
  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();
  echo "Barcode: $code ";

  $countstmt = $conn->prepare("SELECT ammount, open FROM products WHERE barcode = ? AND userId = ?");
  $countstmt->execute(array($code, $userId));
  $ding = $countstmt->fetch();

  $ammount = $ding['Ammount'];
  $open = $ding['Open'];

  echo $ammount;
  echo $open;

  if ($open > 0){
    //Doe -1 bij open
    $delstmt = $conn->prepare("UPDATE products SET open = open - 1 WHERE barcode = ? AND userId = ?");
  } else {
    if($ammount <= 0) {
      die("Ammount = 0 or < 0 (Which is weird)");
    } else {
      echo "Ammount > 0!";
      //Doe -1 bij aantal van product
      $delstmt = $conn->prepare("UPDATE products SET ammount = ammount - 1 WHERE barcode = ? AND userId = ?");
    }
  }
  $delstmt->execute(array($code, $userId));
}
?>
