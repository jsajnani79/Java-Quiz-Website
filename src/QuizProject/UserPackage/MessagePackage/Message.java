package QuizProject.UserPackage.MessagePackage;

import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;
import QuizProject.ModelObject;
import QuizProject.UserPackage.User;
import Servlets.HTMLHelper;
import Servlets.Utilities;

public class Message extends ModelObject implements Comparable<Message> {
	
	public Message(int id, DBConnection connection) {
		super(id, connection);
	}
	
	protected void createModelObject(int senderUserId, int recipientUserId, ArrayList<Object> additionalParameters) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(senderUserId, recipientUserId, false, System.currentTimeMillis()));
		parameters.addAll(additionalParameters);
		super.createModelObject(parameters);
	}
	
	public User getSender() {
		return new User(this.getInt("sender_id"), this.getConnection());
	}
	
	public User getRecipient() {
		return new User(this.getInt("recipient_id"), this.getConnection());
	}
	
	public long getDateSent() {
		return this.getLong("date_sent");
	}
	
	public void wasRead(){
		this.setValue("has_been_read", true);
	}
	
	public boolean hasBeenRead(){
		return this.getBoolean("has_been_read");
	}
	
	public String getTitle() {
		return this.getTitleComponent() + " from " + HTMLHelper.linkforUser(this.getSender()) + " sent on " + Utilities.convertLongToDate(this.getDateSent());
	}
	
	protected String getTitleComponent() {
		return "Message";
	}

	@Override
	public int compareTo(Message other) {
		if (this.getDateSent() > other.getDateSent()) return -1;
		if (this.getDateSent() < other.getDateSent()) return 1;
		return 0;
	}
	
	public String getTypeDescription() {
		return null;
	}
	
}
