<?php

include_once('../php/start.php');
require_once("include/checkUserId.php");

$conn = db();

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);

<<<<<<< HEAD
$userId = $data['userId'];
if (!isset($data["Type"])){
  $type = $data["Type"];
}

=======
$userId = $data['UserId'];
$type = $data["Type"];
>>>>>>> origin/VinniiThuis
$barcode = $data["Barcode"];
$text = $data["Text"];
$list = $data["List"];

if (checkUserId($userId) == false){
  die ("You forgot your UserId, or gave an invalid UserId!");
}



$stmt = $conn->prepare("INSERT INTO jobs (userId, type, barcode, text, list, status) VALUES (?,?,?,?,?,?)");
$stmt->execute(array($userId, $type, $barcode, $text, $list, "new"));

$last_id = $conn->lastInsertId('jobs');
$arr = array('JobId' => $last_id);
echo json_encode($arr);

?>
