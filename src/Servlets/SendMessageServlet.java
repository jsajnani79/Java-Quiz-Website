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
import QuizProject.UserPackage.MessagePackage.NoteMessage;

/**
 * Servlet implementation class SendMessageServlet
 */
@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMessageServlet() {
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
		
		String recipientName = request.getParameter("recipient");
		String composedText = request.getParameter("composedText");
		
		if(composedText.length()>=1000){
			// Redirect to "Bad Message Length" page
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter(); 
			String errorHtml = HTMLHelper.printErrorMessage("Your message input is over 1000 characters and cannot be sent, click the button below to return to the inbox.", "inbox.jsp");
			out.println(errorHtml);
		}
		if (User.accountExists(recipientName, connection)) {
			NoteMessage message = new NoteMessage(0, connection);
			message.createModelObject(user.getID(), User.getIdForUsername(recipientName, connection), composedText);
			/// refresh page to show new message in outbox
			RequestDispatcher dispatch = request.getRequestDispatcher("inbox.jsp");
			dispatch.forward(request, response);
		} else {
			// Redirect to "Bad User Input" page
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter(); 
			String errorHtml = HTMLHelper.printErrorMessage("You have entered incorrect user information, click the button below to return to the inbox.", "inbox.jsp");
			out.println(errorHtml);
		}
	}

}
