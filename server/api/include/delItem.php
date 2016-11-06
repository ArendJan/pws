<?php

function delItem($code, $userId){
  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();
  echo "Barcode: $code ";

  $closedstmt = $conn->prepare("SELECT closed FROM products WHERE barcode = ? AND userId = ?");
  $closedstmt->execute(array($code, $userId));
  $closed = $countstmt->fetchColumn();

  $openstmt = $conn->prepare("SELECT open FROM products WHERE barcode = ? AND userId = ?");
  $openstmt->execute(array($code, $userId));
  $open = $openstmt->fetchColumn();

  echo $closed;
  echo $open;
  if ($open > 0) {
    echo "Open > 0!";
    //Doe -1 bij open product
    $delstmt = $conn->prepare("UPDATE products SET open = open - 1 WHERE barcode = ? AND userId = ?");
    $delstmt->execute(array($code, $userId));
  } else if($closed <= 0) {
    echo "Closed = 0 or < 0 (Which is weird)";
  } else {
    echo "Closed > 0!";
    //Doe -1 bij closed product
    $delstmt = $conn->prepare("UPDATE products SET closed = closed - 1 WHERE barcode = ? AND userId = ?");
    $delstmt->execute(array($code, $userId));
  }
}
?>
