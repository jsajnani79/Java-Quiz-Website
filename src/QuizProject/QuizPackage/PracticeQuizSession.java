package QuizProject.QuizPackage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import QuizProject.QuestionPackage.Question;
import QuizProject.UserPackage.User;

public class PracticeQuizSession extends QuizSession {

	Map<Question, Integer> correctAnswers;
	private static final int NUM_SUCCESSES_NEEDED = 3; 
	
	public PracticeQuizSession(Quiz rootQuiz, User quizTaker) {
		super(rootQuiz, quizTaker);
		this.correctAnswers = new HashMap<Question, Integer>();
	}
	
	public int questionAnswered(Question currentQuestion, Object answer){
		int scoreOnQuestion = currentQuestion.getScore(answer);
		
		if (scoreOnQuestion == currentQuestion.getMaxScore()) {
			if (this.correctAnswers.containsKey(currentQuestion)) {
				this.correctAnswers.put(currentQuestion, this.correctAnswers.get(currentQuestion) + 1);
			} else {
				this.correctAnswers.put(currentQuestion, 1);
			}
		}
		
		return scoreOnQuestion;
	}
	
	/**
	 * Should be called when the user hits the submit button on the quiz.
	 * This does not mean the quiz is necessarily over.
	 */
	public void submitted() {
		HashSet<Question> toRemove = new HashSet<Question>();
		
		for (Question question : this.questions) {
			if (this.correctAnswers.containsKey(question) && this.correctAnswers.get(question) >= NUM_SUCCESSES_NEEDED) {
				toRemove.add(question);
			}
		}
		
		this.questions.removeAll(toRemove);
	}
		
}
