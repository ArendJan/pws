<?php
require_once("php/start.php");
$conn = db();

<<<<<<< HEAD
  $code = $_GET['code'];
  echo "Barcode: $code";
=======
$code = $_GET['code'];
echo "Barcode: $code";
>>>>>>> origin/DatabaseEerste

//Check of product al bestaat
$checkstmt = $conn->prepare("SELECT barcode FROM producten WHERE barcode = :barcode");
$checkstmt->bindParam(':barcode', $code);
$checkstmt->execute();

<<<<<<< HEAD
      if($checkstmt->rowCount() > 0){
          echo "Bestaat!";
          //Doe +1 bij aantal van product
          $upstmt = "UPDATE producten SET aantal = aantal + 1 WHERE barcode = ?";
          $z = $conn->prepare($upstmt);
          $z->execute(array($code));
      } else {
          echo "Bestaat niet!";
          //Voeg product toe
          try {
            $addstmt = $conn->prepare("INSERT INTO producten (barcode, aantal) VALUES (?,1)");
            $addstmt->execute(array($code));
            }
        catch(PDOException $e)
            {
            echo "n";
            }
            echo "y";
      }
=======
if($checkstmt->rowCount() > 0){
  echo "Bestaat!";
  //Doe +1 bij aantal van product
  $upstmt = "UPDATE producten SET aantal = aantal + 1 WHERE barcode = ?";
  $z = $conn->prepare($upstmt);
  $z->execute(array($code));
} else {
  echo "Bestaat niet!";
  //Voeg product toe
  try {
    $addstmt = $conn->prepare("INSERT INTO producten (barcode, aantal) VALUES (?,?)");
    $addstmt->execute(array($code, 1));
  }
  catch(PDOException $e)
  {
    echo "n";
  }
  echo "y";
}
>>>>>>> origin/DatabaseEerste
?>
