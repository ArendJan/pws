<?php

function delItem($code, $userId){
  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();
  echo "Barcode: $code ";

  $countstmt = $conn->prepare("SELECT ammount FROM products WHERE barcode = :barcode AND userId = :userId");
  $countstmt->bindParam(':barcode', $code);
  $countstmt->bindParam(':userId', $userId);
  $countstmt->execute();

  echo "lel";
  $count = $countstmt->fetchColumn();
echo $count;
  if($count <= 0) {
    echo "Ammount = 0 or < 0 (Which is weird)";
  } else {
    echo "Ammount > 0!";
    //Doe -1 bij aantal van product
    $delstmt = $conn->prepare("UPDATE products SET ammount = ammount - 1 WHERE barcode = ? AND userId = ?");
    $delstmt->execute(array($code, $userId));
  }
}
?>
