package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
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

		String quizTitle = request.getParameter("quizTitle");
		String quizDescription = request.getParameter("quizDescription");
		if(quizTitle.equals("")){
			// Redirect to "No Title" page
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter(); 
			String errorHtml = HTMLHelper.printErrorMessage("You must enter a quiz title. Click the button below to return", "create-quiz.jsp");
			out.println(errorHtml);
		}
		if(quizDescription.equals("")){
			// Redirect to "No Description" page
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter(); 
			String errorHtml = HTMLHelper.printErrorMessage("You must enter a quiz description. Click the button below to return", "create-quiz.jsp");
			out.println(errorHtml);
		}
		
		if(quizTitle.length()>=64){
			// Redirect to "Bad Title Length" page
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter(); 
			String errorHtml = HTMLHelper.printErrorMessage("Quiz title must be less than 64 characters. Click the button below to return", "create-quiz.jsp");
			out.println(errorHtml);
		} else {
			//save title and start building quiz
			String isRandom = request.getParameter("isRandom");
			String isMultiPage = request.getParameter("isMultiPage");
			String hasImmediateCorrection = request.getParameter("hasImmediateCorrection");
			String canTakePracticeMode = request.getParameter("canTakePracticeMode");
			boolean random = false;
			boolean multiPage = false;
			boolean immediateCorrection = false;
			boolean practiceMode = false;
			String categoryName = request.getParameter("category");
			ArrayList<String> tags = DisplayQuestionHelper.parseAnswersString(request.getParameter("quizTags"));
			
			if(isRandom!=null){
				if(Integer.parseInt(isRandom)==1) random=true;
			}
			if(isMultiPage!=null){
				if(Integer.parseInt(isMultiPage)==1) multiPage=true;
			}
			if(hasImmediateCorrection!=null){
				if(Integer.parseInt(hasImmediateCorrection)==1) immediateCorrection=true;
			}
			if(canTakePracticeMode!=null){
				if(Integer.parseInt(canTakePracticeMode)==1) practiceMode=true;
			}
			Quiz quiz = new Quiz(0, connection);
			quiz.createModelObject(quizTitle, quizDescription, user.getID(), random, multiPage, immediateCorrection, categoryName, practiceMode, tags);

			RequestDispatcher dispatch = request.getRequestDispatcher("add-quiz-question.jsp?id=" + quiz.getID());
			dispatch.forward(request, response);
		}
	}

}
