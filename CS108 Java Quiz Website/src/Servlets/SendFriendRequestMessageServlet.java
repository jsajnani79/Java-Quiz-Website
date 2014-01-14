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
import QuizProject.UserPackage.User;
import QuizProject.UserPackage.MessagePackage.ChallengeMessage;
import QuizProject.UserPackage.MessagePackage.FriendRequestMessage;

/**
 * Servlet implementation class SendFriendRequestMessageServlet
 */
@WebServlet("/SendFriendRequestMessageServlet")
public class SendFriendRequestMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendFriendRequestMessageServlet() {
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
		int recipientId = Integer.parseInt(request.getParameter("id"));
		User recipientUser = new User(recipientId, user.getConnection());
		if (User.accountExists(recipientUser.getUsername(), connection)) {
			FriendRequestMessage message = new FriendRequestMessage(0,connection);
			message.createModelObject(user.getID(), recipientId);
			/// refresh page
			RequestDispatcher dispatch = request.getRequestDispatcher("user-profile.jsp?id="+recipientId);
			dispatch.forward(request, response);
		} else{
			// Redirect to "User no longer exists" page
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter(); 
			String errorHtml = HTMLHelper.printErrorMessage("This user no longer exists, click the button below to return to the inbox.", "user-profile.jsp?id="+recipientId);
			out.println(errorHtml);
		}
		
	}

}
