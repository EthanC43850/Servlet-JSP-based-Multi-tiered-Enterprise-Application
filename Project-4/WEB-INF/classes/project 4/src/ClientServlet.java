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

public class ClientServlet extends HttpServlet {
    private static Connection connection;
    private static Statement statement;
    private static int mysqlUpdateValue;
    private int[] updateReturnValues;


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


        String sqlStatement = request.getParameter("sqlStatement");
        String message = "";

        try {
            getDBConnection();
            Statement statement = connection.createStatement();
            sqlStatement = sqlStatement.trim();
            String sqlType = sqlStatement.substring(0, 6);

            if (sqlType.equals("select")) {
                ResultSet resultSet = statement.executeQuery(sqlStatement);
                message = ResultSetToHTMLFormatterClass.getHtmlRows(resultSet);
            } else {
                mysqlUpdateValue = statement.executeUpdate(sqlStatement);
            }

            statement.close();

        } catch (SQLException e) {
            message = "ERROR: " + e.getMessage();
            
            // "<tr bgcolor=#ff0000><td><font color=#ffffff><b>Error executing the SQL statement:</b><br>"
            //         + e.getMessage() + "</tr></br>";
        }


        HttpSession session = request.getSession();
        session.setAttribute("message", message);
        session.setAttribute("sqlStatement", sqlStatement);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/clientHome.jsp");
        dispatcher.forward(request, response);
    }









    

    private static void getDBConnection() {
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        String userPropertyName = "C:/xampp/tomcat/webapps/Project-4/WEB-INF/lib/client.properties";

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
