<?php

include_once('../php/start.php');
$conn = db();

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);

$userId = $data['userId'];
$status = $data["Status"];
$type = $data["Type"];

?>
