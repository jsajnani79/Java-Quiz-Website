package QuizProject.QuizPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import QuizProject.QuestionPackage.Question;
import QuizProject.UserPackage.User;

/**
 * This object is only meant to be used in a temporary manner.
 * Instances of this object are not meant to be stored in the
 * database as permanent objects.
 * @author ashkonfarhangi
 *
 */
public class QuizSession {
	
	public static final String QUIZ_SESSION_ATTRIBUTE_NAME = "quiz_session";
		
	Quiz rootQuiz;
	protected ArrayList<Question> questions;
	User quizTaker;
	int score;
	long startTime;
	long endTime;
	
	
	public QuizSession(Quiz rootQuiz, User quizTaker) {
		this.rootQuiz = rootQuiz;
		
		this.questions = rootQuiz.getQuestions();
		if (this.rootQuiz.isRandom()) {
			// Randomize the order the questions appear in the quiz
			long seed = System.nanoTime();
			Collections.shuffle(this.questions, new Random(seed));
		}
		
		this.quizTaker = quizTaker;
	}
	
	public int questionAnswered(Question currentQuestion, Object answer){
		int scoreOnQuestion = currentQuestion.getScore(answer);
		this.score += scoreOnQuestion;
		return scoreOnQuestion;
	}
	
	public void startQuiz() {
		this.startTime = System.currentTimeMillis();
	}
	
	public void endQuiz() {
		this.endTime = System.currentTimeMillis();
		this.rootQuiz.quizTaken(this);
		quizTaker.tookQuiz(this);
	}
	
	public int getScore() {
		return this.score;
	}
	
	public Quiz getRootQuiz() {
		return this.rootQuiz;
	}
	
	public ArrayList<Question> getQuestions() {
		return this.questions;
	}
	
	public User getTaker() {
		return this.quizTaker;
	}
	
	public long getDateTaken() {
		return this.startTime;
	}
		
	public long getDuration() {
		if (this.startTime > 0 && this.endTime > 0) {
			return this.endTime - this.startTime;
		}
		return -1;
	}
	
}
