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
  retour(True,"Fill in all those fields!", "");
  die();
  }
if(!filter_var($emailadres, FILTER_VALIDATE_EMAIL)){
  retour(True, "This isn't an emailadress.", "");
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
  emailFunctie($userId, $emailadres);
  retour(False, $userId, "");
}
catch(PDOException $e)
{
  retour(True, $e, "");
}

function endsWith($haystack, $needle){
  return $needle === "" || (($temp = strlen($haystack) - strlen($needle))>=0 && strpos($haystack, $needle, $temp) !== false);
}

function emailFunctie($userId, $emailadres){

  //TODO: Stuur email.
  require ("../php/swift/swift/lib/swift_required.php");

  $transport = Swift_SmtpTransport::newInstance('smtp.strato.com',465,'ssl');
  $transport->setUsername('smartfridge@svshizzle.com');
  $transport->setPassword('svshizzle123');
  echo "creds";

  $mailer = Swift_Mailer::newInstance($transport);
  echo "mailer instance";

  $message = Swift_Message::newInstance();
  echo "message instance";

  $message->setSubject('Smartfridge UserId');
  $message->setFrom(array('Smartfridge@svshizzle.com' => 'SmartFridge'));
  $message->setTo($emailadres);
  $message->setBody('Your userId for the Smartfridge is: ' . $userId);
  echo "message";

  $mailer->send($message);
  echo "send";

  //if(!result){
  //  retour(False,"Mail error, send a message to ...", "");
  //  die();
  //}
}

?>
