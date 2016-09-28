<?php
include_once('../php/start.php');
require_once("include/checkUserId.php");

$conn = db();

$return_arr = array();

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);

$userId = $data['UserId'];
$sort = $data["Sort"];

if (checkUserId($userId) == false){
  die ('You forgot your userId, or gave an invalid userId!');
}

if($sort == "everything"){
  $stmt = $conn->prepare('SELECT * FROM products WHERE userId = ?');
  $stmt->execute(array($userId));
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $ammount = $row['ammount'];
    $open = $row['open'];
    $closed = $ammount - $open;

    //echo $row['description'] . ":<br>Ammount: " . $row['ammount'] . "<br>Closed: " . $closed . "<br>Opened: " . $row['open'] . "<br><br>";

    $row_array['id'] = $row['ID'];
    $row_array['name'] = $row['description'];
    $row_array['barcode'] = $row['barcode'];
    $row_array['ammount'] = $row['ammount'];
    $row_array['closed'] = $closed;
    $row_array['open'] = $row['open'];

    array_push($return_arr,$row_array);
  }
}elseif ($sort == "opened") {
  $stmt = $conn->prepare('SELECT * FROM products WHERE open > 0 AND userId = ?');
  $stmt->execute(array($userId));
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {

    //echo $row['description'] . ":<br>Opened: " . $row['open'] . "<br><br>";
    $row_array['id'] = $row['ID'];
    $row_array['name'] = $row['description'];
    $row_array['barcode'] = $row['barcode'];
    $row_array['open'] = $row['open'];

    array_push($return_arr,$row_array);
  }
}elseif ($sort == "closed") {
  $stmt = $conn->prepare('SELECT * FROM products WHERE ammount > 0 AND userId = ?');
  $stmt->execute(array($userId));
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $ammount = $row['ammount'];
    $open = $row['open'];
    $closed = $ammount - $open;

    //echo $row['description'] . ":<br>Closed: " . $closed . "<br><br>";
    $row_array['id'] = $row['ID'];
    $row_array['name'] = $row['description'];
    $row_array['barcode'] = $row['barcode'];
    $row_array['closed'] = $closed;

    array_push($return_arr,$row_array);
  }
}else{
  $stmt = $conn->prepare('SELECT * FROM products WHERE ammount > 0 AND userId = ?');
  $stmt->execute(array($userId));
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $ammount = $row['ammount'];
    $open = $row['open'];
    $closed = $ammount - $open;

    //echo $row['description'] . ":<br>Closed " . $closed . "<br>Open: " . $row['open'] . "<br><br>";

    $row_array['id'] = $row['ID'];
    $row_array['name'] = $row['description'];
    $row_array['barcode'] = $row['barcode'];
    $row_array['closed'] = $closed;
    $row_array['open'] = $row['open'];

    array_push($return_arr,$row_array);
  }
}

echo json_encode($return_arr);
?>
