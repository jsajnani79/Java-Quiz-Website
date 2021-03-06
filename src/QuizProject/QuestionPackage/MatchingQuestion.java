
package QuizProject.QuestionPackage;

import java.util.ArrayList;

import QuizProject.DBConnection;


public class MatchingQuestion extends Question {
	
	public static final String QUESTION_TYPE = "MatchingQuestion";
	
	public MatchingQuestion(int id, DBConnection connection){
		super (id, connection);
	}
	
	public void createModelObject(int quizId,String backgroundText, String questionText, ArrayList<String> possibleAnswers, ArrayList<String> actualAnswers) {
		this.createModelObject(quizId, backgroundText, questionText, null, possibleAnswers, null, actualAnswers);
	}
	
	protected void createModelObject(int quizId, String backgroundText, String questionText, String pictureURL, ArrayList<String> possibleAnswers, ArrayList<Object> additionalParameters, ArrayList<String> actualAnswers) {
		ArrayList<Object> parameters = new ArrayList<Object>();

		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}

		super.createModelObject(quizId, QUESTION_TYPE, backgroundText, questionText, pictureURL, possibleAnswers, parameters);
		
		for (int i = 0; i < actualAnswers.size(); i++){
			MatchingAnswer ans = new MatchingAnswer(0,this.getConnection());
			ArrayList<String> actualAnswersArray = new ArrayList<String>();
			actualAnswersArray.add(actualAnswers.get(i));
			ans.createModelObject(this.getID(), i, actualAnswersArray);
		}
	}
	
	public int getMaxScore(){
		return 4;
	}
	
	public ArrayList<String> getQuestionsArrayList(){
		return this.getCSV("possible_answers");
	}
	
	public ArrayList<String> getAnswersArrayList(){
		ArrayList<String> answersArrayList = new ArrayList<String>();
		ArrayList<Integer> answerIDs = this.getAnswerIDs();
		for (int i = 0; i < answerIDs.size(); i++){
			answersArrayList.add(new MatchingAnswer(answerIDs.get(i), this.getConnection()).getAnswerContent());
		}
		return answersArrayList;
	}
	
	public int getNumAnswers(){
		return this.getMaxScore();
	}
	
	@Override
	public int getScore(Object obj) {
		if(!(obj instanceof ArrayList<?>)){ 
			return 0;
		}
		ArrayList<Integer> answerIDs = this.getAnswerIDs();
		ArrayList<String> userAnswers = (ArrayList<String>) obj;
		int score = 0;
		for (int i = 0; i < userAnswers.size(); i++){
			String currAnswer = userAnswers.get(i);
			score += new MatchingAnswer(answerIDs.get(i), this.getConnection()).getScore(currAnswer);
		}
		return score;
	}
	
}
