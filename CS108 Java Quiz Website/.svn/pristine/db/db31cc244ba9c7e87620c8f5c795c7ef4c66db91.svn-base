package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import QuizProject.DBConnection;
import QuizProject.UserPackage.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		
		DBConnection connection = (DBConnection) context.getAttribute(ContextListener.CONNECTION_KEY);
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter(); 
		
		if (User.accountExists(name, connection) && User.checkPassword(name, password, connection)) {
			// Add the authenticated user as an attribute to the session
			HttpSession session = request.getSession(); 
			session.setAttribute(SessionListener.AUTHENTICATED_USER_KEY, new User(name, connection));
			
			// Redirect to Homepage
			RequestDispatcher dispatch = request.getRequestDispatcher("user-home.jsp");
			dispatch.forward(request, response);
		} else {
			// Generate "Please Try Again" page
			RequestDispatcher dispatch = request.getRequestDispatcher("please-try-again.html");
			dispatch.forward(request, response);
		}
	}

}
