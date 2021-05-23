<!DOCTYPE html>
<html>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Historico</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='/css/main.css'>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

    <script>
        _selectedRow = null;
        _selectedRowHistory = null;

        formatFormDate = function (date) {
            return date.split('T')[0];
        }

        formatTableDate = function (date) {
            formated = formatFormDate(date).split('-');

            return formated[2] + '-' + formated[1] + '-' + formated[0];
        }

        formatGraphDate = function(date){
            formated = formatFormDate(date).split('-');

            return new Date( formated[0], formated[1], formated[2] );
        }

        refreshHistory = function () {
            dateMinFilterHistory = document.getElementById('dateMinFilterHistory');
            dateMaxFilterHistory = document.getElementById('dateMaxFilterHistory');
            weightMinFilterHistory = document.getElementById('weightMinFilterHistory');
            weightMaxFilterHistory = document.getElementById('weightMaxFilterHistory');
            heightMinFilterHistory = document.getElementById('heightMinFilterHistory');
            heightMaxFilterHistory = document.getElementById('heightMaxFilterHistory');

            const Http = new XMLHttpRequest();
            Http.open("GET", "/api/history/getAll?animalId=" + _selectedRow.value.id +
                "&dateMin=" + dateMinFilterHistory.value +
                "&dateMax=" + dateMaxFilterHistory.value +
                "&weightMin=" + weightMinFilterHistory.value +
                "&weightMax=" + weightMaxFilterHistory.value +
                "&heightMin=" + heightMinFilterHistory.value +
                "&heightMax=" + heightMaxFilterHistory.value);

            Http.send();

            Http.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    animalField = document.getElementById("animalField");
                    animalField.value = _selectedRow.value.id + "-" + _selectedRow.value.specie;

                    historyData = JSON.parse(Http.responseText);

                    table = document.getElementById('historyTable').getElementsByTagName('tbody')[0];

                    table.innerHTML = "";

                    var data = new google.visualization.DataTable();
                    data.addColumn('date', 'Data');
                    data.addColumn('number', 'Peso');
                    data.addColumn('number', 'Altura');

                    var options = {
                        'title': 'Historico',
                        'height' : '100%',
                        'width': '100%'
                    };

                    for (i = 0; i < historyData.length; i++) {
                        row = table.insertRow(i);

                        row.innerHTML = "<td>" + historyData[i].id + "</td>" +
                            "<td>" + formatTableDate(historyData[i].date) + "</td>" +
                            "<td>" + historyData[i].weight + "</td>" +
                            "<td>" + historyData[i].height + "</td>";

                        row.value = historyData[i];

                        data.addRow([formatGraphDate(historyData[i].date), historyData[i].weight, historyData[i].height]);

                        row.onclick = function () {

                            idField = document.getElementById("idField");
                            dateField = document.getElementById("dateField");
                            weightField = document.getElementById("weightField");
                            heightField = document.getElementById("heightField");

                            idField.value = this.value.id;
                            dateField.value = formatFormDate(this.value.date);
                            weightField.value = this.value.weight;
                            heightField.value = this.value.height;

                            if (_selectedRowHistory != null) {
                                _selectedRowHistory.setAttribute("class", null);
                            }

                            this.setAttribute("class", "selected");

                            _selectedRowHistory = this;
                        }
                    }

                    var chart = new google.visualization.AreaChart(document.getElementById('graph'));
                    chart.draw(data, options);
                }
            }
        }

        refresh = function () {

            speciesFilter = document.getElementById('specieFilter');
            bornDateMinFilter = document.getElementById('bornDateMinFilter');
            bornDateMaxFilter = document.getElementById('bornDateMaxFilter');
            aquisitionDateMinFilter = document.getElementById('aquisitionDateMinFilter');
            aquisitionDateMaxFilter = document.getElementById('aquisitionDateMaxFilter');
            weightMinFilter = document.getElementById('weightMinFilter');
            weightMaxFilter = document.getElementById('weightMaxFilter');
            heightMinFilter = document.getElementById('heightMinFilter');
            heightMaxFilter = document.getElementById('heightMaxFilter');
            activeFilter = document.getElementById('activeFilter');

            const Http = new XMLHttpRequest();
            Http.open("GET", '/api/animal/getAll?species=' + speciesFilter.value +
                '&bornDateMin=' + bornDateMinFilter.value +
                '&bornDateMax=' + bornDateMaxFilter.value +
                '&aquisitionDateMin=' + aquisitionDateMinFilter.value +
                '&aquisitionDateMax=' + aquisitionDateMaxFilter.value +
                '&weightMin=' + weightMinFilter.value +
                '&weightMax=' + weightMaxFilter.value +
                '&heightMin=' + heightMinFilter.value +
                '&heightMax=' + heightMaxFilter.value +
                '&active=' + activeFilter.checked);

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
                            "<td>" + formatTableDate(animals[i].bornDate) + "</td>" +
                            "<td>" + formatTableDate(animals[i].acquisitionDate) + "</td>" +
                            "<td>" + animals[i].weight + "</td>" +
                            "<td>" + animals[i].height + "</td>" +
                            "<td>" + animals[i].active + "</td>";

                        row.value = animals[i];

                        row.onclick = function () {
                            if (_selectedRow != null) {
                                _selectedRow.setAttribute("class", null);
                            }

                            this.setAttribute("class", "selected");

                            _selectedRow = this;

                            refreshHistory();
                        }
                    }
                }
            }
        }

        send = function () {
            const Http = new XMLHttpRequest();
            Http.open("POST", '/api/history/update');

            Http.setRequestHeader("Content-Type", "application/json");

            idField = document.getElementById("idField");
            dateField = document.getElementById("dateField");
            weightField = document.getElementById("weightField");
            heightField = document.getElementById("heightField");

            value = {};

            value.id = idField.value;
            value.animalId = _selectedRow.value.id;
            value.date = dateField.value;
            value.weight = weightField.value;
            value.height = heightField.value;

            Http.onreadystatechange = function () {
                if (this.readyState == 4) {
                    if (this.status == 200) {
                        clearFields();
                        refreshHistory();
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
            animalField = document.getElementById("animalField");
            dateField = document.getElementById("dateField");
            weightField = document.getElementById("weightField");
            heightField = document.getElementById("heightField");

            idField.value = null;
            animalField.value = null;
            dateField.value = null;
            weightField.value = null;
            heightField.value = null;

            _selectedRowHistory = null;
        }

        print = function () {
            idField = document.getElementById("idField");
            dateField = document.getElementById("dateField");
            weightField = document.getElementById("weightField");
            heightField = document.getElementById("heightField");

            window.open("/api/history/pdf?animalId=" + _selectedRow.value.id +
                "&dateMin=" + dateMinFilterHistory.value +
                "&dateMax=" + dateMaxFilterHistory.value +
                "&weightMin=" + weightMinFilterHistory.value +
                "&weightMax=" + weightMaxFilterHistory.value +
                "&heightMin=" + heightMinFilterHistory.value +
                "&heightMax=" + heightMaxFilterHistory.value);
        }

        window.onload = function () {
            google.charts.load('current', { 'packages': ['corechart'] });

            refresh();

            document.getElementById("tab1").onclick = function () {
                document.getElementById("historyTable").style.display = "block";

                document.getElementById("graph").style.display = "none";
            };

            document.getElementById("tab2").onclick = function () {
                document.getElementById("historyTable").style.display = "none";

                document.getElementById("graph").style.display = "block";
            };
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
    <div id="gridHistory">
        <div id="formDiv">
            <h1>Animais:</h1>
            <form>
                <p>ID:</p><input id="idField" type="text" disabled="true" />
                <p>Animal:</p><input id="animalField" type="text" disabled="true" />
                <p>Data:</p><input id="dateField" type="date" />
                <p>Peso:</p><input id="weightField" type="number" />
                <p>Altura:</p><input id="heightField" type="number" />
                <div class="horizontal">
                    <input value="Salvar" onclick="send()" type="button" />
                    <input value="Limpar" onclick="clearFields()" type="button" />
                    <input value="Imprimir" onclick="print()" type="button" />
                </div>
            </form>
        </div>
        <div id="tableDiv">
            <div id="tabs">
                <div id="tab1" class="tab">Tabela</div>
                <div id="tab2" class="tab">Grafico</div>
            </div>
            <table id="historyTable">
                <thead>
                    <tr id="filter" class="default">
                        <td></td>
                        <td>
                            <input id="dateMinFilterHistory" oninput="refreshHistory()" placeholder="Data início"
                                type="date" />
                            <input id="dateMaxFilterHistory" oninput="refreshHistory()" placeholder="Data fim"
                                type="date" />
                        </td>
                        <td>
                            <input id="weightMinFilterHistory" oninput="refreshHistory()" placeholder="Peso minimo"
                                type="number" />
                            <input id="weightMaxFilterHistory" oninput="refreshHistory()" placeholder="Peso Maximo"
                                type="number" />
                        </td>
                        <td>
                            <input id="heightMinFilterHistory" oninput="refreshHistory()" placeholder="Altura minima"
                                type="number" />
                            <input id="heightMaxFilterHistory" oninput="refreshHistory()" placeholder="Altura maxima"
                                type="number" />
                        </td>
                    </tr>
                    <tr class="default">
                        <th>ID</th>
                        <th>Data</th>
                        <th>Peso</th>
                        <th>Altura</th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
            <div id="graph"></div>
        </div>
    </div>
    <table id='dataTable'>
        <thead>
            <tr id="filter">
                <td></td>
                <td><input id="specieFilter" oninput="refresh()" placeholder="Nome" type="text" /></td>
                <td>
                    <input id="bornDateMinFilter" oninput="refresh()" placeholder="Nascimento início" type="date" />
                    <input id="bornDateMaxFilter" oninput="refresh()" placeholder="Nascimento fim" type="date" />
                </td>
                <td>
                    <input id="aquisitionDateMinFilter" oninput="refresh()" placeholder="Aquisição início"
                        type="date" />
                    <input id="aquisitionDateMaxFilter" oninput="refresh()" placeholder="Aquisição fim" type="date" />
                </td>
                <td>
                    <input id="weightMinFilter" oninput="refresh()" placeholder="Peso minimo" type="number" />
                    <input id="weightMaxFilter" oninput="refresh()" placeholder="Peso maximo" type="number" />
                </td>
                <td>
                    <input id="heightMinFilter" oninput="refresh()" placeholder="Altura minima" type="number" />
                    <input id="heightMaxFilter" oninput="refresh()" placeholder="Altura maxima" type="number" />
                </td>
                <td>
                    <p>Ativo</p><input id="activeFilter" oninput="refresh()" type="checkbox" checked="true" />
                </td>
            </tr>
            <tr>
                <th>ID</th>
                <th>Esp&eacute;cie</th>
                <th>Data de nascimento</th>
                <th>Data de aquisi&ccedil;&atilde;o</th>
                <th>Peso</th>
                <th>Altura</th>
                <th>Ativo</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</body>

</html>