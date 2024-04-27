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

public class SuppliersInsertServlet extends HttpServlet {
    private static Connection connection;
    private static Statement statement;
    private static int mysqlUpdateValue;
    private int[] updateReturnValues;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String snum = request.getParameter("snum");
        String sname = request.getParameter("sname");
        int status = Integer.parseInt(request.getParameter("status"));
        // make try catch block and change the message if no int found

        String city = request.getParameter("city");

        String sqlStatement = "INSERT INTO suppliers (snum, sname, status, city) VALUES (?, ?, ?, ?)";

        String message = "";

        try {
            getDBConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, snum);
            preparedStatement.setString(2, sname);
            preparedStatement.setInt(3, status); // Set as integer
            preparedStatement.setString(4, city);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                message = "New shipments record:(" + snum + ", " + sname + ", " + status + ", " + city + ") - successfully inserted into database!";
            } else {
                message = "No rows affected.";
            }
    
            preparedStatement.close();

        } catch (SQLException e) {
            message = "ERROREROEROERR: " + e.getMessage();
        } catch (NumberFormatException e) {
            message = "ERROR: Status must be an integer.";
        }

        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        //session.setAttribute("sqlStatement", sqlStatement);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataentryHome.jsp");
        dispatcher.forward(request, response);
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
