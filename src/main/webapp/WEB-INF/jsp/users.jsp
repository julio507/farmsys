<!DOCTYPE html>
<html>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Cadastro de Usuarios</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='/css/main.css'>

    <script>
        _selectedRow = null;

        refresh = function () {

            clearFields();

            const Http = new XMLHttpRequest();
            Http.open("GET", '/api/user/getAll');
            Http.send();

            Http.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    users = JSON.parse(Http.responseText);

                    table = document.getElementById('usersTable').getElementsByTagName('tbody')[0];

                    table.innerHTML = "";

                    for (i = 0; i < users.length; i++) {
                        row = table.insertRow(i);

                        row.innerHTML = "<td>" + users[i].id + "</td>" +
                            "<td>" + users[i].name + "</td>" +
                            "<td>" + users[i].email + "</td>" +
                            "<td>" + users[i].enabled + "</td>";

                        row.value = users[i];

                        row.onclick = function () {
                            idField = document.getElementById("idField");
                            nameField = document.getElementById("nameField");
                            emailField = document.getElementById("emailField");
                            passwordField = document.getElementById("passwordField");
                            statusField = document.getElementById("statusField");

                            idField.value = this.value.id;
                            nameField.value = this.value.name;
                            emailField.value = this.value.email;
                            passwordField.value = null;
                            statusField.checked = this.value.enabled;

                            if (_selectedRow != null) {
                                _selectedRow.setAttribute("class", null);
                            }

                            this.setAttribute("class", "selected");

                            _selectedRow = this;
                        }
                    }
                }
            }
        }

        send = function () {
            const Http = new XMLHttpRequest();
            Http.open("POST", '/api/user/update');

            Http.setRequestHeader("Content-Type", "application/json");

            idField = document.getElementById("idField");
            nameField = document.getElementById("nameField");
            emailField = document.getElementById("emailField");
            passwordField = document.getElementById("passwordField");
            statusField = document.getElementById("statusField");

            value = {};

            value.id = idField.value;
            value.name = nameField.value;
            value.email = emailField.value;

            if (passwordField.value) {
                value.password = passwordField.value;
            }

            value.enabled = statusField.checked;

            Http.onreadystatechange = function () {
                if (this.readyState == 4) {
                    if (this.status == 200) {
                        refresh();
                    }

                    else if (this.status == 500) {
                        alert(JSON.parse(this.responseText).message);
                    }
                }
            }

            Http.send(JSON.stringify(value));
        }

        clearFields = function () {
            idField = document.getElementById("idField");
            nameField = document.getElementById("nameField");
            emailField = document.getElementById("emailField");
            passwordField = document.getElementById("passwordField");
            statusField = document.getElementById("statusField");

            idField.value = null;
            nameField.value = null;
            emailField.value = null;
            passwordField.value = null;
            statusField.value = null;

            _selectedRow = null;
        }

        window.onload = function () {
            refresh();
        }
    </script>
<body>
    <div id="menuBar" class="horizontal">
        <a href="/">
            <img src="/img/home.png">
        </a>
    </div>
    <h1>Usuarios:</h1>
    <form>
        <p>ID:</p><input id="idField" type="text" disabled="true" />
        <p>Nome:<span id="red">*</span></p><input id="nameField" type="text" />
        <p>E-mail:<span id="red">*</span></p><input id="emailField" type="text" />
        <p>Senha:</p><input id="passwordField" type="text" />
        <div class="horizontal">
            <p>Ativo:</p><input id="statusField" type="checkbox" />
        </div>
        <div class="horizontal">
            <input value="Salvar" onclick="send()" type="button" />
            <input value="Limpar" onclick="clearFields()" type="button" />
        </div>
    </form>
    <table id='usersTable'>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>E-mail</th>
                <th>Ativo</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</body>

</html>