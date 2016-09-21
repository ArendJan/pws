<?php
require_once("../../php/start.php");
$conn = db();



$code = $_GET['code'];
$userId = $_GET['userId'];

try {
  $openstmt = $conn->prepare("UPDATE products SET open = open + 1 WHERE barcode = ? AND userId = ?");
  $openstmt->execute(array($code, $userId));
}
catch(PDOException $e)
{
  echo "n";
}
echo "y";
 ?>
