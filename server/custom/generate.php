<?php
require_once("../php/start.php");

if(!isset($_POST["Email"])){
  echo "please don't try to hack my server!!!!";
  die();
}

$conn = db();
$userId = uniqid();

try {
  $addstmt = $conn->prepare("INSERT INTO users (userId, email) VALUES (?,?)");
  $addstmt->execute(array($userId,$_POST["Email"]));
}
catch(PDOException $e)
{
  echo "n";
  echo $e;
}
echo "y";
echo $userId;

 ?>
