package QuizProject.UserPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import QuizProject.DBConnection;
import QuizProject.ModelObject;
import QuizProject.QuizPackage.*;
import QuizProject.UserPackage.AchievementPackage.Achievement;
import QuizProject.UserPackage.AchievementPackage.AmateurAuthorAchievement;
import QuizProject.UserPackage.AchievementPackage.IAmTheGreatestAchievement;
import QuizProject.UserPackage.AchievementPackage.PracticeMakesPerfectAchievement;
import QuizProject.UserPackage.AchievementPackage.ProdigiousAuthorAchievement;
import QuizProject.UserPackage.AchievementPackage.ProlificAuthorAchievement;
import QuizProject.UserPackage.AchievementPackage.QuizMachineAchievement;
import QuizProject.UserPackage.MessagePackage.ChallengeMessage;
import QuizProject.UserPackage.MessagePackage.FriendRequestMessage;
import QuizProject.UserPackage.MessagePackage.Message;
import QuizProject.UserPackage.MessagePackage.NoteMessage;

public class User extends ModelObject {

	private static final int NUM_RECENT_QUIZZES = 3;
	
	//constructors
	
	public User(String username, DBConnection connection) {
		super(User.getIdForUsername(username, connection), connection);
	}
	
	public User(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(String username, String password) {
		this.createModelObject(username, password, null);
	}
	
	protected void createModelObject(String username, String password, ArrayList<Object> additionalParameters) {
		if (!User.accountExists(username, this.getConnection())) {
			String salt = Crypto.generateSalt();
			String hashedPassword = Crypto.generateHash(password, salt);
			
			ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(username, hashedPassword, salt, 0, 0, 0, 0, 0, 0, 0, 0));
			
			if (additionalParameters != null) {
				parameters.addAll(additionalParameters);
			}
			
			super.createModelObject(parameters);
		}
	}
	
