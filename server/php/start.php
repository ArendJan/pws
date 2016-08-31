<?php
$DEBUG=true;
if($DEBUG){
  ini_set('display_errors', 1);
  ini_set('display_startup_errors', 1);
  error_reporting(E_ALL);
}

function nav(){ require_once(dirname(__FILE__)."/header.php");}
function head(){ require_once(dirname(__FILE__)."/head.php");}
function js(){require_once(dirname(__FILE__)."/js.php");}

require_once(dirname(__FILE__)."/db.php");
function db(){return connectDB();}
 ?>
