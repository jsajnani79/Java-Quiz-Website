
package QuizProject.QuestionPackage;

import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;

public class FillInTheBlankQuestion extends Question{
	
	public static final String QUESTION_TYPE = "FillInTheBlankQuestion";

	public FillInTheBlankQuestion(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int quizId, String backgroundText, String questionText, ArrayList<String> actualAnswers) {
		this.createModelObject(quizId, backgroundText, questionText, null, null, null, actualAnswers);
	}
	
	protected void createModelObject(int quizId, String backgroundText, String questionText, String pictureURL, ArrayList<String> possibleAnswers, ArrayList<Object> additionalParameters, ArrayList<String> actualAnswers) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList());

		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}

		super.createModelObject(quizId, QUESTION_TYPE, backgroundText, questionText, pictureURL, possibleAnswers, parameters);
		
		FillInTheBlankAnswer ans = new FillInTheBlankAnswer(0, this.getConnection());
		ans.createModelObject(this.getID(), -1, actualAnswers);
	}
	
	public int getMaxScore(){
		return 1;
	}
	
	public int getNumAnswers(){
		return this.getMaxScore();
	}
	
	public int getScore(Object obj){
		if(!(obj instanceof String)){ 
			return 0;
		}
		String currAnswer = (String) obj;
		currAnswer = currAnswer.toLowerCase();
		ArrayList<Integer> answerIDs = this.getAnswerIDs();
		return new ResponseAnswer(answerIDs.get(0),this.getConnection()).getScore(currAnswer);
	}
}