	public static ArrayList<User> getAllUsers(DBConnection connection) {
		ArrayList<User> users = new ArrayList<User>();
		ResultSet rs = connection.executeQuery("SELECT * FROM users");
		try {
			while (rs.next()) {
				users.add(new User(rs.getInt("id"), connection));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return users;
	}
	
	// Authentication
	
	public static int getIdForUsername(String username, DBConnection connection) {
		int id = 0;
		
		ResultSet rs = connection.executeQuery("SELECT * FROM users WHERE username = \"" + username + "\";");
		try {
			if (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	public static boolean accountExists(String username, DBConnection connection) {
		ResultSet rs = User.getDBEntry(username, connection);
		try {
			if (rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean checkPassword(String username, String passwordAttempt, DBConnection connection) {
		ResultSet rs = User.getDBEntry(username, connection);
		try {
			while(rs.next()) {
				String salt = rs.getString("salt");
				String hashedPassword = rs.getString("hashed_password");
				String hashedAttempt = Crypto.generateHash(passwordAttempt, salt);
				if (hashedPassword.equals(hashedAttempt)) return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static ResultSet getDBEntry(String username, DBConnection connection) {
		return connection.executeQuery("SELECT * FROM users WHERE username = \"" + username + "\";");
	}
	
	// Properties
	
	public String getUsername() {
		return this.getString("username");
	}
	
	public void setUsername(String username) {
		this.setValue("username", username);
	}
	
	public String getHashedPassword() {
		return this.getString("username");
	}
	
	public String getSalt() {
		return this.getString("salt");
	}
	
	public void setPassword(String password) {
		String salt = Crypto.generateSalt();
		String hashedPassword = Crypto.generateHash(password, salt);
		this.setValue("hashed_password", hashedPassword);
		this.setValue("salt", salt);
	}
	
	public boolean isAdmin() {
		return this.getBoolean("is_admin");
	}
	
	public void makeAdmin() {
		this.setValue("is_admin", true);
	}
	
	public void revokeAdminStatus() {
		this.setValue("is_admin", false);
	}
	
	public boolean hasDonePracticeMode() {
		return this.getBoolean("has_done_practice_mode");
	}
	
	public void didPracticeMode() {
		this.setValue("has_done_practice_mode", true);
	}
	
	// Friends
	
	public static ArrayList<User> getUsersLikeName(DBConnection connection, String username) {
		ArrayList<User> users = new ArrayList<User>();
		
		ResultSet rs = connection.executeQuery("SELECT * FROM users WHERE username LIKE \"" + username + "\"");
		try {
			while (rs.next()) {
				users.add(new User(rs.getInt("id"), connection));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public Set<Integer> getFriendIds() {
		return new HashSet<Integer>(this.getConnection().getIds("friends", "friend_one_id", this.getID(), "friend_two_id"));
	}
	
	public Set<User> getFriends() {
		Set<Integer> friendIds = this.getFriendIds();
		Set<User> friends = new HashSet<User>();
		for (int friendId : friendIds) {
			friends.add(new User(friendId, this.getConnection()));
		}
		return friends;
	}
	
	public boolean hasFriendRequestPending(DBConnection connection, int userId){		
		ResultSet rs = connection.executeQuery("SELECT * FROM friend_request_messages WHERE sender_id = " + this.getID() + ";");
		try {
			while (rs.next()) {
				if (rs.getInt("recipient_id") == userId && rs.getInt("has_been_accepted") == 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean hasFriendRequestInbound(DBConnection connection, int userId) {		
		ResultSet rs = connection.executeQuery("SELECT * FROM friend_request_messages WHERE sender_id = " + userId + ";");
		try {
			while (rs.next()) {
				if (rs.getInt("recipient_id") == this.getID() && rs.getInt("has_been_accepted") == 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// Messages
	
	public Set<FriendRequestMessage> getFriendRequestMessagesReceived() {
		Set<FriendRequestMessage> messages = new HashSet<FriendRequestMessage>();
		
		ArrayList<Integer> messageIds = this.getConnection().getIds("friend_request_messages", "recipient_id", this.getID(), "id");
		for (int messageId : messageIds) {
			messages.add(new FriendRequestMessage(messageId, this.getConnection()));
		}
		
		return messages;
	}
	
	public Set<FriendRequestMessage> getFriendRequestMessagesSent() {
		Set<FriendRequestMessage> messages = new HashSet<FriendRequestMessage>();
		
		ArrayList<Integer> messageIds = this.getConnection().getIds("friend_request_messages", "sender_id", this.getID(), "id");
		for (int messageId : messageIds) {
			messages.add(new FriendRequestMessage(messageId, this.getConnection()));
		}
		
		return messages;
	}
	
	public Set<ChallengeMessage> getChallengeMessagesReceived() {
		Set<ChallengeMessage> messages = new HashSet<ChallengeMessage>();
		
		ArrayList<Integer> messageIds = this.getConnection().getIds("challenge_messages", "recipient_id", this.getID(), "id");
		for (int messageId : messageIds) {
			messages.add(new ChallengeMessage(messageId, this.getConnection()));
		}
		
		return messages;
	}
	
	public Set<ChallengeMessage> getChallengeMessagesSent() {
		Set<ChallengeMessage> messages = new HashSet<ChallengeMessage>();
		
		ArrayList<Integer> messageIds = this.getConnection().getIds("challenge_messages", "sender_id", this.getID(), "id");
		for (int messageId : messageIds) {
			messages.add(new ChallengeMessage(messageId, this.getConnection()));
		}
		
		return messages;
	}
	
	public Set<NoteMessage> getNoteMessagesReceived() {		
		Set<NoteMessage> messages = new HashSet<NoteMessage>();
		
		ArrayList<Integer> messageIds = this.getConnection().getIds("note_messages", "recipient_id", this.getID(), "id");
		for (int messageId : messageIds) {
			messages.add(new NoteMessage(messageId, this.getConnection()));
		}
		
		return messages;
	}
	
	public Set<NoteMessage> getNoteMessagesSent() {		
		Set<NoteMessage> messages = new HashSet<NoteMessage>();
		
		ArrayList<Integer> messageIds = this.getConnection().getIds("note_messages", "sender_id", this.getID(), "id");
		for (int messageId : messageIds) {
			messages.add(new NoteMessage(messageId, this.getConnection()));
		}
		
		return messages;
	}
	
	public ArrayList<Message> getMessagesReceived() {
		ArrayList<Message> messages = new ArrayList<Message>(this.getFriendRequestMessagesReceived());
		messages.addAll(this.getNoteMessagesReceived());
		messages.addAll(this.getChallengeMessagesReceived());
		
		Collections.sort(messages);
		
		return messages;
	}
	
	public int getNumUnreadMessages() {
		int unreadCount = 0;
		
		ArrayList<Message> messagesReceived = this.getMessagesReceived();
		for (Message message : messagesReceived) {
			if (!message.hasBeenRead()) unreadCount++;
		}
		
		return unreadCount;
	}
	
	 public ArrayList<Message> getMessagesSent() {
		 ArrayList<Message> messages = new ArrayList<Message>(this.getFriendRequestMessagesSent());
		 messages.addAll(this.getNoteMessagesSent());
		 messages.addAll(this.getChallengeMessagesSent());
		 
		 Collections.sort(messages);
			
		 return messages;
	 }
	 
	 // Quizzes
	
	public ArrayList<Integer> getQuizScoreIds() {
		return this.getConnection().getIds("scores", "quiz_taker_id", this.getID() , "id");
	}
	
	public ArrayList<Score> getQuizScores() {
		ArrayList<Integer> scoreIds = this.getQuizScoreIds();
		ArrayList<Score> scores = new ArrayList<Score>();
		
		for (int scoreId : scoreIds) {
			scores.add(new Score(scoreId, this.getConnection()));
		}
		
		return scores;
	}
	
	public int getNumQuizzesTaken(){
		return this.getQuizScoreIds().size();
	}
	
	public ArrayList<Score> getScoresForQuiz(DBConnection connection, int quizID){
		ArrayList<Score> scores = new ArrayList<Score>();
		ResultSet rs = connection.executeQuery("SELECT * FROM scores WHERE quiz_taker_id = " + this.getID() + " AND quiz_id = " + quizID);
		try {
			while (rs.next()) {
				scores.add(new Score(rs.getInt("id"), connection));	
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return scores;		
	}
	
	public ArrayList<Quiz> getRecentlyTakenQuizzes(DBConnection connection) {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		
		int quizCount = 0;
		ResultSet rs = connection.executeQuery("SELECT * FROM scores WHERE quiz_taker_id = " + this.getID() + " ORDER BY date_taken DESC");
		try {
			while (quizCount < NUM_RECENT_QUIZZES && rs.next()) {
				quizzes.add(new Quiz(rs.getInt("quiz_id"), connection));
				quizCount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return quizzes;
	}
	
	public ArrayList<Quiz> getAllQuizzesTaken(DBConnection connection){
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		ResultSet rs = connection.executeQuery("SELECT * FROM quizzes WHERE quiz_taker_id = " + this.getID());
		try {
			while (rs.next()) {
				quizzes.add(new Quiz(rs.getInt("id"), connection));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return quizzes;
	}
	
	public ArrayList<Integer> getQuizCreatedIds() {
		return this.getConnection().getIds("quizzes", "creator_id", this.getID(), "id");
	}
	
	public ArrayList<Quiz> getQuizzesCreated() {
		ArrayList<Integer> quizIds = this.getQuizCreatedIds();
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		
		for (int quizId : quizIds) {
			quizzes.add(new Quiz(quizId, this.getConnection()));
		}
		
		return quizzes;
	}
	
	public int getNumQuizzesCreated(){
		return this.getQuizCreatedIds().size();
	}
	
	public ArrayList<Quiz> getRecentlyCreatedQuizzes(DBConnection connection) {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		
		int quizCount = 0;
		ResultSet rs = connection.executeQuery("SELECT * FROM quizzes WHERE creator_id = " + this.getID() + " ORDER BY date_created DESC");
		try {
			while (quizCount < NUM_RECENT_QUIZZES && rs.next()) {
				quizzes.add(new Quiz(rs.getInt("id"), connection));
				quizCount++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return quizzes;
	}
	
	// Achievements
	
	public boolean hasAmateurAuthorAchievement() {
		return (this.getLong("has_amateur_achievement") != 0);
	}
	
	public void earnedAmateurAuthorAchievement() {
		this.setValue("has_amateur_achievement", System.currentTimeMillis());
	}
	
	public long dateAmateurAuthorAchievementEarned(){
		return this.getLong("has_amateur_achievement");
	}
	
	public boolean hasIAmTheGreatestAchievement() {
		return (this.getLong("has_i_am_the_greatest_achievement") != 0);	
	}
	
	public void earnedIAmTheGreatestAchievement() {
		this.setValue("has_i_am_the_greatest_achievement", System.currentTimeMillis());
	}
	
	public long dateIAmTheGreatestAchievementEarned() {
		return this.getLong("has_i_am_the_greatest_achievement");	
	}
	
	public boolean hasPracticeMakesPerfectAchievement() {
		return (this.getLong("has_practice_makes_perfect_achievement") != 0);
	}
	
	public void earnedPracticeMakesPerfectAchievement() {
		this.setValue("has_practice_makes_perfect_achievement", System.currentTimeMillis());
	}
	
	public long datePracticeMakesPerfectAchievementEarned() {
		return this.getLong("has_practice_makes_perfect_achievement");
	}
	
	public boolean hasProdigiousAuthorAchievement() {
		return (this.getLong("has_prodigious_author_achievement") != 0);
	}
	
	public void earnedProdigiousAuthorAchievement() {
		this.setValue("has_prodigious_author_achievement", System.currentTimeMillis());
	}
	
	public long dateProdigiousAuthorAchievementEarned() {
		return this.getLong("has_prodigious_author_achievement");
	}
	
	public boolean hasProlificAuthorAchievement() {
		return (this.getLong("has_prolific_author_achievement") != 0);
	}
	
	public void earnedProlificAuthorAchievement() {
		this.setValue("has_prolific_author_achievement", System.currentTimeMillis());
	}
	
	public long dateProlificAuthorAchievementEarned() {
		return this.getLong("has_prolific_author_achievement");
	}
	
	public boolean hasQuizMachineAchievement() {
		return (this.getLong("has_quiz_machine_achievement") != 0);
	}
	
	public void earnedQuizMachineAchievement() {
		this.setValue("has_quiz_machine_achievement", System.currentTimeMillis());
	}
	
	public long dateQuizMachineAchievementEarned() {
		return this.getLong("has_quiz_machine_achievement");
	}
	
	public ArrayList<Achievement> getAchievements(){
		ArrayList<Achievement> achievements = new ArrayList<Achievement>();
		if (this.hasAmateurAuthorAchievement()) achievements.add(new AmateurAuthorAchievement());
		if (this.hasIAmTheGreatestAchievement()) achievements.add(new IAmTheGreatestAchievement());
		if (this.hasPracticeMakesPerfectAchievement()) achievements.add(new PracticeMakesPerfectAchievement());
		if (this.hasProdigiousAuthorAchievement()) achievements.add(new ProdigiousAuthorAchievement());
		if (this.hasProlificAuthorAchievement()) achievements.add(new ProlificAuthorAchievement());
		if (this.hasQuizMachineAchievement()) achievements.add(new QuizMachineAchievement());
		return achievements;
	}
	
	// Announcements
	
	public void createAnnouncement(String title, String message){
		Announcement newAnnouncement = new Announcement(0, this.getConnection());
		newAnnouncement.createModelObject(title, message, this.getID(), System.currentTimeMillis());
	}
	
	// Activity
	
	public ArrayList<ActivityEvent> getRecentActivity() {
		ArrayList<ActivityEvent> activity = new ArrayList<ActivityEvent>();
		
		ArrayList<Score> quizScores = this.getQuizScores();
		for (Score score : quizScores) {
			activity.add(new ActivityEvent(score, score.getDateTaken(), this));
		}
		
		ArrayList<Quiz> quizzesCreated = this.getQuizzesCreated();
		for (Quiz quiz : quizzesCreated) {
			activity.add(new ActivityEvent(quiz, quiz.getDateCreated(), this));
		}
		
		if (this.hasAmateurAuthorAchievement()) {
			activity.add(new ActivityEvent(new AmateurAuthorAchievement(), this.dateAmateurAuthorAchievementEarned(), this));
		}
		if (this.hasIAmTheGreatestAchievement()) {
			activity.add(new ActivityEvent(new IAmTheGreatestAchievement(), this.dateIAmTheGreatestAchievementEarned(), this));
		}
		if (this.hasPracticeMakesPerfectAchievement()) {
			activity.add(new ActivityEvent(new PracticeMakesPerfectAchievement(), this.datePracticeMakesPerfectAchievementEarned(), this));
		}
		if (this.hasProdigiousAuthorAchievement()) {
			activity.add(new ActivityEvent(new ProdigiousAuthorAchievement(), this.dateProdigiousAuthorAchievementEarned(), this));
		}
		if (this.hasProlificAuthorAchievement()) {
			activity.add(new ActivityEvent(new ProlificAuthorAchievement(), this.dateProlificAuthorAchievementEarned(), this));
		}
		if (this.hasQuizMachineAchievement()) {
			activity.add(new ActivityEvent(new QuizMachineAchievement(), this.dateQuizMachineAchievementEarned(), this));
		}
		
		Collections.sort(activity);
		
		return activity;
	}
	
	public ArrayList<Score> friendsRecentScores(Quiz quiz) {
		ArrayList<Score> scores = new ArrayList<Score>();
		
		Set<User> friends = this.getFriends();
		for (User friend : friends) {
			ResultSet rs = this.getConnection().executeQuery("SELECT * FROM scores WHERE quiz_id = " + this.getID() + " AND quiz_taker_id = " + friend.getID() + ";");
			try {
				while (rs.next()) {
					scores.add(new Score(rs.getInt("id"), this.getConnection()));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		Collections.sort(scores, new ScoreRecencyComparator());
				
		return scores;
	}
	
	class ScoreRecencyComparator implements Comparator<Score> {
		@Override
		public int compare(Score first, Score second) {
			if(first.getDateTaken() < second.getDateTaken()){
				return 1;
			} else if(first.getDateTaken() > second.getDateTaken()){
				return -1;
			}
			return 0;
		}
	}
	
	
	public ArrayList<ActivityEvent> getFriendsRecentActivity() {
		Set<User> friends = this.getFriends();
		
		ArrayList<ActivityEvent> friendsRecentActivity = new ArrayList<ActivityEvent>();
		for (User friend : friends) {
			friendsRecentActivity.addAll(friend.getRecentActivity());
		}
		
		Collections.sort(friendsRecentActivity);
		
		return friendsRecentActivity;
	}
	
	// Notification Events
	
	public void tookQuiz(QuizSession session) {		
		if (session instanceof PracticeQuizSession) {
			this.didPracticeMode();
		}
		checkTakenAchievements();
	}
	
	public void createdQuiz(Quiz quiz) {
		this.checkCreationAchievements();
	}
	
	public void checkTakenAchievements(){		
		if (QuizMachineAchievement.isEligible(this)){
			 this.earnedQuizMachineAchievement();
		}
		
		if (IAmTheGreatestAchievement.isEligible(this)){
			this.earnedIAmTheGreatestAchievement();
		}
		
		if (PracticeMakesPerfectAchievement.isEligible(this)){
			this.earnedPracticeMakesPerfectAchievement();
		}
	}
	
	public void checkCreationAchievements(){		
		if (AmateurAuthorAchievement.isEligible(this)) {
			this.earnedAmateurAuthorAchievement();
		}
		
		if (ProlificAuthorAchievement.isEligible(this)) {
			this.earnedProlificAuthorAchievement();
		}
		
		if (ProdigiousAuthorAchievement.isEligible(this)) {
			this.earnedProdigiousAuthorAchievement();
		}
	}
	
	public boolean isFriendsWith(int friendId) {
		Set<Integer> friendIds = this.getFriendIds();
		if (friendIds.contains(friendId)){
			return true;
		}
		return false;
	}
	
	public void befriend(User user) {
		this.getConnection().insert("friends", new ArrayList<Object>(Arrays.asList(this.getID(), user.getID())));
		this.getConnection().insert("friends", new ArrayList<Object>(Arrays.asList(user.getID(), this.getID())));
	}
	
	public void unfriend(User user) {
		this.getConnection().executeUpdate("DELETE FROM friends WHERE friend_one_id = " + this.getID() + " AND friend_two_id = " + user.getID());
		this.getConnection().executeUpdate("DELETE FROM friends WHERE friend_one_id = " + user.getID() + " AND friend_two_id = " + this.getID());
	}
	
	@Override
	public void delete() {
		this.getConnection().executeUpdate("DELETE FROM friends WHERE friend_one_id = " + this.getID());
		this.getConnection().executeUpdate("DELETE FROM friends WHERE friend_two_id = " + this.getID());
		super.delete();
	}
	
	@Override
	public String getTableName() {
		return "users";
	}

}
