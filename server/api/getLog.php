<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include_once('../php/start.php');
require_once("include/checkUserId.php");

$conn = db();

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);

$userId = $data['UserId'];

$json_array = array();

if (checkUserId($userId) == false){
  die ("You forgot your UserId, or gave an invalid UserId!");
}

  $stmt = $conn->prepare('SELECT * FROM logging WHERE userId = ?');
  $stmt->execute(array($userId));

$result = $stmt -> fetchAll();
foreach( $result as $row ) {

    $row_array['ID'] = $row['ID'];
    $row_array['Time'] = $row['time'];
    $row_array['Script'] = $row['script'];
    $row_array['Params'] = json_decode($row['params']);
    $row_array["UserId"] = $row["userId"];
    array_push($json_array,$row_array);

}

echo json_encode($json_array);
?>
