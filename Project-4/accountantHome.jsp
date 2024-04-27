<!DOCTYPE html>
<html lang="en">


<% String accountantmessage=(String) session.getAttribute("accountantmessage"); if (accountantmessage==null)
    accountantmessage="NULLL" ;%>

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>SQL Query Options</title>
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
                margin-bottom: 20px;
            }

            h2 {
                color: #666;
                margin-bottom: 5px;
            }

            .option-container {
                text-align: left;
                margin-bottom: 15px;
            }

            .option-label {
                font-weight: bold;
                margin-left: 5px;
                vertical-align: middle;
                display: inline-block;
            }

            .checkbox-container {
                display: inline-block;
                vertical-align: middle;
            }

            .button-container {
                display: flex;
                justify-content: space-between;
                margin-top: 20px;
                padding-bottom: 25px;
                border-bottom: 1px solid #ccc;


            }

            .button {
                padding: 15px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                width: 48%;
            }

            .execute {
                background-color: #4caf50;
                color: #fff;
            }

            .clear {
                background-color: #f44336;
                color: #fff;
            }

            .centered {
                text-align: center;
            }

            .highlighted {
                color: red;

            }

            .extra-space {
                margin-bottom: 25px;
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
        </style>
    </head>

    <body>
        <div class="container">
            <h1>Enterprise System</h1>
            <div class="centered">
                <h2>You are connected to the Project 4 Enterprise System database as an <span
                        class="highlighted">accountant-level</span> user.</h2>
                <p class="extra-space">Please select the operation you would like to perform from the list below.</p>
            </div>


            <div class="option-container">
                <div class="checkbox-container">
                    <input type="radio" id="option1" name="option" value="option1">
                </div>
                <label for="option1" class="option-label">Get the maximum status value of all suppliers</label>
            </div>
            <div class="option-container">
                <div class="checkbox-container">
                    <input type="radio" id="option2" name="option" value="option2">
                </div>
                <label for="option2" class="option-label">Get the total weight of all parts</label>
            </div>
            <div class="option-container">
                <div class="checkbox-container">
                    <input type="radio" id="option3" name="option" value="option3">
                </div>
                <label for="option3" class="option-label">Get the total number of shipments</label>
            </div>
            <div class="option-container">
                <div class="checkbox-container">
                    <input type="radio" id="option4" name="option" value="option4">
                </div>
                <label for="option4" class="option-label">Get the name and number of workers of the job with the
                    most
                    workers</label>
            </div>
            <div class="option-container">
                <div class="checkbox-container">
                    <input type="radio" id="option5" name="option" value="option5">
                </div>
                <label for="option5" class="option-label">List the name and status of every supplier</label>
            </div>
            <form id="queryInputBox" action="/Project-4/AccountantServlet" method="post">
                <input type="hidden" id="selectedOption" name="selectedOption" value="">
            </form>
            <div class="button-container">
                <button class="button execute" onclick="executeCommand()">Execute command</button>
                <button class="button clear" onclick="clearResults()">Clear results</button>
            </div>
            <h2>Execution Results</h2>
            <div class="results">
                <%=accountantmessage%>
            </div>
        </div>

        <script>
            function executeCommand()
            {
                var options = document.getElementsByName('option');
                var selectedOption;
                for (var i = 0; i < options.length; i++)
                {
                    if (options[i].checked)
                    {
                        selectedOption = options[i].value;
                        break;
                    }
                }

                if (selectedOption)
                {
                    // Set the value of the hidden input field to the selected option
                    document.getElementById('selectedOption').value = selectedOption;
                    console.log(selectedOption);
                    document.getElementById('queryInputBox').submit();

                } else
                {
                    // No option is selected
                    console.log("Please select an option");
                }
            }

            function clearResults()
            {
                document.querySelector('.results').innerHTML = '';
            }
        </script>

    </body>

</html>