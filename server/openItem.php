<?php
require_once("php/start.php");
$conn = db();
$code = $_GET['code'];

//Doe -1 bij aantal van product
try {
  $delstmt = $conn->prepare("UPDATE producten SET open = open + 1 WHERE barcode =?");
  $dellstmt->execute(array($code));
}
catch(PDOException $e)
{
  echo "n";
}
echo "y";
 ?>
