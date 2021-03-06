package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import QuizProject.DBConnection;
import QuizProject.UserPackage.Crypto;
import QuizProject.UserPackage.User;

/**
 * Servlet implementation class EditProfileServlet
 */
@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfileServlet() {
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
		DBConnection connection = (DBConnection) request.getServletContext().getAttribute(ContextListener.CONNECTION_KEY);
		User user = (User) request.getSession().getAttribute(SessionListener.AUTHENTICATED_USER_KEY);
		String requestType = request.getParameter("type");
		if(requestType.equals("editUsername")){
			String newUsername = request.getParameter("newUsername");
			
			if (User.accountExists(newUsername, connection) || newUsername.equals("")) {
				//Error handling
				response.setContentType("text/html"); 
				PrintWriter out = response.getWriter(); 
				String errorHtml = HTMLHelper.printErrorMessage("This username is not available, click the button below to return to the edit profile page.", "edit-profile.jsp");
				out.println(errorHtml);
			} else {
				user.setUsername(newUsername);
				//Refresh to edit profile page
				RequestDispatcher dispatch = request.getRequestDispatcher("edit-profile.jsp");
				dispatch.forward(request, response);
			}		
		}else if(requestType.equals("editPassword")){
			String currentPassword = request.getParameter("currentPassword");
			String newPassword = request.getParameter("newPassword");
			if(newPassword.equals("")){
				//Error handling
				response.setContentType("text/html"); 
				PrintWriter out = response.getWriter(); 
				String errorHtml = HTMLHelper.printErrorMessage("Password cannot be empty, click the button below to return to the edit profile page.", "edit-profile.jsp");
				out.println(errorHtml);
			}else{
				if (User.checkPassword(user.getUsername(), currentPassword, connection)) {
					user.setPassword(newPassword);
					//Refresh to edit profile page
					RequestDispatcher dispatch = request.getRequestDispatcher("edit-profile.jsp");
					dispatch.forward(request, response);
				} else {
					//Error handling
					response.setContentType("text/html"); 
					PrintWriter out = response.getWriter(); 
					String errorHtml = HTMLHelper.printErrorMessage("The password you inputted is incorrect, click the button below to return to the edit profile page.", "edit-profile.jsp");
					out.println(errorHtml);
				}
			}
		}
	}
}
