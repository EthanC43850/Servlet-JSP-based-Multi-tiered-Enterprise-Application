import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;

import com.mysql.cj.jdbc.MysqlDataSource;

public class AuthenticationServlet extends HttpServlet {

    private static Connection connection;
    private ResultSet lookupResults;
    private PreparedStatement pStatement;

    /*
     * public static void main(String[] args) throws SQLException,
     * ClassNotFoundException {
     * 
     * String inboundUsername = "root";
     * String inboundPassword = "Orlando.123";
     * 
     * String credentialsSearchQuery =
     * "select * from usercredentials where login_username = ? and login_password = ?"
     * ;
     * 
     * PreparedStatement pstatement;
     * ResultSet lookupResults;
     * 
     * boolean userCredentialsOK = false;
     * 
     * try {
     * 
     * getDBConnection();
     * pstatement = connection.prepareStatement(credentialsSearchQuery);
     * 
     * pstatement.setString(1, inboundUsername);
     * pstatement.setString(2, inboundPassword);
     * 
     * lookupResults = pstatement.executeQuery();
     * 
     * if (lookupResults.next()) {
     * System.out.println("inboundusername was: " + inboundUsername + " and " +
     * inboundPassword);
     * userCredentialsOK = true;
     * } else {
     * System.out.println("result set had no credentials match - DENIED!");
     * userCredentialsOK = false;
     * }
     * 
     * } catch (SQLException ex) {
     * ex.printStackTrace();
     * }
     * 
     * if (userCredentialsOK) {
     * if (inboundUsername.equals("root")) {
     * System.out.println("redirecting to rootHome.jsp");
     * // response.sendRedirect("/Project4/rootHome.jsp");
     * } else if (inboundUsername.equals("client")) {
     * // response.sendRedirect("/Project4/clientHome.jsp");
     * } else if (inboundUsername.equals("dataentryuser")) {
     * // response.sendRedirect("/Project4/dataEntryHome.jsp");
     * } else if (inboundUsername.equals("dataupdateuser")) {
     * // response.sendRedirect("/Project4/dataUpdateHome.jsp");
     * } else if (inboundUsername.equals("theaccountant")) {
     * // response.sendRedirect("/Project4/accountantHome.jsp");
     * }
     * } else {
     * System.out.println("Redirecting to errorpage.html");
     * // response.sendRedirect("/Project4/errorpage.html");
     * }
     * 
     * }
     */

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String inboundUsername = request.getParameter("username");
        String inboundPassword = request.getParameter("password");

        String credentialsSearchQuery = "select * from usercredentials where login_username = ? and login_password = ?";

        boolean foundUserName;
        boolean foundUserPassword;

        boolean userCredentialsOK = false;

        String sqlStatement = request.getParameter("sqlStatement");
        String message = "";

        try {

            getDBConnection();
            pStatement = connection.prepareStatement(credentialsSearchQuery);

            pStatement.setString(1, inboundUsername);
            pStatement.setString(2, inboundPassword);

            lookupResults = pStatement.executeQuery();

            if (lookupResults.next()) {
                userCredentialsOK = true;
            } else {
                userCredentialsOK = false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (userCredentialsOK) {
            if (inboundUsername.equals("root")) {
                response.sendRedirect("/Project-4/rootHome.jsp");
            } else if (inboundUsername.equals("client1")) {
                response.sendRedirect("/Project-4/clientHome.jsp");
            } else if (inboundUsername.equals("dataentryuser")) {
                response.sendRedirect("/Project-4/dataentryHome.jsp");
            } else if (inboundUsername.equals("theaccountant")) {
                response.sendRedirect("/Project-4/accountantHome.jsp");
            }
        } else {
            response.sendRedirect("/Project-4/errorpage.html");
        }

    }

    private static void getDBConnection() {
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        String userPropertyName = "C:/xampp/tomcat/webapps/Project-4/WEB-INF/lib/systemapp.properties";

        try {
            filein = new FileInputStream(userPropertyName);
            properties.load(filein);
            dataSource = new MysqlDataSource();

            dataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(properties.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(properties.getProperty("MYSQL_DB_PASSWORD"));

            // establish a connection to the dataSource - the database
            connection = dataSource.getConnection();
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }

    }

}