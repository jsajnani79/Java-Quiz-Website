package QuizProject.UserPackage.MessagePackage;

import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;
import QuizProject.UserPackage.User;

public class FriendRequestMessage extends Message {

	public FriendRequestMessage(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int senderUserId, int recipientUserId) {
		this.createModelObject(senderUserId, recipientUserId, null);
	}
	
	protected void createModelObject(int senderUserId, int recipientUserId, ArrayList<Object> additionalParameters) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(false));
		
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
		
		User sender = new User(this.getInt("sender_id"), this.getConnection());
		User recipient = new User(this.getInt("recipient_id"), this.getConnection());
		sender.befriend(recipient);
	}
	
	public String getTableName(){
		return "friend_request_messages";
	}
	
	@Override
	public String getTitleComponent() {
		String title = "";
		
		if (this.hasBeenRead()) {
			title =  "Friend request";
		} else {
			title =  "New friend request";
		}
		
		return title;
	}
	
	@Override
	public String getTypeDescription() {
		return "Friend Request";
	}

}
