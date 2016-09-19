<?php
include_once('../php/start.php');
$conn = db();

if(empty($_GET['Sort'])){
  $sort = "opened+closed";
} else {
  $sort = $_GET['Sort'];
}

if($sort == "everything"){
  $stmt = $conn->prepare('SELECT * FROM producten');
  $stmt->execute();
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $aantal = $row['aantal'];
    $open = $row['open'];
    $closed = $aantal - $open;
    echo $row['omschrijving'] . ":<br>Ammount: " . $row['aantal'] . "<br>Closed: " . $closed . "<br>Opened: " . $row['open'] . "<br><br>";
  }
}elseif ($sort == "opened") {
  $stmt = $conn->prepare('SELECT * FROM producten WHERE open > 0');
  $stmt->execute();
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    echo $row['omschrijving'] . ":<br>Opened: " . $row['open'] . "<br><br>";
  }
}elseif ($sort == "closed") {
  $stmt = $conn->prepare('SELECT * FROM producten WHERE aantal > 0');
  $stmt->execute();
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $aantal = $row['aantal'];
    $open = $row['open'];
    $closed = $aantal - $open;
    echo $row['omschrijving'] . ":<br>Closed: " . $closed . "<br><br>";
  }
}elseif ($sort == "opened+closed") {
  $stmt = $conn->prepare('SELECT * FROM producten WHERE aantal > 0');
  $stmt->execute();
  $result = $stmt -> fetchAll();
  foreach( $result as $row ) {
    $aantal = $row['aantal'];
    $open = $row['open'];
    $closed = $aantal - $open;
    echo $row['omschrijving'] . ":<br>Closed " . $closed . "<br>Open: " . $row['open'] . "<br><br>";
  }
}
?>
