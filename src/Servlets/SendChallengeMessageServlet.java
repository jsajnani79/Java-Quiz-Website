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
import QuizProject.UserPackage.User;
import QuizProject.UserPackage.MessagePackage.ChallengeMessage;

/**
 * Servlet implementation class SendChallengeMessageServlet
 */
@WebServlet("/SendChallengeMessageServlet")
public class SendChallengeMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendChallengeMessageServlet() {
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
		String recipientName = request.getParameter("challengeUsername");
		int id = Integer.parseInt(request.getParameter("id"));
		Quiz quiz = new Quiz(id, connection);
		if (User.accountExists(recipientName, connection)) {
			ChallengeMessage message = new ChallengeMessage(0, connection);
			message.createModelObject(user.getID(), User.getIdForUsername(recipientName, connection), quiz.getID());
			/// refresh page
			RequestDispatcher dispatch = request.getRequestDispatcher("quiz-summary.jsp?id="+quiz.getID());
			dispatch.forward(request, response);
		} else{
			// Redirect to "Bad User Input" page
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter(); 
			String errorHtml = HTMLHelper.printErrorMessage("You have entered incorrect user information, click the button below to return to the quiz.", "quiz-summary.jsp?id="+quiz.getID());
			out.println(errorHtml);
		}
	}

}
