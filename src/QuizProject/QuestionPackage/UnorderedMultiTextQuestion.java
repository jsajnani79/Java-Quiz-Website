package QuizProject.QuestionPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import QuizProject.DBConnection;

public class UnorderedMultiTextQuestion extends Question {

	public static final String QUESTION_TYPE = "UnorderedMultiTextQuestion";

	public UnorderedMultiTextQuestion(int id, DBConnection connection){
		super (id, connection);
	}
	
	public void createModelObject(int quizId, String backgroundText, String questionText, ArrayList<ArrayList<String>> actualAnswers) {
		this.createModelObject(quizId, backgroundText, questionText, null, null, null, actualAnswers);
	}
	
	protected void createModelObject(int quizId, String backgroundText, String questionText, String pictureURL, ArrayList<String> possibleAnswers, ArrayList<Object> additionalParameters, ArrayList<ArrayList<String>> actualAnswers) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList());

		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}

		super.createModelObject(quizId, QUESTION_TYPE, backgroundText, questionText, pictureURL, possibleAnswers, parameters);
		
		for (int i = 0; i < actualAnswers.size(); i++){
			UnorderedMultiTextAnswer ans = new UnorderedMultiTextAnswer(0,this.getConnection());
			ans.createModelObject(this.getID(), i, new ArrayList<String>(actualAnswers.get(i)));
		}
	}

	public int getMaxScore(){
		return this.getAnswerIDs().size();
	}
	
	public int getNumAnswers(){
		return this.getMaxScore();
	}
	
	@Override
	public int getScore(Object obj){
		if(!(obj instanceof ArrayList<?>)) {
			return 0;
		}
		ArrayList<Integer> answerIDs = this.getAnswerIDs();
		ArrayList<String> userAnswers = (ArrayList<String>) obj;
		boolean[] answerChecked = new boolean[userAnswers.size()];
		int score = 0;
		for (int i = 0; i < userAnswers.size(); i++){
			String currAnswer = userAnswers.get(i);
			currAnswer = currAnswer.toLowerCase();
			for (int j = 0; j < answerIDs.size(); j++){
				if (new UnorderedMultiTextAnswer(answerIDs.get(j), this.getConnection()).getScore(currAnswer) == 1 && !answerChecked[i]){
					answerChecked[i] = true;
					score++;
					break;
				}
			}
		}
		return score;
	}	

}
