<?php
require_once("php/start.php");
require_once("php/getItem.php");
$conn = db();

$code = $_GET['code'];
echo "Barcode: $code ";
$desc = getTags($code);
echo "Description: $desc ";

//Check of product al bestaat
$checkstmt = $conn->prepare("SELECT barcode FROM products WHERE barcode = :barcode");
$checkstmt->bindParam(':barcode', $code);
$checkstmt->execute();

if($checkstmt->rowCount() > 0){
  echo "Exists! ";
  //Doe +1 bij aantal van product
  $upstmt = "UPDATE products SET ammount = ammount + 1 WHERE barcode = ?";
  $z = $conn->prepare($upstmt);
  $z->execute(array($code));
} else {
  echo "Doesn't exist! ";
  //Voeg product toe
  try {
    $addstmt = $conn->prepare("INSERT INTO products (barcode, ammount,description) VALUES (?,1,?)");
    $addstmt->execute(array($code,$desc));
  }
  catch(PDOException $e)
  {
    echo "n";
  }
  echo "y";
}
?>
