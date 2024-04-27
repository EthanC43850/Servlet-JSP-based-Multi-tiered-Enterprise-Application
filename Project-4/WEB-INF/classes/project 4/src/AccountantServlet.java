import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

import com.mysql.cj.jdbc.MysqlDataSource;

public class AccountantServlet extends HttpServlet {

    private static Connection connection;
    private static Statement statement;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String selectedOption = request.getParameter("selectedOption");
        String message = "";

        try {
            getDBConnection();

            if (selectedOption.equals("option1")) {
                CallableStatement callableStatement = connection
                        .prepareCall("{call Get_The_Maximum_Status_Of_All_Suppliers()}");
                ResultSet resultSet = callableStatement.executeQuery();
                message = ResultSetToHTMLFormatterClass.getHtmlRows(resultSet);
            } else if (selectedOption.equals("option2")) {
                CallableStatement callableStatement = connection
                        .prepareCall("{call  Get_The_Sum_Of_All_Parts_Weights()}");
                ResultSet resultSet = callableStatement.executeQuery();
                message = ResultSetToHTMLFormatterClass.getHtmlRows(resultSet);
            } else if (selectedOption.equals("option3")) {
                CallableStatement callableStatement = connection
                        .prepareCall("{call Get_The_Total_Number_Of_Shipments()}");
                ResultSet resultSet = callableStatement.executeQuery();
                message = ResultSetToHTMLFormatterClass.getHtmlRows(resultSet);
            } else if (selectedOption.equals("option4")) {
                CallableStatement callableStatement = connection
                        .prepareCall("{call Get_The_Name_Of_The_Job_With_The_Most_Workers()}");
                ResultSet resultSet = callableStatement.executeQuery();
                message = ResultSetToHTMLFormatterClass.getHtmlRows(resultSet);
            } else if (selectedOption.equals("option5")) {
                CallableStatement callableStatement = connection
                        .prepareCall("{call List_The_Name_And_Status_Of_All_Suppliers()}");
                ResultSet resultSet = callableStatement.executeQuery();
                message = ResultSetToHTMLFormatterClass.getHtmlRows(resultSet);
            }

        } catch (Exception e) {
            message = "<tr bgcolor=#ff0000><td><font color=#ffffff><b>Error executing the SQL statement:</b><br>"
                    + e.getMessage() + "</tr>";
        }

        HttpSession session = request.getSession();
        session.setAttribute("accountantmessage", message);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/accountantHome.jsp");
        dispatcher.forward(request, response);

    }

    private static void getDBConnection() {
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        String userPropertyName = "C:/xampp/tomcat/webapps/Project-4/WEB-INF/lib/accountant.properties";

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
