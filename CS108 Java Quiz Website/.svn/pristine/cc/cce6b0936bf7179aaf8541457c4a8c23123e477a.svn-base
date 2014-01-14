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
import QuizProject.QuizPackage.Quiz;
import QuizProject.UserPackage.Announcement;
import QuizProject.UserPackage.User;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
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
		//Refresh to admin page
		RequestDispatcher dispatch = request.getRequestDispatcher("admin-page.jsp");
		
		if (requestType.equals("announcement")) {
			Announcement announcement = new Announcement(0, connection);
			long dateCreated = System.currentTimeMillis();
			String announcementText = request.getParameter("announcementText");
			announcement.createModelObject("", announcementText, user.getID(), dateCreated);
			dispatch.forward(request, response);
			
		} else if (requestType.equals("removeUser")){
			String username = request.getParameter("userToRemove");
			User userToRemove = new User(username, connection);
			if(!userToRemove.exists()){
				response.setContentType("text/html"); 
				PrintWriter out = response.getWriter(); 
				String errorHtml = HTMLHelper.printErrorMessage("You have entered an invalid user. Click the button below to return", "admin-page.jsp");
				out.println(errorHtml);
			}else{
				userToRemove.delete();
				dispatch.forward(request, response);
			}
		} else if (requestType.equals("promoteUser")){
			String username = request.getParameter("userToPromote");
			User userToPromote = new User(username, connection);
			if(!userToPromote.exists()){
				response.setContentType("text/html"); 
				PrintWriter out = response.getWriter(); 
				String errorHtml = HTMLHelper.printErrorMessage("You have entered an invalid user. Click the button below to return", "admin-page.jsp");
				out.println(errorHtml);
			}else{
				userToPromote.makeAdmin();
				dispatch.forward(request, response);
			}
			
		} else if (requestType.equals("removeQuiz")){
			int quizId = Integer.parseInt(request.getParameter("id")); /// admin needs to see list of quizzes with delete buttons next to them
			Quiz quizToRemove = new Quiz(quizId, connection);
			quizToRemove.delete();
			dispatch.forward(request, response);
			
		} else if (requestType.equals("clearHistory")){
			int quizId = Integer.parseInt(request.getParameter("id")); /// admin needs to see list of quizzes with clear buttons next to them
			Quiz quizToClear = new Quiz(quizId, connection);
			quizToClear.clearHistory();	
			dispatch.forward(request, response);
		}
		
	}

}
