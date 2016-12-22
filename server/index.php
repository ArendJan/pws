<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Smartfridge</title>

    <!-- Bootstrap Core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="css/one-page-wonder.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Smartfridge</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a href="#about">Smartfridge</a>
                    </li>
                    <li>
                        <a href="#services">Test</a>
                    </li>
                    <li>
                        <a href="#contact">Documents</a>
                    </li>
                    <li>
                        <a href="#print">Print</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Full Width Image Header -->
    <header class="header-image">
        <div class="headline">
            <div class="container">
                <h1>Smartfridge</h1>
                <h2>A DIY fridge connected to the internet</h2>
            </div>
        </div>
    </header>

    <!-- Page Content -->
    <div class="container">

        <hr class="featurette-divider">

        <!-- First Featurette -->
        <div class="featurette" id="about">
            <img class="featurette-image img-circle img-responsive pull-right" src="/img/plaatje1.png">
            <h2 class="featurette-heading">A smart fridge
                <span class="text-muted">Everybody wants it!</span>
            </h2>
            <p class="lead">We, Vincent Kling and Arend-Jan van Hilten, have been working very long and hard on our endproject for school. We have created a smart fridge, that knows what is in your fridge and can do all kinds of stuff for you.</p>
        </div>

        <hr class="featurette-divider">

        <!-- Second Featurette -->
        <div class="featurette" id="services">
            <img class="featurette-image img-circle img-responsive pull-left" src="/img/plaatje2.png">
            <h2 class="featurette-heading">Test it!
                <span class="text-muted">On our fridge</span>
            </h2>
            <p class="lead">You can test our app and even do stuff on our fridge. Our app is downloadable from the <a href="https://play.google.com/store/apps/details?id=com.svshizzle.pws.smartfridge" target="_blank"> Play Store(Android only)</a>. The password for our own setup is kaasblokje. You can also recreate our project, by looking at <a href="https://www.hackster.io/ajv/smart-fridge-4a50b5"target="_blank">this project on Hackster.io(still WIP)</a>. </p>
        </div>

        <hr class="featurette-divider">

        <!-- Third Featurette -->
        <div class="featurette" id="contact">
            <img class="featurette-image img-circle img-responsive pull-right" src="/img/documentation.png">
            <h2 class="featurette-heading">See the output
                <span class="text-muted">Only Dutch</span>
            </h2>
            <p class="lead">You can download<a href="/downloads/verslag.docx"> our paper from here</a>, together with<a href="/downloads/bijlagen.docx"> the attachments.</a> These are both in dutch, but we also have made a <a href="https://arendjan.github.io/slate/"target="_blank">documentationpage for the info</a> how to communicate with the server. </p>
        </div>

        <hr class="featurette-divider">
        <!-- Third Featurette -->
        <div class="featurette" id="print">
            <img class="featurette-image img-circle img-responsive pull-right" src="/img/printer.jpg">
            <h2 class="featurette-heading">Print
                <span class="text-muted">something in our fridge.</span>
            </h2>
            <p class="lead">We have our fridge with everything connected and loaded with 100m thermal paper. We can't use it all, but we have to disassemble it in max 6 months and it would be cool to say we used all that paper.<br>So we want you to send us a message, we don't care what you write, but we'll read it and keep it. Please don't spam and not more than 1 or 2 texts long. If you add your @twitter to it, we'll tweet a picture of the message! <br>
            <input type="text" maxlength="300" id="textInput"><br><button  id="textSend">Send!</button></p>
        </div>

        <hr class="featurette-divider">
        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Blablabla, just use it.</p>
                </div>
            </div>
        </footer>

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>
<script>
$(document).ready(function() {
    $("#textSend").click(function(){
        text = $("#textInput").val();
        $.ajax({url: "/custom/printtext.php", data:{Text:text}, type:'POST', success: function(result){
            console.log(result);
   }});
    });
});

</script>
</body>

</html>
