<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

include_once('../php/start.php');
require_once("include/checkUserId.php");

$conn = db();

require_once("include/log.php");

require_once("include/newItem.php");

require_once("include/delItem.php");
require_once("include/delOpen.php");
require_once("include/delClosed.php");

require_once("include/openItem.php");

require_once("include/checkUserId.php");

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);

$userId = $data['UserId'];

logging(basename($_SERVER['PHP_SELF']),$_POST['JSON'],$userId);

if (!isset($data["Barcode"])){
  die("You have to post your barcode!");
}

$code = $data["Barcode"];

if (!isset($data["Action"]) || $data['Action'] == ""){
  $action = "add";
} else {
  $action = $data["Action"];
}

if (checkUserId($userId) == false){
  die('You forgot your userId, or you gave an invalid userId!');
}
if($action == "add"){
  echo "Adding Product";
  addItem($code, $userId);
} else if ($action == "del") {
  echo "Removing Product";
  delItem($code, $userId);
} else if ($action == "open") {
  echo "Opening Product";
  openItem($code, $userId);
} else if ($action == "delOpen") {
  echo "Deleting Open Product";
  delOpen($code, $userId);
} else if ($action == "delClosed") {
  echo "Deleting Closed Product";
  delClosed($code, $userId);
}else{
  die ("Not a correct action");
}

try{
  $returnstmt = $conn->prepare('SELECT * FROM products WHERE userId = ? AND barcode = ?');
  $returnstmt->execute(array($userId, $code));
}
catch (PDOException $e){
  errorLogging(basename($_SERVER['PHP_SELF']), $_POST['JSON'], $userId, $e);
  die;
}

$result = $returnstmt -> fetch();

$return_arr = array();

$row_array['Id'] = intval($result['ID']);
$row_array['Name'] = $result['description'];
$row_array['Barcode'] = strval($result['barcode']);
$row_array['Closed'] = intval($result['closed']);
$row_array['Open'] = intval($result['open']);

array_push($return_arr,$row_array);
echo json_encode($return_arr);
?>
