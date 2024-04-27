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

public class JobsInsertServlet extends HttpServlet {
    private static Connection connection;
    private static Statement statement;
    private static int mysqlUpdateValue;
    private int[] updateReturnValues;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String jnum = request.getParameter("jnum");
        String jname = request.getParameter("jname");
        int numworkers = Integer.parseInt(request.getParameter("numworkers"));
        String city = request.getParameter("city");

        String sqlStatement = "INSERT INTO jobs (jnum, jname, numworkers, city) VALUES (?, ?, ?, ?)";

        String message = "";

        try {
            getDBConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, jnum);
            preparedStatement.setString(2, jname);
            preparedStatement.setInt(3, numworkers); // Set as integer
            preparedStatement.setString(4, city);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                message = "New jobs record:(" + jnum + ", " + jname + ", " + numworkers + ", " + city + ") - successfully inserted into database!";
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
