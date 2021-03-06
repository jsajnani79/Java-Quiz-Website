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
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext context = request.getServletContext();
		
		DBConnection connection = (DBConnection) context.getAttribute(ContextListener.CONNECTION_KEY);
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
//		response.setContentType("text/html"); 
//		PrintWriter out = response.getWriter(); 
		
		if (User.accountExists(name, connection)) {
			RequestDispatcher dispatch = request.getRequestDispatcher("name-in-use.jsp");
			dispatch.forward(request, response);
		} else {
			// Add the authenticated user as an attribute to the session
			if(name.equals("")||password.equals("")){
				response.setContentType("text/html"); 
				PrintWriter out = response.getWriter(); 
				String errorHtml = HTMLHelper.printErrorMessage("You must enter a value for both the username and password fields, click the button below to return to the creation page", "create-new-account.html");
				out.println(errorHtml);
			}else{
			User authenticatedUser = new User(name, connection);
			authenticatedUser.createModelObject(name, password);
			HttpSession session = request.getSession();
			session.setAttribute(SessionListener.AUTHENTICATED_USER_KEY, authenticatedUser);
			
			// Redirect to "User Welcome" page
			RequestDispatcher dispatch = request.getRequestDispatcher("user-home.jsp");
			dispatch.forward(request, response);
			}			
		}
		
	}

}
