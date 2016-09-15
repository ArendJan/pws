<?php
require_once("php/start.php");
$conn = db();

$code = $_GET['code'];
echo "Barcode: $code ";

$countstmt = $conn->prepare("SELECT aaantal FROM producten WHERE barcode = :barcode");
$countstmt->bindParam(':barcode', $code);
$count = $countstmt->fetchColumn();

if($count == 0) {
  echo "Aantal > 0";
  //Doe -1 bij aantal van product
  $delstmt = "UPDATE producten SET aantal = aantal - 1 WHERE barcode = ?";
  $z = $conn->prepare($delstmt);
  $z->execute(array($code));
} else {
  echo "Aantal = 0!";
}

 ?>
