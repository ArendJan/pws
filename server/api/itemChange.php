<?php

require_once(../newItem.php);
require_once(../delItem.php);
require_once(../openItem.php);

$code = $_POST["code"];
$action = $_POST["action"];

if($action == "add"){
  add($code);
} elseif ($action == "del") {
  del($code);
} elseif ($action == "open") {
  upate($code);
}

 ?>
