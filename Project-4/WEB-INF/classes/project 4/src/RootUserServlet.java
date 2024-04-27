import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.nimbus.State;

import java.sql.*;

import com.mysql.cj.jdbc.MysqlDataSource;

public class RootUserServlet extends HttpServlet {
    private static Connection connection;
    private static Statement statement;
    private static int mysqlUpdateValue;
    private static int businessLogicUpdateValue = 0;
    private int[] updateReturnValues;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String sqlStatement = request.getParameter("sqlStatement");
        String message = "";
        String businessLogicOutput = "<br><br>Business Logic Not Triggered";

        try {
            getDBConnection();
            Statement statement = connection.createStatement();
            sqlStatement = sqlStatement.trim();
            String sqlType = sqlStatement.substring(0, 6);

            if (sqlType.equals("select")) {
                ResultSet resultSet = statement.executeQuery(sqlStatement);
                message = ResultSetToHTMLFormatterClass.getHtmlRows(resultSet);
                resultSet.close();
            } else {

                mysqlUpdateValue = statement.executeUpdate(sqlStatement);

                if (sqlStatement.toLowerCase().contains("shipments") && sqlStatement.toLowerCase().contains("insert")) {
                    try {
                        int value = extractLastInteger(sqlStatement);

                        if (value >= 100) {
                            updateSuppliersTable();

                            businessLogicOutput = "Business Logic Detected! - Updating Supplier Status<br>";
                            businessLogicOutput = "Business Logic updated " + businessLogicUpdateValue
                                    + " supplier status marks.";
                        } else {
                            businessLogicOutput = "<br><br>Business Logic Detected! - Updating Supplier Status<br>";
                            businessLogicOutput = "Business Logic updated " + businessLogicUpdateValue
                                    + " supplier status marks.";
                        }
                    } catch (NumberFormatException e) {
                        // Handle the case where the 4th parameter is not a valid integer
                    }

                } else if (sqlStatement.toLowerCase().contains("shipments")) {
                    businessLogicOutput = "Business Logic Detected! - Updating Supplier Status<br>";
                    businessLogicOutput = "Business Logic updated " + businessLogicUpdateValue
                            + " supplier status marks";
                }

                message += "The statement executed successfully. <br>" + mysqlUpdateValue
                        + " row(s) affected.<br><br>";
            }

            message += businessLogicOutput;

            statement.close();

        } catch (

        SQLException e) {
            message = "ERROR: " + e.getMessage();
        }

        HttpSession session = request
                .getSession();
        session.setAttribute("message", message);
        session.setAttribute("sqlStatement", sqlStatement);
        RequestDispatcher dispatcher = getServletContext()
                .getRequestDispatcher("/rootHome.jsp");
        dispatcher.forward(request, response);
    }

    private void updateSuppliersTable() throws SQLException {

        businessLogicUpdateValue = statement.executeUpdate(
                "update suppliers set status = status + 5 where snum in (select snum from shipments where quantity >=100)");
    }

    private int extractLastInteger(String input) {
        // Regular expression to match the last occurrence of an integer
        Pattern pattern = Pattern.compile("\\b\\d+\\b");
        Matcher matcher = pattern.matcher(input);

        int lastInteger = 0;

        // Find the last occurrence of the integer in the input string
        while (matcher.find()) {
            lastInteger = Integer.parseInt(matcher.group());
        }

        return lastInteger;
    }

    private static void getDBConnection() {
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        String userPropertyName = "C:/xampp/tomcat/webapps/Project-4/WEB-INF/lib/root.properties";

        try {
            filein = new FileInputStream(userPropertyName);
            properties.load(filein);
            dataSource = new MysqlDataSource();

            dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));

            // establish a connection to the dataSource - the database
            connection = dataSource.getConnection();
            statement = connection.createStatement();
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }

    }
}
