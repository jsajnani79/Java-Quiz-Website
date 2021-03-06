package QuizProject.UserPackage.MessagePackage;

import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;
import QuizProject.QuizPackage.Quiz;

public class ChallengeMessage extends Message {
			
	public ChallengeMessage(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int senderUserId, int recipientUserId, int quizId) {
		this.createModelObject(senderUserId, recipientUserId, quizId, null);
	}
	
	protected void createModelObject(int senderUserId, int recipientUserId, int quizId, ArrayList<Object> additionalParameters) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(quizId, false));
		
		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}
		
		super.createModelObject(senderUserId, recipientUserId, parameters);
	}
	
	public boolean hasBeenAccepted(){
		return this.getBoolean("has_been_accepted");
	}
	
	public void wasAccepted(){
		this.setValue("has_been_accepted", true);
	}
	
	public String getTableName(){
		return "challenge_messages";
	}
	
	public Quiz getQuiz() {
		return new Quiz(this.getInt("challenged_quiz_id"), this.getConnection());
	}
	
	@Override
	public String getTitleComponent() {
		String title = "";
		
		if (this.hasBeenRead()) {
			title =  "Challenge";
		} else {
			title =  "New Challenge";
		}
		
		return title;
	}
	
	@Override
	public String getTypeDescription() {
		return "Quiz Challenge";
	}

}
