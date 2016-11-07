<?php

function openItem($code, $userId){
  require_once(dirname(__FILE__)."/../../php/start.php");
  $conn = db();

  $closedstmt = $conn->prepare("SELECT closed FROM products WHERE barcode = ? AND userId = ?");
  $closedstmt->execute(array($code, $userId));
  $closed = $countstmt->fetchColumn();

  if ($closed <= 0){
    echo "Closed = 0 or < 0 (Which is weird)";
  } else {
    echo "Closed > 0!";
    try {
      $openstmt = $conn->prepare("UPDATE products SET open = open + 1 WHERE barcode = ? AND userId = ?");
      $openstmt->execute(array($code, $userId));
      $open2stmt = $conn->prepare("UPDATE products SET closed = closed - 1 WHERE barcode = ? AND userId = ?");
      $open2stmt->execute(array($code, $userId));
    }
    catch(PDOException $e)
    {
      echo "n";
    }
    echo "y";
  }
}
 ?>
