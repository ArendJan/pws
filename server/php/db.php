<?php

function connectDB(){
  $servername = "rdbms.strato.de";
  $username = "U2672986";
  $password = "Profielwerkstuk2016";

try {
    $conn = new PDO("mysql:host=$servername;dbname=DB2672986", $username, $password);
    // set the PDO error mode to exception
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    }
catch(PDOException $e)
    {
      echo $e;
      echo "je fukking db functie doet het niet kut!";
    die();
    }

  return $conn;
}


?>
