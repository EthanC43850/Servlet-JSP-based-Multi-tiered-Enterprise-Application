import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

import com.mysql.cj.jdbc.MysqlDataSource;

public class ShipmentsInsertServlet extends HttpServlet {
    private static Connection connection;
    private static Statement statement;
    private static int mysqlUpdateValue;
    private int[] updateReturnValues;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String snum = request.getParameter("snum");
        String pnum = request.getParameter("pnum");
        String jnum = request.getParameter("jnum");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        String sqlStatement = "INSERT INTO shipments (snum, pnum, jnum, quantity) VALUES (?, ?, ?, ?)";
        boolean businessLogicTriggered = false;
        String message = "";
        try {
            getDBConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, snum);
            preparedStatement.setString(2, pnum);
            preparedStatement.setString(3, jnum); // Set as integer
            preparedStatement.setInt(4, quantity);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                message = "New shipments record:(" + snum + ", " + pnum + ", " + jnum + ", " + quantity
                        + ") - successfully inserted into database!";
            } else {
                message = "No rows affected.";
            }

            if (quantity >= 100)
            {
                updateSuppliersTable();
                message += " Business Logic Triggered.";
            }
            else
            {
                message += " Business Logic Not Triggered.";
            }

            preparedStatement.close();

        } catch (SQLException e) {
            message = "ERROREROEROERR: " + e.getMessage();
        } catch (NumberFormatException e) {
            message = "ERROR: Status must be an integer.";
        }

        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        // session.setAttribute("sqlStatement", sqlStatement);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataentryHome.jsp");
        dispatcher.forward(request, response);
    }

    private void updateSuppliersTable() throws SQLException {

        statement.executeUpdate(
                "update suppliers set status = status + 5 where snum in (select snum from shipments where quantity >=100)");
    }

    private static void getDBConnection() {
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        String userPropertyName = "C:/xampp/tomcat/webapps/Project-4/WEB-INF/lib/dataentry.properties";

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
