<?php
require_once("php/start.php");
$conn = db();

$stmt = $conn->prepare('SELECT * FROM products WHERE ammount > 0');
$stmt->execute();

$result = $stmt -> fetchAll();

foreach( $result as $row ) {
    echo $row['description'] . ": " . $row['ammount'] . "<br>";
}
?>
