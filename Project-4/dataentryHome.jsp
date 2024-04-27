<!DOCTYPE html>
<html lang="en">

<% String message=(String) session.getAttribute("message"); if (message==null) message="NULLllllL" ; String
    sqlStatement=(String) session.getAttribute("sqlStatement"); %>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Data Entry Form</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
                margin: 0;
                padding: 0;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            .container {
                background-color: #fff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                text-align: center;
                width: 80%;
                max-width: 800px;
            }

            h1 {
                color: #333;
                margin-top: -5px;
                margin-bottom: -10px;
            }

            h2 {
                color: #666;
                margin-bottom: 5px;
            }

            .input-container {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
                padding-top: 30px;


            }

            .input-box {
                flex-grow: 1;
                padding: 10px;
                margin-right: 55px;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 14px;
                width: 80%;

            }

            .input-label {
                display: block;
                text-align: left;
                font-weight: bold;
                margin-bottom: 5px;
            }

            .button-container {
                display: flex;
                justify-content: space-between;
                margin-top: 20px;
                border-bottom: 1px solid #ccc;
                padding-bottom: 20px;
            }

            .button {
                padding: 7px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                width: 48%;
            }

            .enter-data {
                background-color: #4caf50;
                color: #fff;
            }

            .clear-data {
                background-color: #f44336;
                color: #fff;
            }

            .results {
                padding: 10px;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h1>Data Entry Application</h1>
            <h2>Enter your data below:</h2>
            <div class="dataentry-row">
                <form id="queryInputBox1" action="/Project-4/SuppliersInsertServlet" method="post">

                    <div class="input-container">
                        <div>
                            <label for="snum" class="input-label">Supply Number:</label>
                            <input type="text" id="snum" name="snum" class="input-box"
                                placeholder="Enter supply number">
                        </div>
                        <div>
                            <label for="sname" class="input-label">Supply Name:</label>
                            <input type="text" id="sname" name="sname" class="input-box"
                                placeholder="Enter supply name">
                        </div>
                        <div>
                            <label for="status" class="input-label">Status:</label>
                            <input type="text" id="status" name="status" class="input-box" placeholder="Enter status">
                        </div>
                        <div>
                            <label for="city" class="input-label">City:</label>
                            <input type="text" id="city" name="city" class="input-box" placeholder="Enter city">
                        </div>
                    </div>
                </form>

                <div class="button-container">
                    <button class="button enter-data" onclick="submitSupplyForm(event)">Enter Supplier Record into
                        Database</button>
                    <button class="button clear-data" onclick="clearData()">Clear Data and Results</button>
                </div>

            </div>
            <div class="dataentry-row">

                <form id="queryInputBox2" action="/Project-4/PartsInsertServlet" method="post">

                    <div class="input-container">
                        <div>
                            <label for="pnum" class="input-label">Part Number:</label>
                            <input type="text" id="pnum" name="pnum" class="input-box" placeholder="Enter part number">
                        </div>
                        <div>
                            <label for="pname" class="input-label">Part Name:</label>
                            <input type="text" id="pname" name="pname" class="input-box" placeholder="Enter part name">
                        </div>
                        <div>
                            <label for="color" class="input-label">Color:</label>
                            <input type="text" id="color" name="color" class="input-box" placeholder="Enter color">
                        </div>
                        <div>
                            <label for="weight" class="input-label">Weight:</label>
                            <input type="text" id="weight" name="weight" class="input-box" placeholder="Enter weight">
                        </div>
                        <div>
                            <label for="city" class="input-label">City:</label>
                            <input type="text" id="city" name="city" class="input-box" placeholder="Enter city">
                        </div>
                    </div>

                </form>
                <div class="button-container">
                    <button class="button enter-data" onclick="submitPartForm(event)">Enter Part Record into
                        Database</button>
                    <button class="button clear-data" onclick="clearData()">Clear Data and Results</button>
                </div>
            </div>
            <div class="dataentry-row">
                <form id="queryInputBox3" action="/Project-4/JobsInsertServlet" method="post">

                    <div class="input-container">
                        <div>
                            <label for="jnum" class="input-label">Job Number:</label>
                            <input type="text" id="jnum" name="jnum" class="input-box" placeholder="Enter job number">
                        </div>
                        <div>
                            <label for="sname" class="input-label">Job Name:</label>
                            <input type="text" id="sname" name="jname" class="input-box" placeholder="Enter job name">
                        </div>
                        <div>
                            <label for="numworkers" class="input-label">numworkers:</label>
                            <input type="text" id="numworkers" name="numworkers" class="input-box"
                                placeholder="Enter numworkers">
                        </div>
                        <div>
                            <label for="city" class="input-label">City:</label>
                            <input type="text" id="city" name="city" class="input-box" placeholder="Enter city">
                        </div>
                    </div>
                </form>

                <div class="button-container">
                    <button class="button enter-data" onclick="submitJobForm(event)">Enter Job Record into
                        Database</button>
                    <button class="button clear-data" onclick="clearData()">Clear Data and Results</button>
                </div>

            </div>
            <div class="dataentry-row">
                <form id="queryInputBox4" action="/Project-4/ShipmentsInsertServlet" method="post">

                    <div class="input-container">
                        <div>
                            <label for="snum" class="input-label">Supply Number:</label>
                            <input type="text" id="snum" name="snum" class="input-box"
                                placeholder="Enter supply number">
                        </div>
                        <div>
                            <label for="pnum" class="input-label">Parts Number:</label>
                            <input type="text" id="pnum" name="pnum" class="input-box" placeholder="Enter parts number">
                        </div>
                        <div>
                            <label for="jnum" class="input-label">Job Number:</label>
                            <input type="text" id="jnum" name="jnum" class="input-box" placeholder="Enter job number">
                        </div>
                        <div>
                            <label for="quantity" class="input-label">quantity:</label>
                            <input type="text" id="quantity" name="quantity" class="input-box"
                                placeholder="Enter quantity">
                        </div>
                    </div>
                </form>

                <div class="button-container">
                    <button class="button enter-data" onclick="submitShipmentForm(event)">Enter Shipment Record into
                        Database</button>
                    <button class="button clear-data" onclick="clearData()">Clear Data and Results</button>
                </div>

            </div>

            <h2>Execution Results</h2>
            <div class="results">
                <%=message%>
            </div>
        </div>
    </body>


    <script>
        function submitSupplyForm(event)
        {
            document.getElementById('queryInputBox1').submit();
        }

        function submitPartForm(event)
        {
            document.getElementById('queryInputBox2').submit();
        }

        function submitJobForm(event)
        {
            document.getElementById('queryInputBox3').submit();
        }

        function submitShipmentForm(event)
        {
            document.getElementById('queryInputBox4').submit();
        }

        function clearData()
        {
            document.querySelector('.results').innerHTML = '';
        }
    </script>

</html>