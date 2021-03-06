package Servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import QuizProject.DBConnection;
import QuizProject.QuestionPackage.FillInTheBlankQuestion;
import QuizProject.QuestionPackage.MatchingQuestion;
import QuizProject.QuestionPackage.MultiMultipleChoiceQuestion;
import QuizProject.QuestionPackage.MultipleChoiceQuestion;
import QuizProject.QuestionPackage.OrderedMultiTextQuestion;
import QuizProject.QuestionPackage.PictureResponseQuestion;
import QuizProject.QuestionPackage.Question;
import QuizProject.QuestionPackage.ResponseQuestion;
import QuizProject.QuestionPackage.UnorderedMultiTextQuestion;
import QuizProject.QuizPackage.PracticeQuizSession;
import QuizProject.QuizPackage.Quiz;
import QuizProject.QuizPackage.QuizSession;

/**
 * Servlet implementation class GradeQuizServlet
 */
@WebServlet("/GradeQuizServlet")
public class GradeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradeQuizServlet() {
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
		QuizSession quizSession = (QuizSession) request.getSession().getAttribute(QuizSession.QUIZ_SESSION_ATTRIBUTE_NAME);
		
		long startTime = Long.parseLong(request.getParameter("startTime"));
		long elapsedTime = System.currentTimeMillis()-startTime;
		int id = Integer.parseInt(request.getParameter("id"));
		Quiz quiz = new Quiz(id, connection);
		int maxScore = quiz.getMaxScore();
		
		int userScore = 0;
		int numAnswers = 0;
		int answerIndex = 0;
		int displayIndex;
		
		if (quizSession != null) {
			ArrayList<Question> questions = quizSession.getQuestions();
			
			for (Question question : questions){
				numAnswers = question.getNumAnswers();
				
				if (question instanceof ResponseQuestion) { 
					displayIndex = answerIndex+1;
					String answer = request.getParameter("answer"+displayIndex);
					quizSession.questionAnswered(question,answer);
					
				} else if (question instanceof FillInTheBlankQuestion) { 
					displayIndex = answerIndex+1;
					String answer = request.getParameter("answer" + displayIndex);
					quizSession.questionAnswered(question,answer);
					
				} else if (question instanceof MatchingQuestion) {
					ArrayList<String> userAnswers = new ArrayList<String>();
					for (int mindex = 0; mindex < numAnswers; mindex++){
						displayIndex = answerIndex + 1 + mindex;
						String answer = request.getParameter("answer" + displayIndex);
						userAnswers.add(answer);
					}
					quizSession.questionAnswered(question,userAnswers);
					
				} else if (question instanceof MultiMultipleChoiceQuestion) {
					ArrayList<Integer> userAnswers = new ArrayList<Integer>();
					for(int MMCindex=0;MMCindex<numAnswers;MMCindex++){
						displayIndex = answerIndex+MMCindex+1;
						String answer = request.getParameter("answer" + displayIndex);
						int chosenAnswer;
						if (answer==null) chosenAnswer=-1;
						else chosenAnswer = Integer.parseInt(answer);
						userAnswers.add(chosenAnswer);
					}
					quizSession.questionAnswered(question,userAnswers);
					
				} else if (question instanceof MultipleChoiceQuestion){
					displayIndex = answerIndex+1;
					String answer = request.getParameter("answer"+displayIndex);
					int chosenAnswer;
					if(answer==null) chosenAnswer=-1;
					else chosenAnswer = Integer.parseInt(answer);
					quizSession.questionAnswered(question,chosenAnswer);
					
				} else if (question instanceof OrderedMultiTextQuestion){
					ArrayList<String> userAnswers = new ArrayList<String>();
					for(int orderedMTindex =0;orderedMTindex<numAnswers;orderedMTindex++){
						displayIndex = answerIndex+orderedMTindex+1;
						String answer = request.getParameter("answer"+displayIndex);
						userAnswers.add(answer);
					}				
					quizSession.questionAnswered(question,userAnswers);
					
				} else if (question instanceof PictureResponseQuestion){
					displayIndex = answerIndex+1;
					String answer = request.getParameter("answer"+displayIndex);
					quizSession.questionAnswered(question,answer);
					
				} else if (question instanceof UnorderedMultiTextQuestion){
					ArrayList<String> userAnswers = new ArrayList<String>();
					for(int unorderedMTindex =0;unorderedMTindex<numAnswers;unorderedMTindex++){
						displayIndex = answerIndex+unorderedMTindex+1;
						String answer = request.getParameter("answer"+displayIndex);
						userAnswers.add(answer);
					}				
					quizSession.questionAnswered(question,userAnswers);
				} answerIndex+=numAnswers;
			}
			if(quizSession instanceof PracticeQuizSession){
				((PracticeQuizSession) quizSession).submitted();
				ArrayList<Question> practiceQuestions = quizSession.getQuestions();
				if(practiceQuestions.size()>0){
					RequestDispatcher dispatch = request.getRequestDispatcher("display-quiz.jsp?id="+id+"&questionIndex="+0+"&type=practice&startedNew=false");
					dispatch.forward(request, response);
				} else {
					quizSession.endQuiz();
					RequestDispatcher dispatch = request.getRequestDispatcher("quiz-results.jsp?id="+id+"&time="+elapsedTime+"&score="+userScore+"&maxScore="+maxScore);
					dispatch.forward(request, response);
				}
			}else{
				quizSession.endQuiz();
				RequestDispatcher dispatch = request.getRequestDispatcher("quiz-results.jsp?id="+id+"&time="+elapsedTime+"&score="+userScore+"&maxScore="+maxScore);
				dispatch.forward(request, response);
			}
		} else {
			// error occurred
		}
		
		
	}

}
