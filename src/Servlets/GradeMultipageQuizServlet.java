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
import QuizProject.QuizPackage.Score;
import QuizProject.UserPackage.User;

/**
 * Servlet implementation class GradeMultipageQuizServlet
 */
@WebServlet("/GradeMultipageQuizServlet")
public class GradeMultipageQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GradeMultipageQuizServlet() {
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

		//		Parameters Required
		long startTime = Long.parseLong(request.getParameter("startTime"));
		int id = Integer.parseInt(request.getParameter("id"));
		int questionIndex = Integer.parseInt(request.getParameter("questionIndex"));
		int answerIndex = Integer.parseInt(request.getParameter("answerIndex"));
		int currScore = Integer.parseInt(request.getParameter("currScore"));
		int accumulatedScore = Integer.parseInt(request.getParameter("accumulatedScore"));
		int gradingAnswerIndex = Integer.parseInt(request.getParameter("gradingAnswerIndex"));
		Quiz quiz = new Quiz(id, connection);
		QuizSession quizSession = (QuizSession) request.getSession().getAttribute(QuizSession.QUIZ_SESSION_ATTRIBUTE_NAME);
		int numAnswers = 0;
		int displayIndex;
		int maxScore = quiz.getMaxScore();
		if (quizSession != null) {
			ArrayList<Question> questions = quizSession.getQuestions();
			if (questionIndex < questions.size()) {
				Question question = questions.get(questionIndex);
				numAnswers = question.getNumAnswers();
				if(question instanceof ResponseQuestion){ 
					displayIndex = gradingAnswerIndex+1;
					String answer = request.getParameter("answer"+displayIndex);
					currScore=quizSession.questionAnswered(question,answer);
				}else if(question instanceof FillInTheBlankQuestion){ 
					displayIndex = gradingAnswerIndex+1;
					String answer = request.getParameter("answer"+displayIndex);
					currScore=quizSession.questionAnswered(question,answer);
				}else if(question instanceof MatchingQuestion){
					ArrayList<String> userAnswers = new ArrayList<String>();
					for(int Mindex=0;Mindex<numAnswers;Mindex++){
						displayIndex = gradingAnswerIndex+1+Mindex;
						String answer = request.getParameter("answer"+displayIndex);
						userAnswers.add(answer);
					}
					currScore=quizSession.questionAnswered(question,userAnswers);
				}else if(question instanceof MultiMultipleChoiceQuestion){
					
					ArrayList<Integer> userAnswers = new ArrayList<Integer>();
					for(int MMCindex=0;MMCindex<numAnswers;MMCindex++){
						displayIndex = gradingAnswerIndex+MMCindex+1;
						String answer = request.getParameter("answer"+displayIndex);
						int chosenAnswer;
						if(answer==null) chosenAnswer=-1;
						else chosenAnswer = Integer.parseInt(answer);
						userAnswers.add(chosenAnswer);
					}
					currScore=quizSession.questionAnswered(question,userAnswers);
				} else if (question instanceof MultipleChoiceQuestion){
					displayIndex = gradingAnswerIndex+1;
					String answer = request.getParameter("answer"+displayIndex);
					int chosenAnswer;
					if(answer==null) chosenAnswer=-1;
					else chosenAnswer = Integer.parseInt(answer);
					currScore=quizSession.questionAnswered(question,chosenAnswer);
				} else if (question instanceof OrderedMultiTextQuestion){
					ArrayList<String> userAnswers = new ArrayList<String>();
					for(int orderedMTindex =0;orderedMTindex<numAnswers;orderedMTindex++){
						displayIndex = gradingAnswerIndex+orderedMTindex+1;
						String answer = request.getParameter("answer"+displayIndex);
						userAnswers.add(answer);
					}				
					currScore=quizSession.questionAnswered(question,userAnswers);
				} else if (question instanceof PictureResponseQuestion){
					displayIndex = gradingAnswerIndex+1;
					String answer = request.getParameter("answer"+displayIndex);
					currScore=quizSession.questionAnswered(question,answer);
				} else if (question instanceof UnorderedMultiTextQuestion){
					ArrayList<String> userAnswers = new ArrayList<String>();
					for(int unorderedMTindex =0;unorderedMTindex<numAnswers;unorderedMTindex++){
						displayIndex = gradingAnswerIndex+unorderedMTindex+1;
						String answer = request.getParameter("answer"+displayIndex);
						userAnswers.add(answer);
					}				
					currScore=quizSession.questionAnswered(question,userAnswers);
				}
			}
			gradingAnswerIndex+=numAnswers;
			accumulatedScore+=currScore;
			questionIndex++;
			
			
			if (questionIndex >= questions.size()) {
				long elapsedTime = System.currentTimeMillis()-startTime;
				if(quizSession instanceof PracticeQuizSession){
					((PracticeQuizSession) quizSession).submitted();
					ArrayList<Question> practiceQuestions = quizSession.getQuestions();
					if(practiceQuestions.size()>0){
						RequestDispatcher dispatch = request.getRequestDispatcher("display-multipage-quiz.jsp?id="+id+"&startTime="+startTime+"&questionIndex="+0+"&answerIndex="+answerIndex+"&gradingAnswerIndex="+gradingAnswerIndex+"&currScore="+currScore+"&accumulatedScore="+accumulatedScore+"&startedNew=false&type=practice");
						dispatch.forward(request, response);
					} else {
						quizSession.endQuiz();
						RequestDispatcher dispatch = request.getRequestDispatcher("quiz-results.jsp?id="+id+"&time="+elapsedTime+"&score="+accumulatedScore+"&maxScore="+maxScore);
						dispatch.forward(request, response);
					}
				}else{
					//Refresh to results page and add score
					quizSession.endQuiz();
					RequestDispatcher dispatch = request.getRequestDispatcher("quiz-results.jsp?id="+id+"&time="+elapsedTime+"&score="+accumulatedScore+"&maxScore="+maxScore);
					dispatch.forward(request, response);
				}
			} else {
				RequestDispatcher dispatch = request.getRequestDispatcher("display-multipage-quiz.jsp?id="+id+"&startTime="+startTime+"&questionIndex="+questionIndex+"&answerIndex="+answerIndex+"&gradingAnswerIndex="+gradingAnswerIndex+"&currScore="+currScore+"&accumulatedScore="+accumulatedScore+"&startedNew=false&type=normal");
				dispatch.forward(request, response);
			}
		} else {
		
		}
	}
}
