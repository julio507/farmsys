<!DOCTYPE html>
<html>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Cadastro de Animais</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='/css/main.css'>

    <script>
        _selectedRow = null;

        formatFormDate = function( date ){
            return date.split( 'T' )[0];
        }

        formatTableDate = function( date ){
            formated = formatFormDate( date ).split( '-' );

            return formated[2] + '-' + formated[1] + '-' + formated[0];
        }

        refresh = function () {

            clearFields();

            const Http = new XMLHttpRequest();
            Http.open("GET", '/api/animal/getAll');
            Http.send();

            Http.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    animals = JSON.parse(Http.responseText);

                    table = document.getElementById('dataTable').getElementsByTagName('tbody')[0];

                    table.innerHTML = "";

                    for (i = 0; i < animals.length; i++) {
                        row = table.insertRow(i);

                        row.innerHTML = "<td>" + animals[i].id + "</td>" +
                            "<td>" + animals[i].specie + "</td>" +
                            "<td>" + formatTableDate( animals[i].bornDate ) + "</td>" +
                            "<td>" + formatTableDate( animals[i].acquisitionDate ) + "</td>" +
                            "<td>" + animals[i].weight + "</td>" +
                            "<td>" + animals[i].height + "</td>";

                        row.value = animals[i];

                        row.onclick = function () {
                            idField = document.getElementById("idField");
                            specieField = document.getElementById("specieField");
                            bornField = document.getElementById("bornField");
                            acquisitionField = document.getElementById("acquisitionField");
                            weightField = document.getElementById("weightField");
                            heightField = document.getElementById("heightField");

                            idField.value = this.value.id;
                            specieField.value = this.value.specie;
                            bornField.value = formatFormDate( this.value.bornDate );
                            acquisitionField.value = formatFormDate( this.value.acquisitionDate );
                            weightField.value = this.value.weight;
                            heightField.value = this.value.height;

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
            Http.open("POST", '/api/animal/update');

            Http.setRequestHeader("Content-Type", "application/json");

            idField = document.getElementById("idField");
            specieField = document.getElementById("specieField");
            bornField = document.getElementById("bornField");
            acquisitionField = document.getElementById("acquisitionField");
            weightField = document.getElementById("weightField");
            heightField = document.getElementById("heightField");

            value = {};

            value.id = idField.value;
            value.specie = specieField.value;
            value.bornDate = bornField.value;
            value.acquisitionDate = acquisitionField.value;
            value.weight = weightField.value;
            value.height = heightField.value;

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
            specieField = document.getElementById("specieField");
            bornField = document.getElementById("bornField");
            acquisitionField = document.getElementById("acquisitionField");
            weightField = document.getElementById("weightField");
            heightField = document.getElementById("heightField");

            idField.value = null;
            specieField.value = null;
            bornField.value = null;
            acquisitionField.value = null;
            weightField.value = null;
            heightField.value = null;

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
        <a href="/logout">
            <img src="/img/logout.png">
        </a>
    </div>
    <h1>Animais:</h1>
    <form>
        <p>ID:</p><input id="idField" type="text" disabled="true" />
        <p>Esp&eacute;cie:<span id="red">*</span></p><input id="specieField" type="text" />
        <p>Data de nascimento:<span id="red">*</span></p><input id="bornField" type="date" />
        <p>Data de aquisi&ccedil;&atilde;o:<span id="red">*</span></p><input id="acquisitionField" type="date" />
        <p>Peso:<span id="red">*</span></p><input id="weightField" type="number" min="0" />
        <p>Altura:<span id="red">*</span></p><input id="heightField" type="number" min="0" />
        <div class="horizontal">
            <input value="Salvar" onclick="send()" type="button" />
            <input value="Limpar" onclick="clearFields()" type="button" />
        </div>
    </form>
    <table id='dataTable'>
        <thead>
            <tr>
                <th>ID</th>
                <th>Esp&eacute;cie</th>
                <th>Data de nascimento</th>
                <th>Data de aquisi&ccedil;&atilde;o</th>
                <th>Peso</th>
                <th>Altura</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</body>

</html>