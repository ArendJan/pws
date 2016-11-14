<?php
function addItem($code, $userId){

  require_once(dirname(__FILE__)."/../../php/start.php");
  require_once(dirname(__FILE__)."/getItem.php");
  $conn = db();

  echo "Barcode: $code ";
  $desc = getTags($code);
  echo "Description: $desc ";

  //Check of product al bestaat
  $checkstmt = $conn->prepare("SELECT barcode FROM products WHERE barcode = ? AND userId = ?");
  $checkstmt->execute(array($code, $userId));

  if($checkstmt->rowCount() > 0){
    echo "Exists! ";
    //Doe +1 bij aantal van product
    $upstmt = $conn->prepare("UPDATE products SET closed = closed + 1 WHERE barcode = ? AND userID = ?");
    $upstmt->execute(array($code, $userId));
  } else {
    echo "Doesn't exist! ";
    //Voeg product toe
    try {
      $addstmt = $conn->prepare("INSERT INTO products (barcode, closed, description, userId) VALUES (?,1,?,?)");
      $addstmt->execute(array($code,$desc,$userId));
    }
    catch(PDOException $e)
    {
      echo "n " . $e;
    }
    echo "y";
  }
}
?>
