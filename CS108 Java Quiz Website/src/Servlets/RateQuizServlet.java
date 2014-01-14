package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import QuizProject.DBConnection;
import QuizProject.QuizPackage.Quiz;
import QuizProject.UserPackage.User;

/**
 * Servlet implementation class RateQuizServlet
 */
@WebServlet("/RateQuizServlet")
public class RateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RateQuizServlet() {
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
		int id = Integer.parseInt(request.getParameter("id"));
		int rating;
		if(request.getParameter("rating")==null){
			rating = -1;
		}else {
			rating = Integer.parseInt(request.getParameter("rating"));
		}
		String ratingText = request.getParameter("ratingText");
		Quiz quiz = new Quiz(id, connection);
		quiz.wasReviewed(rating, ratingText);
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz-summary.jsp?id="+quiz.getID());
		dispatch.forward(request, response);
	}

}
