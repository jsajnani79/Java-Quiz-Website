package QuizProject.QuestionPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;
import QuizProject.ModelObject;

public class Question extends ModelObject {
	
	
	//constructors
	
	public Question(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int quizId, String questionType, String backgroundText, String questionText, String pictureURL, ArrayList<String> possibleAnswers) {
		this.createModelObject(quizId, questionType, backgroundText, questionText, pictureURL, possibleAnswers, null);
	}
	
	protected void createModelObject(int quizId, String questionType, String backgroundText, String questionText, String pictureURL, ArrayList<String> possibleAnswers, ArrayList<Object> additionalParameters) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(quizId, questionType, backgroundText, questionText, pictureURL, possibleAnswers));
		
		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}
		
		super.createModelObject(parameters);
	}
	
	
	//getters
	
	public int getScore(Object question){
		return 0;
	}
	
	public int getMaxScore(){
		return 0;
	}
	
	public String getBackgroundText(){
		return this.getString("background_text");
	}
	
	public String getQuestionText(){
		return this.getString("question_text");
	}
	
	public String getPictureURL(){
		return this.getString("picture_url");
	}
	
	public ArrayList<Integer> getAnswerIDs() {
		ResultSet rs = this.getConnection().executeQuery("SELECT *"  + " FROM answers WHERE question_id = " + this.getID() + ";");
		ArrayList<Integer> ids = new ArrayList<Integer>();
		try {
			while (rs.next()) {
				ids.add(rs.getInt("id")); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	public int getNumAnswers(){
		return 0;
	}
	
	@Override
	public String getTableName() {
		return "questions";
	}
	
}
