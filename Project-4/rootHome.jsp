<!DOCTYPE html>
<html lang="en">

<% String message=(String) session.getAttribute("message"); if (message==null) message="NULLL" ; String
    sqlStatement=(String) session.getAttribute("sqlStatement"); %>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Command Execution</title>
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
                max-width: 600px;
            }

            h1 {
                color: #333;
                margin-bottom: 10px;
            }

            h2 {
                color: #666;
                margin-bottom: 20px;
            }

            input[type="text"] {
                width: 100%;
                padding: 20px;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
                box-sizing: border-box;
                font-size: 16px;
            }

            .button-container {
                display: flex;
                justify-content: space-between;
                margin-bottom: 20px;
            }

            .button {
                flex-grow: 1;
                padding: 15px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
            }

            .execute {
                background-color: #4caf50;
                color: #fff;
                margin-right: 10px;
            }

            .reset {
                background-color: #f44336;
                color: #fff;
                margin-right: 10px;
            }

            .clear {
                background-color: #ffeb3b;
                color: #333;
            }

            .results {
                padding: 10px;
                font-weight: bold;
                overflow-y: auto;
                max-height: 200px;

            }

            .results table {
                width: 100%;
                border-collapse: collapse;
                overflow-x: auto;
            }

            .results th,
            .results td {
                padding: 8px;
                border: 1px solid #ddd;
            }

            .results th {
                background-color: #f2f2f2;
                font-weight: bold;
            }

            .highlighted {
                color: red;

            }

            .centered {
                text-align: center;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h1>Enterprise System</h1>
            <div class="centered">
                <h2>You are connected to the Project 4 Enterprise System database as a <span
                        class="highlighted">root-level</span> user.</h2>
                <p>Please enter any SQL query or update command in the box below.</p>
            </div>
            <form id="queryInputBox" action="/Project-4/RootUserServlet" method="post">
                <input id="userInput" type="text" name="sqlStatement" placeholder="Enter your command">

            </form>

            <div class="button-container">
                <button class="button execute" onclick="executeCommand()">Execute Command</button>
                <button class="button reset" onclick="resetForm()">Reset Form</button>
                <button class="button clear" onclick="clearResults()">Clear Results</button>
            </div>
            <h2>Execution Results</h2>
            <div class="results">
                <%=message%>
            </div>

            <p>
            <table id="data">

            </table>
            </p>

        </div>


        <script>
            function executeCommand()
            {
                document.getElementById('queryInputBox').submit();
            }

            function resetForm()
            {
                //document.getElementById('queryInputBox').reset();
                document.getElementById('queryInputBox').reset();
            }

            function clearResults()
            {
                document.querySelector('.results').innerHTML = '';
            }
        </script>
    </body>

</html>