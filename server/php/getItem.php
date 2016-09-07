<?php

function getTags($code){
  $tags = get_meta_tags('http://www.jumbo.com/zoeken?SearchTerm=' . $code);
  $desc = $tags['description'];
  return $desc
}
?>
