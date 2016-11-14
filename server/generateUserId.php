<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Generate userId</title>

</head>

<body>

  <div>
    <h2>Generate userId</h2>
    <p>
      <input type="text" maxlength="300" id="email"></input>
      <button id="generate">Generate userId!</button>
    </p>
    <div id="done" style="display:none">
    </div>
  </div>

  <!-- jQuery -->
  <script src="js/jquery.js"></script>

  <!-- Bootstrap Core JavaScript -->
  <script src="js/bootstrap.min.js"></script>
  <script>
  $(document).ready(function() {
    $("#generate").click(function(){
      email = $("#email").val();
      $.ajax({url: "/custom/generate.php", data:{Email:email}, type:'POST', success: function(result){
        console.log(result);
        result = JSON.parse(result);
        if(result.Error==true){

          $("#done").show();
          $("#done").html(result.ErrorMessage + " Please try again.");
        }else if(result.Error==false){
          $("#done").show();
          $("#done").html("This is your userId: "+ result.ErrorMessage +". You also recieve an email with this userId!");
        }
      }});
    });
  });

  </script>

</body>

</html>
