package QuizProject.QuestionPackage;

import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;


public class MultipleChoiceQuestion extends Question {
	
	public static final String QUESTION_TYPE = "MultipleChoiceQuestion";
	
	public MultipleChoiceQuestion(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int quizId, String backgroundText, String questionText, ArrayList<String> possibleAnswers, int actualAnswer) {
		this.createModelObject(quizId, backgroundText, questionText, null, possibleAnswers, null, actualAnswer);
	}
	
	protected void createModelObject(int quizId, String backgroundText, String questionText, String pictureURL, ArrayList<String> possibleAnswers, ArrayList<Object> additionalParameters, int actualAnswer) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList());

		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}

		super.createModelObject(quizId, QUESTION_TYPE, backgroundText, questionText, pictureURL, possibleAnswers, parameters);
		
		MultipleChoiceAnswer ans = new MultipleChoiceAnswer(0, this.getConnection());
		ArrayList<String> actualAnswers = new ArrayList<String>();
		actualAnswers.add(Integer.toString(actualAnswer));
		ans.createModelObject(this.getID(), -1, actualAnswers);
	}
	
	public int getMaxScore(){
		return 1;
	}
	
	public int getNumAnswers(){
		return this.getMaxScore();
	}
	
	public ArrayList<String> getPossibleAnswers(){
		return this.getCSV("possible_answers");
	}
	
	public int getScore(Object obj){
		if(!(obj instanceof Integer)){ 
			return 0;
		}
		int answer = (Integer) obj;
		if (answer < 0) return 0;
		ArrayList<Integer> answerIDs = this.getAnswerIDs();
		return new MultipleChoiceAnswer(answerIDs.get(0),this.getConnection()).getScore(answer);
	}
	
}
