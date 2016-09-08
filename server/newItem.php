<?php
require_once("php/start.php");
require_once("php/getItem.php");
$conn = db();

$code = $_GET['code'];
echo "Barcode: $code ";
$desc = getTags($code);
echo "Omschrijving: $desc ";

//Check of product al bestaat
$checkstmt = $conn->prepare("SELECT barcode FROM producten WHERE barcode = :barcode");
$checkstmt->bindParam(':barcode', $code);
$checkstmt->execute();

if($checkstmt->rowCount() > 0){
  echo "Bestaat! ";
  //Doe +1 bij aantal van product
  $upstmt = "UPDATE producten SET aantal = aantal + 1 WHERE barcode = ?";
  $z = $conn->prepare($upstmt);
  $z->execute(array($code));
} else {
  echo "Bestaat niet! ";
  //Voeg product toe
  try {
    $addstmt = $conn->prepare("INSERT INTO producten (barcode, aantal,omschrijving) VALUES (?,1,?)");
    $addstmt->execute(array($code,$desc));
  }
  catch(PDOException $e)
  {
    echo "n";
  }
  echo "y";
}
?>
