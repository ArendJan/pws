<?php
require_once("../php/start.php");
if(!isset($_POST["Text"])){
  echo "please don't try to hack my server!!!!";
  die();
}
$conn = db();
try {
  $addstmt = $conn->prepare("INSERT INTO jobs (userId, type,text, done) VALUES (?,'text',?,'new')");
  $addstmt->execute(array($masteruserId,$_POST["Text"]));
}
catch(PDOException $e)
{
  echo "n";
  echo $e;
}
echo "y";

 ?>
