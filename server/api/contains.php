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

if (!isset($data["Sort"]) || $data["Sort"] == ""){
  $sort = "opened+closed";
} else {
  $sort = $data["Sort"];
}

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

    $row_array['Id'] = $row['ID'];
    $row_array['Name'] = $row['description'];
    $row_array['Barcode'] = $row['barcode'];
    $row_array['Ammount'] = $row['ammount'];
    $row_array['Closed'] = $closed;
    $row_array['Open'] = $row['open'];

    array_push($return_arr,$row_array);
  }
}elseif ($sort == "opened") {
  $stmt = $conn->prepare('SELECT * FROM products WHERE open > 0 AND userId = ?');
  $stmt->execute(array($userId));
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {

    //echo $row['description'] . ":<br>Opened: " . $row['open'] . "<br><br>";
    $row_array['Id'] = $row['ID'];
    $row_array['Name'] = $row['description'];
    $row_array['Barcode'] = $row['barcode'];
    $row_array['Open'] = $row['open'];

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
    $row_array['Id'] = $row['ID'];
    $row_array['Name'] = $row['description'];
    $row_array['Barcode'] = $row['barcode'];
    $row_array['Closed'] = $closed;

    array_push($return_arr,$row_array);
  }
}elseif ($sort == "opened+closed")  {
  $stmt = $conn->prepare('SELECT * FROM products WHERE ammount > 0 AND userId = ?');
  $stmt->execute(array($userId));
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $ammount = $row['ammount'];
    $open = $row['open'];
    $closed = $ammount - $open;

    //echo $row['description'] . ":<br>Closed " . $closed . "<br>Open: " . $row['open'] . "<br><br>";

    $row_array['Id'] = intval($row['ID']);
    $row_array['Name'] = $row['description'];
    $row_array['Barcode'] = strval($row['barcode']);
    $row_array['Closed'] = intval($closed);
    $row_array['Open'] = intval($row['open']);

    array_push($return_arr,$row_array);
  }
}

echo json_encode($return_arr);
?>
