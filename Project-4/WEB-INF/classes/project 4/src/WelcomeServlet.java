// A simple servlet to process an HTTP get request.
// Main servlet in first-example web-app

// Users of Tomcat 10 onwards should be aware that, as a result of the move from Java EE to Jakarta EE as part of the
// transfer of Java EE to the Eclipse Foundation, the primary package for all implemented APIs has changed
// from javax.* to jakarta.*. This will almost certainly require code changes to enable applications to migrate
// from Tomcat 9 and earlier to Tomcat 10 and later. 

import javax.servlet.*; //used for Tomcat 9.x and earlier only
import javax.servlet.http.*; //used for Tomcat 9.x and earlier only
// import jakarta.servlet.*;
// import jakarta.servlet.http.*;
import java.io.*;

@SuppressWarnings("serial")
public class WelcomeServlet extends HttpServlet {

   // process "get" requests from clients
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {

      String username = request.getParameter("username");
      String password = request.getParameter("password");

      HttpSession session = request.getSession();
      session.setAttribute("username", username);
      session.setAttribute("password", password);
      RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/authentication.jsp");
      dispatcher.forward(request, response);
   }

} // end WelcomeServlet class
