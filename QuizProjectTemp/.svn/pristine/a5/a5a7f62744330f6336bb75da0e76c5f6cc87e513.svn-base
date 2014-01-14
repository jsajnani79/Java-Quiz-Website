package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import QuizProject.DBConnection;
import QuizProject.QuizPackage.Quiz;
import QuizProject.UserPackage.User;

/**
 * Servlet implementation class AddQuestionToQuiz
 */
@WebServlet("/AddQuestionToQuiz")
public class AddQuestionToQuiz extends HttpServlet {
	
	private static final int NUMBER_ANSWER_OPTIONS = 4;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddQuestionToQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		RequestDispatcher dispatch = request.getRequestDispatcher("add-quiz-question.jsp?id="+id);
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ServletContext context = request.getServletContext();
		HttpSession session = request.getSession(); 
		DBConnection connection = (DBConnection) context.getAttribute(ContextListener.CONNECTION_KEY);
		User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY); 
		Quiz quiz = new Quiz(id, user.getConnection());
		
		String questionText = request.getParameter("questionText");
		String backgroundText = request.getParameter("questionBackground");
		String answersString =  request.getParameter("questionAnswers");
		String questionType = request.getParameter("questionType");
		
		if (questionText.equals("")) {
			// Redirect to "Bad User Input" page
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter(); 
			String errorHtml = HTMLHelper.printErrorMessage("You must enter a value for question text, click the button below to return to the creation page.", "add-quiz-question.jsp?id="+id);
			out.println(errorHtml);
		} else if (questionType.equals("FillInTheBlankQuestion") && (request.getParameter("firstSentence").equals("")||request.getParameter("secondSentence").equals("")) ){
					// Redirect to "Bad User Input" page
					response.setContentType("text/html"); 
					PrintWriter out = response.getWriter(); 
					String errorHtml = HTMLHelper.printErrorMessage("You must enter a value for question text, click the button below to return to the creation page.", "add-quiz-question.jsp?id="+id);
					out.println(errorHtml);
		}
		else  {
			if (questionType.equals("OrderedMultiTextQuestion")||questionType.equals("UnorderedMultiTextQuestion")){
				ArrayList<ArrayList<String>> actualAnswers = new ArrayList<ArrayList<String>>();
				int numAnswers = Integer.parseInt(request.getParameter("numAnswers"));
				for (int index = 0;index<numAnswers;index++) {
					String answerString = request.getParameter("answer"+index);
					ArrayList<String> currAnswers = DisplayQuestionHelper.parseAnswersString(answerString);
					actualAnswers.add(currAnswers);
				}
				if (questionType.equals("OrderedMultiTextQuestion")) {
					quiz.addOrderedMultiTextQuestion(backgroundText, questionText, actualAnswers);
				} else if (questionType.equals("UnorderedMultiTextQuestion")) {
					quiz.addUnorderedMultiTextQuestion(backgroundText, questionText, actualAnswers);
				}
			}
			else if(questionType.equals("MatchingQuestion")||questionType.equals("MultiMultipleChoiceQuestion")||questionType.equals("MultipleChoiceQuestion")) {
				ArrayList<String> possibleAnswers = new ArrayList<String>();
				ArrayList<String> actualAnswers = new ArrayList<String>();
				for(int index=0;index<NUMBER_ANSWER_OPTIONS;index++){
					if (request.getParameter("option"+index).equals("")||request.getParameter("option"+index)==null) {
						possibleAnswers.add(" ");
					} else {
						possibleAnswers.add(request.getParameter("option"+index));
					}
				}
				if (questionType.equals("MatchingQuestion")) {
					for (int index=0;index<NUMBER_ANSWER_OPTIONS;index++) {
						actualAnswers.add(request.getParameter("answer"+index));
					}
					quiz.addMatchingQuestion(backgroundText, questionText, possibleAnswers, actualAnswers); //two ordered lists
				} else if (questionType.equals("MultiMultipleChoiceQuestion")) {
					ArrayList<Integer> actualAnswersInt = new ArrayList<Integer>();
					for (int index=0;index<NUMBER_ANSWER_OPTIONS;index++){
						if (request.getParameter("checkbox"+index)==null||request.getParameter("checkbox"+index).equals("")||request.getParameter("checkbox"+index).equals("null")) {
							actualAnswersInt.add(-1);
						} else {
							actualAnswersInt.add(Integer.parseInt(request.getParameter("checkbox"+index)));
						}
					}
					quiz.addMultiMultipleChoiceQuestion(backgroundText, questionText, possibleAnswers, actualAnswersInt);
				} else if (questionType.equals("MultipleChoiceQuestion")) {
					int actualAnswer;
					if (request.getParameter("multChoice[]")==null||request.getParameter("multChoice[]").equals("")||request.getParameter("multChoice[]").equals("null")){
						actualAnswer = -1;
					} else {
						actualAnswer = Integer.parseInt(request.getParameter("multChoice[]"));
					}
					quiz.addMultipleChoiceQuestion(backgroundText, questionText, possibleAnswers, actualAnswer);
				}
			} else if (questionType.equals("ResponseQuestion")||questionType.equals("FillInTheBlankQuestion")||questionType.equals("PictureResponseQuestion")){
				ArrayList<String> actualAnswers = DisplayQuestionHelper.parseAnswersString(answersString);
				if (questionType.equals("ResponseQuestion")){
					quiz.addResponseQuestion(backgroundText, questionText, actualAnswers);
				} else if (questionType.equals("FillInTheBlankQuestion")) {
					questionText = request.getParameter("firstSentence")+"  _____________  "+request.getParameter("secondSentence");
					quiz.addFillInTheBlankQuestion(backgroundText, questionText, actualAnswers);
				} else if (questionType.equals("PictureResponseQuestion")) {
					String pictureURL = request.getParameter("pictureURL");
					quiz.addPictureResponseQuestion(backgroundText, questionText, pictureURL, actualAnswers);
				}
			}		
			RequestDispatcher dispatch = request.getRequestDispatcher("add-quiz-question.jsp?id="+id);
			dispatch.forward(request, response);
		}
	}
}
