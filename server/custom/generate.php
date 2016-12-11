<?php

ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

require_once("../php/start.php");

$emailadres= $_POST['Email'];

function retour($error, $errormessage, $url){
  $json = array("Error"=>$error, "ErrorMessage"=>$errormessage, "Url"=>$url);

  echo json_encode($json);
}

if (empty($emailadres) || !isset($emailadres) ) {
  retour(True,"Voer alle invoervelden in!", "");
  die();
  }
if(!filter_var($emailadres, FILTER_VALIDATE_EMAIL)){
  retour(True, "Dit is geen emailadres.", "");
  die();
}

try {
  $conn = db();

  $checkstmt = $conn->prepare("SELECT * FROM users WHERE email = (?)");
  $checkstmt->execute(array($emailadres));
  if($checkstmt->fetch()){
    retour(True, "This email is already used.", "");
    die();
  }
  $userId = uniqid();
  $addstmt = $conn->prepare("INSERT INTO users (userId, email) VALUES (?,?)");
  $addstmt->execute(array($userId,$emailadres));
  //emailFunctie();
  retour(False, $userId, "");
}
catch(PDOException $e)
{
  retour(True, $e, "");
}


function emailFunctie(){

  $emailadres= $_POST['Email'];

  //TODO: Stuur email.
  require "PHPMailer/PHPMailerAutoload.php";
  $mail = new PHPMailer;
  $mail->Host = '';
  $mail->SMTPAuth = true;
  $mail->Username = '';
  $mail->Password = '';
  $mail->SMTPSecure = 'tls';
  $mail->Port = 587;
  $mail->setFrom("", "Smart Fridge");
  $mail->addAddress($emailadres);

  $mail->isHTML(true);

  $mail->Subject = "Smartfridge UserId";
  $mail->Body = "";
  $mail->AltBody ="";

  if(!$mail->send()){
    retour(False,"Mail error, send a message to ...", "");
    die();
  }
}

?>
