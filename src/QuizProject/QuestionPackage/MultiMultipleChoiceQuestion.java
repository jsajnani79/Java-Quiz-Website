package QuizProject.QuestionPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import QuizProject.DBConnection;

public class MultiMultipleChoiceQuestion extends Question {
	
	public static final String QUESTION_TYPE = "MultiMultipleChoiceQuestion";

	public MultiMultipleChoiceQuestion(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int quizId, String backgroundText, String questionText, ArrayList<String> possibleAnswers, ArrayList<Integer> actualAnswer) {
		this.createModelObject(quizId, backgroundText, questionText, null, possibleAnswers, null, actualAnswer);
	}
	
	protected void createModelObject(int quizId, String backgroundText, String questionText, String pictureURL, ArrayList<String> possibleAnswers, ArrayList<Object> additionalParameters, ArrayList<Integer> actualAnswer) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList());

		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}

		super.createModelObject(quizId, QUESTION_TYPE, backgroundText, questionText, pictureURL, possibleAnswers, parameters);
		
		MultiMultipleChoiceAnswer ans = new MultiMultipleChoiceAnswer(0, this.getConnection());
		ArrayList<String> actualAnswerStrings = new ArrayList<String>();
		for (int selection : actualAnswer){
			actualAnswerStrings.add(Integer.toString(selection));
		}
		ans.createModelObject(this.getID(), -1, actualAnswerStrings);
	}
	
	public int getMaxScore(){
		return 1;
	}
	
	public ArrayList<String> getPossibleAnswers(){
		return this.getCSV("possible_answers");
	}
	
	public int getNumAnswers(){
		return 4;
	}
	
	public int getScore(Object obj){
		if(!(obj instanceof ArrayList<?>)){ 
			return 0;
		}
		ArrayList<Integer> answer = (ArrayList<Integer>) obj;
		ArrayList<Integer> answerIDs = this.getAnswerIDs();
		return new MultiMultipleChoiceAnswer(answerIDs.get(0),this.getConnection()).getScore(answer);
	}
	
}
