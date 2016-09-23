<?php
include_once('../php/start.php');
$conn = db();

if (!isset($_POST['JSON'])){
  die("You have to post your values in _POST['JSON']");
}

$data = json_decode($_POST['JSON'],true);

$userId = $data['userId'];
$sort = $data["Sort"];

if($sort == "everything"){
  $stmt = $conn->prepare('SELECT * FROM products WHERE userId = ?');
  $stmt->execute(array($userId));
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $ammount = $row['ammount'];
    $open = $row['open'];
    $closed = $ammount - $open;
    echo $row['description'] . ":<br>Ammount: " . $row['ammount'] . "<br>Closed: " . $closed . "<br>Opened: " . $row['open'] . "<br><br>";
  }
}elseif ($sort == "opened") {
  $stmt = $conn->prepare('SELECT * FROM products WHERE open > 0 AND userId = ?');
  $stmt->execute(array($userId));
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    echo $row['description'] . ":<br>Opened: " . $row['open'] . "<br><br>";
  }
}elseif ($sort == "closed") {
  $stmt = $conn->prepare('SELECT * FROM products WHERE ammount > 0 AND userId = ?');
  $stmt->execute(array($userId));
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $ammount = $row['ammount'];
    $open = $row['open'];
    $closed = $ammount - $open;
    echo $row['description'] . ":<br>Closed: " . $closed . "<br><br>";
  }
}else{
  $stmt = $conn->prepare('SELECT * FROM products WHERE ammount > 0 AND userId = ?');
  $stmt->execute(array($userId));
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $ammount = $row['ammount'];
    $open = $row['open'];
    $closed = $ammount - $open;
    echo $row['description'] . ":<br>Closed " . $closed . "<br>Open: " . $row['open'] . "<br><br>";
  }
}
?>
