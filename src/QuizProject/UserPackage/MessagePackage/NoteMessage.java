package QuizProject.UserPackage.MessagePackage;

import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;

public class NoteMessage extends Message {
	
	public NoteMessage(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int senderUserId, int recipientUserId, String message) {
		this.createModelObject(senderUserId, recipientUserId, message, null);
	}

	protected void createModelObject(int senderUserId, int recipientUserId, String message, ArrayList<Object> additionalParameters) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(message));
		
		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}
		
		super.createModelObject(senderUserId, recipientUserId, parameters);
	}
	
	public String getMessage() {
		return this.getString("message");
	}
	
	public String getTableName() {
		return "note_messages";
	}
	
	@Override
	public String getTitleComponent() {
		String title = "";
		
		if (this.hasBeenRead()) {
			title =  "Note";
		} else {
			title =  "Unread note";
		}
		
		return title;
	}
	
	@Override
	public String getTypeDescription() {
		return "Message";
	}

}
