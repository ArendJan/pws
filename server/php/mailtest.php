<?php

require_once('swift/swift/lib/swift_required.php');

$transport = Swift_SmtpTransport::newInstance('smtp.gmail.com', 587, 'tls')
  ->setUsername('svshizzle@gmail.com')
  ->setPassword('kalsbeek2014');

$mailer = Swift_Mailer::newInstance($transport);

$message = Swift_Message::newInstance('Test Subject')
  ->setFrom(array('svshizzle@gmail.com' => 'SvShizzle'))
  ->setTo(array('arendjan18@gmaasdfil.com','arendsurvey@gmail.com'))
  ->setBody('This is a test mail.lololol');

$result = $mailer->send($message);
?>
