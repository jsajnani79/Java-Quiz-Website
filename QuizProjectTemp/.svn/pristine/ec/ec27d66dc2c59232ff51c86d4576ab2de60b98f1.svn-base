package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import QuizProject.DBConnection;
import QuizProject.UserPackage.User;
import QuizProject.UserPackage.MessagePackage.FriendRequestMessage;

/**
 * Servlet implementation class AcceptFriendServlet
 */
@WebServlet("/AcceptFriendServlet")
public class AcceptFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptFriendServlet() {
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
		int messageId = Integer.parseInt(request.getParameter("id"));
		//ADD FRIEND TO DB
		FriendRequestMessage message = new FriendRequestMessage(messageId,connection);
		message.wasAccepted();
		/// refresh page
		RequestDispatcher dispatch = request.getRequestDispatcher("inbox.jsp");
		dispatch.forward(request, response);
	}

}
