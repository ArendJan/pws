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
  emailFunctie($emailadres, $userId);
  retour(False, $userId, "");
}
catch(PDOException $e)
{
  retour(True, $e, "");
}


function emailFunctie($email, $userid){

require_once('swift/swift/lib/swift_required.php');

$transport = Swift_SmtpTransport::newInstance('smtp.strato.com', 587, 'tls')
        ->setUsername('smartfridge@svshizzle.com')
        ->setPassword('Koelkast123');

$mailer = Swift_Mailer::newInstance($transport);

$message = Swift_Message::newInstance('Userid')
  ->setFrom(array('smartfridge@svshizzle.com' => 'Smartfridge'))
  ->setTo(array($email))
  ->setBody('Your userid is '. $userid);

$result = $mailer->send($message);
}
?>
