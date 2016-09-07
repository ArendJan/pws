<?php
require_once("php/start.php");
$conn = db();

$stmt = $conn->prepare('SELECT * FROM producten WHERE aantal > 0');
$stmt->execute();

$result = $stmt -> fetchAll();

foreach( $result as $row ) {
    echo $row['omschrijving'] . ": " . $row['aantal'] . "<br>";
}
?>
