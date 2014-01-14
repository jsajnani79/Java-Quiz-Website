package QuizProject.QuestionPackage;

import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;
import QuizProject.ModelObject;

public class Answer extends ModelObject {
	
	public Answer(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int questionId, int mapIndex, ArrayList<String> answerContent) {
		this.createModelObject(questionId, mapIndex, answerContent, null);
	}
	
	protected void createModelObject(int questionId, int mapIndex, ArrayList<String> answerContent, ArrayList<Object> additionalParameters) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(questionId, mapIndex, answerContent));
		
		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}
		
		super.createModelObject(parameters);
	}
	
	@Override
	protected String getTableName(){
		return "answers";
	}
	
}

//public Answer(int id, DBConnection connection) {
//	super(id, connection);
//}
//
//public void createModelObject() {
//	this.createModelObject(null);
//}
//
//protected void createModelObject(ArrayList<Object> additionalParameters) {
//	ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList());
//	
//	if (additionalParameters != null) {
//		parameters.addAll(additionalParameters);
//	}
//	
//	super.createModelObject(parameters);
//}
