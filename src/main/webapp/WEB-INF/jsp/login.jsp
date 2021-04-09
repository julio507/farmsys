<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='/css/default.css'>
    <style>
        form{
            display: grid;
            padding-left: 25%;
            padding-right: 25%;
            padding-top: 20%;
        }

        #button{
            margin-top: 5px;
        }

        h1{
            font-size: 35px;
        }
    </style>
</head>
<body>
    <div>
        <form method="POST" action="login/post">
            <h1>FarmSys</h1>
            <p>Usuario:</p><input type="text"/>
            <p>Senha:</p><input type="text"/>
            <input id="button" type="submit" value="Entrar"/>
        </form>
    </div>
</body>
</html>