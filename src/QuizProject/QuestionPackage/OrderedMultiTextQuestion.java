package QuizProject.QuestionPackage;

import java.util.ArrayList;

import QuizProject.DBConnection;


public class OrderedMultiTextQuestion extends Question {
	
	public static final String QUESTION_TYPE = "OrderedMultiTextQuestion";
	
	public OrderedMultiTextQuestion(int id, DBConnection connection){
		super (id, connection);
	}
	
	public void createModelObject(int quizId, String backgroundText, String questionText, ArrayList<ArrayList<String>> actualAnswers) {
		this.createModelObject(quizId, backgroundText, questionText, null, null, null, actualAnswers);
	}
	
	protected void createModelObject(int quizId, String backgroundText, String questionText, String pictureURL, ArrayList<String> possibleAnswers, ArrayList<Object> additionalParameters, ArrayList<ArrayList<String>> actualAnswers) {
		ArrayList<Object> parameters = new ArrayList<Object>();

		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}

		super.createModelObject(quizId, QUESTION_TYPE, backgroundText, questionText, pictureURL, possibleAnswers, parameters);
		
		for (int i = 0; i < actualAnswers.size(); i++){
			OrderedMultiTextAnswer ans = new OrderedMultiTextAnswer(0,this.getConnection());
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
	public int getScore(Object obj) {
		if(!(obj instanceof ArrayList<?>)){ 
			return 0;
		}
		ArrayList<Integer> answerIDs = this.getAnswerIDs();
		ArrayList<String> userAnswers = (ArrayList<String>) obj;
		int score = 0;
		for (int i = 0; i < userAnswers.size(); i++){
			String currAnswer = userAnswers.get(i);
			currAnswer = currAnswer.toLowerCase();
			score += new OrderedMultiTextAnswer(answerIDs.get(i), this.getConnection()).getScore(currAnswer);
		}
		return score;
	}
	
}
