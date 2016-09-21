<?php
require_once(dirname(__FILE__)."/../../php/start.php");
$conn = db();
stop de $conn in de functie, geen overhead en nu kan $conn niet gelezen worden
Ook de requres in de functie
$code = $_GET['code'];
$userId = $_GET['userId'];
echo "Barcode: $code ";

$countstmt = $conn->prepare("SELECT ammount FROM products WHERE barcode = :barcode AND userId = :userId");
$countstmt->bindParam(':barcode', $code);
$countstmt->bindParam(':userId', $userId);
$count = $countstmt->fetchColumn();

if($count == 0) {
  echo "Ammount > 0";
  //Doe -1 bij aantal van product
  $delstmt = $conn->prepare("UPDATE products SET ammount = ammount - 1 WHERE barcode = ? AND userId = ?");
  $delstmt->execute(array($code, $userId));
} else {
  echo "Ammount = 0!";
}

 ?>
