package QuizProject.QuizPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;
import QuizProject.ModelObject;
import QuizProject.QuestionPackage.FillInTheBlankQuestion;
import QuizProject.QuestionPackage.MatchingQuestion;
import QuizProject.QuestionPackage.MultiMultipleChoiceQuestion;
import QuizProject.QuestionPackage.MultipleChoiceQuestion;
import QuizProject.QuestionPackage.OrderedMultiTextQuestion;
import QuizProject.QuestionPackage.PictureResponseQuestion;
import QuizProject.QuestionPackage.Question;
import QuizProject.QuestionPackage.ResponseQuestion;
import QuizProject.QuestionPackage.UnorderedMultiTextQuestion;
import QuizProject.UserPackage.User;

public class Quiz extends ModelObject {
	
	
	//static final constants
	
	private static final int RANKING_DISPLAY_TIME_INTERVAL = 1000 * 60 * 60 * 24; /// pretty sure should be last day
	private static final int NUM_HISTORICAL_SCORES = 10;
	private static final int NUM_RECENT_QUIZZES = 3;
	private static final String SCORE_SORT_DESCRIPTOR = "numerical_score DESC, duration ASC";
	private static final String REVERSE_SCORE_SORT_DESCRIPTOR = "numerical_score ASC, duration DESC";
	
	
	//constructors
	
	public Quiz(int id, DBConnection connection) {
		super(id, connection);
	}
	
	/*public Quiz newQuiz(DBConnection connection, String name, String description, int creatorId, boolean random, boolean multiPage, boolean immediateCorrection, String categoryName, boolean canTakePracticeMode) {
		Quiz quiz = new Quiz(0, connection);
		quiz.createModelObject(name, description, creatorId, random, multiPage, immediateCorrection, categoryName, canTakePracticeMode, null);
		return quiz;
	}*/
	
	public void createModelObject(String name, String description, int creatorId, boolean random, boolean multiPage, boolean immediateCorrection, String categoryName, boolean canTakePracticeMode, ArrayList<String> tags) {
		this.createModelObject(name, description, creatorId, random, multiPage, immediateCorrection, categoryName, canTakePracticeMode, tags, null);
	}
	
	protected void createModelObject(String name, String description, int creatorId, boolean random, boolean multiPage, boolean immediateCorrection, String categoryName, boolean canTakePracticeMode, ArrayList<String> tags, ArrayList<Object> additionalParameters) {
		long dateCreated = System.currentTimeMillis();
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(name, description, creatorId, 0, dateCreated, random, multiPage, immediateCorrection, categoryName, false, canTakePracticeMode, 0, 0));
		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}
		
		super.createModelObject(parameters);
		
		if (tags != null)
			{
			for (String tagName : tags) {
				Tag.newTag(this.getConnection(), tagName, this);
			}
		}
		
		User creator = new User(creatorId, this.getConnection());
		creator.createdQuiz(this);
	}
	
	public static int getIdForName(String name, DBConnection connection) { /// delete this method once challenges work based on quiz ids
		int id = 0;
		
		ResultSet rs = connection.executeQuery("SELECT * FROM quizzes WHERE name = \"" + name + "\";");
		try {
			if (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	}
	
	// quiz list getters
	
	public static ArrayList<Quiz> getMostPopularQuizzes(DBConnection connection) {
		return Quiz.getQuizzes(connection, null, "mean_rating DESC, total_times_taken DESC", NUM_RECENT_QUIZZES);
	}
	
	public static ArrayList<Quiz> getRecentlyCreatedQuizzes(DBConnection connection) {
		return Quiz.getQuizzes(connection, null, "date_created DESC", NUM_RECENT_QUIZZES);
	}
	
	public static ArrayList<Quiz> getAllQuizzes(DBConnection connection) {
		return Quiz.getQuizzes(connection, null, "date_created DESC", -1);
	}
	
	public static ArrayList<Quiz> getReportedQuizzes(DBConnection connection) {
		return Quiz.getQuizzes(connection, "is_inappropriate = 1", null, -1);
	}
	
	public static ArrayList<Quiz> getBestRatedQuizzes(DBConnection connection) {
		return Quiz.getQuizzes(connection, null, "mean_rating DESC", 5);
	}
	
	public static ArrayList<Quiz> getQuizzesForName(DBConnection connection, String name) {
		return Quiz.getQuizzes(connection, "name = \"" + name + "\"", null, -1);
	}
	
	public static ArrayList<Quiz> getQuizzesForCategory(DBConnection connection, String categoryName) {
		return Quiz.getQuizzes(connection, "category_name = \"" + categoryName + "\"", null, -1);
	}
	
	public static ArrayList<Quiz> getQuizzesForTag(DBConnection connection, String tagName) {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		
		ResultSet rs = connection.executeQuery("SELECT * FROM tags WHERE name = \"" + tagName + "\"");
		try {
			while (rs.next()) {
				quizzes.add(new Quiz(rs.getInt("quiz_id"), connection));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quizzes;
	}
	
	/**
	 * Pass in null for the predicate and sortDescriptor parameters if you
	 * don't want to use them. Passing in an empty string will not work.
	 * Pass in a negative limit if you don't want a limit to be imposed
	 * on search results.
	 * @param connection
	 * @param predicate
	 * @param sortDescriptor
	 * @param limit
	 * @return
	 */
	private static ArrayList<Quiz> getQuizzes(DBConnection connection, String predicate, String sortDescriptor, int limit) {
		if (predicate == null) predicate = "";
		else predicate = " WHERE " + predicate;
		
		if (sortDescriptor == null) sortDescriptor = "";
		else sortDescriptor = " ORDER BY " + sortDescriptor;
		
		String limitString = null;
		if (limit < 0) limitString = "";
		else limitString = " LIMIT " + limit;
		
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		
		ResultSet rs = connection.executeQuery("SELECT * FROM quizzes" + predicate + sortDescriptor + limitString + ";");
		try {
			while (rs.next()) {
				quizzes.add(new Quiz(rs.getInt("id"), connection));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quizzes;
	}
	
	//getters and setters for quiz properties 
	
	public String getCategoryName() {
		return this.getString("category_name");
	}
	
	public ArrayList<String> getTagNames() {
		ArrayList<String> tags = new ArrayList<String>();
		
		ResultSet rs = this.getConnection().executeQuery("SELECT * FROM tags WHERE quiz_id = " + this.getID());
		try {
			while (rs.next()) {
				tags.add(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tags;
	}

	public String getName() {
		return this.getString("name");
	}
	
	public void setName(String name) {
		this.setValue("name", name);
	}
	
	public String getDescription() {
		return this.getString("description");
	}
	
	public void setDescription(String description) {
		this.setValue("description", description);
	}
	
	public User getCreator(){
		return new User(this.getInt("creator_id"), this.getConnection());
	}
	
	public long getDateCreated() {
		return this.getLong("date_created");
	}
	
	public boolean isRandom() {
		return this.getBoolean("is_random");
	}
	
	public boolean isMultiPage() {
		return this.getBoolean("is_multi_page");
	}
	
	public boolean immediateCorrection() {
		return this.getBoolean("immediate_correction");
	}
	
	public boolean canTakePracticeMode() {
		return this.getBoolean("can_take_practice_mode");
	}
	
	public boolean isReported(){
		return this.getBoolean("is_inappropriate");
	}
	
	public void wasReported(){
		this.setValue("is_inappropriate", true);
	}
	
	// Stats
	
	public int getHighestScore() {
		return (int) this.getExtremum("numerical_score", SCORE_SORT_DESCRIPTOR);
	}
	
	public int getLowestScore() {
		return (int) this.getExtremum("numerical_score", REVERSE_SCORE_SORT_DESCRIPTOR);
	}
	
	public long getLongestTime() {
		return this.getExtremum("duration", "duration ASC");
	}
	
	public long getShortestTime() {
		return this.getExtremum("duration", "duration DESC");
	}
	
	public long getAverageTime() {
		return this.getMean("duration");
	}
	
	public int getMeanScore() {
		return (int) this.getMean("numerical_score");
	}
	
	/**
	 * This only works for columns whose value when correct should be non-negative. Score
	 * and duration fit this constraint.
	 * @param min
	 * @param valueName
	 * @return
	 */
	private long getExtremum(String valueName, String sortDescriptor) {
		if (sortDescriptor == null) sortDescriptor = "";
		else sortDescriptor = " ORDER BY " + sortDescriptor;
		
		long extremum = -1;
		
		ResultSet rs = this.getConnection().executeQuery("SELECT * FROM scores WHERE quiz_id = " + this.getID() + sortDescriptor + " LIMIT 1;");
		try {
			if (rs.next()) {
				extremum = rs.getLong(valueName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return extremum;
	}
	
	/**
	 * This only works for columns whose value when correct should be non-negative. Score
	 * and duration fit this constraint.
	 * @param valueName
	 * @return
	 */
	private long getMean(String valueName) {
		long mean = -1;
		
		ResultSet rs = this.getConnection().executeQuery("SELECT * FROM scores WHERE quiz_id = " + this.getID() + " LIMIT 1;");
		try {
			if (rs.next()) {
				String paramName = "AVG(" + valueName + ")";
				rs = this.getConnection().executeQuery("SELECT " + paramName + " FROM scores WHERE quiz_id = " + this.getID() + ";");
				if (rs.next()) {
					mean = rs.getLong(paramName);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mean;
	}
	
	public int getTotalTimesTaken() {
		return this.getInt("total_times_taken");
	}
	
	public int getTotalTimeSpentOnQuiz() {
		int sum = 0;

		String paramName = "SUM(duration)";
		ResultSet rs = this.getConnection().executeQuery("SELECT " + paramName + " FROM scores WHERE quiz_id = " + this.getID() + ";");
		try {
			if (rs.next()) {
				sum = rs.getInt(paramName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return sum;
	}
	
	// scores
	
	private ArrayList<Score> getScores(String predicate, String sortDescriptor) {
		if (predicate == null) predicate = "";
		else predicate += " AND ";
		
		if (sortDescriptor == null) sortDescriptor = "";
		else sortDescriptor = " ORDER BY " + sortDescriptor;
		
		ArrayList<Score> scores = new ArrayList<Score>();
		
		ResultSet rs = this.getConnection().executeQuery("SELECT * FROM scores WHERE " + predicate + "quiz_id = " + this.getID() + sortDescriptor + " LIMIT " + NUM_HISTORICAL_SCORES + ";");
		try {
			while (rs.next()) {
				scores.add(new Score(rs.getInt("id"), this.getConnection()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return scores;
	}
	
	public ArrayList<Score> getMostRecentScores() {
		return this.getScores(null, "date_taken DESC");
	}
	
	public ArrayList<Score> getBestAllTimeScores() {
		return this.getScores(null, SCORE_SORT_DESCRIPTOR);
	}
	
	public ArrayList<Score> getBestIntervalScores(){
		return this.getScores("date_taken > " + (System.currentTimeMillis() - RANKING_DISPLAY_TIME_INTERVAL), SCORE_SORT_DESCRIPTOR);
	}
	
	public ArrayList<Question> getQuestions() {
		ArrayList<Question> questionSet = new ArrayList<Question>();
		ResultSet rs = this.getConnection().executeQuery("SELECT *"  + " FROM questions WHERE quiz_id = " + this.getID() + ";");
		try {
			while (rs.next()) {
				String questionType = rs.getString("question_type");
				if (questionType.equals(FillInTheBlankQuestion.QUESTION_TYPE)) {
					questionSet.add(new FillInTheBlankQuestion(rs.getInt("id"),this.getConnection()));
				}else if (questionType.equals(MatchingQuestion.QUESTION_TYPE)) {
					questionSet.add(new MatchingQuestion(rs.getInt("id"),this.getConnection()));
				} else if (questionType.equals(MultiMultipleChoiceQuestion.QUESTION_TYPE)) {
					questionSet.add(new MultiMultipleChoiceQuestion(rs.getInt("id"),this.getConnection()));
				} else if (questionType.equals(MultipleChoiceQuestion.QUESTION_TYPE)) {
					questionSet.add(new MultipleChoiceQuestion(rs.getInt("id"),this.getConnection()));
				} else if (questionType.equals(OrderedMultiTextQuestion.QUESTION_TYPE)) {
					questionSet.add(new OrderedMultiTextQuestion(rs.getInt("id"),this.getConnection()));
				} else if (questionType.equals(PictureResponseQuestion.QUESTION_TYPE)) {
					questionSet.add(new PictureResponseQuestion(rs.getInt("id"),this.getConnection()));
				} else if (questionType.equals(ResponseQuestion.QUESTION_TYPE)) {
					questionSet.add(new ResponseQuestion(rs.getInt("id"),this.getConnection()));
				} else if (questionType.equals(UnorderedMultiTextQuestion.QUESTION_TYPE)) {
					questionSet.add(new UnorderedMultiTextQuestion(rs.getInt("id"),this.getConnection()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questionSet;
	}
	
	public int getMaxScore(){
		int totalScore = 0;
		for (Question currQuestion : this.getQuestions()) {
			totalScore += currQuestion.getMaxScore();
		}
		return totalScore;
	}
		
	// methods for adding different question types
	
	public void addFillInTheBlankQuestion(String backgroundText, String questionText, ArrayList<String> actualAnswers){
		FillInTheBlankQuestion newQuestion = new FillInTheBlankQuestion(0, this.getConnection());
		newQuestion.createModelObject(this.getID(), backgroundText, questionText, actualAnswers);
	}
	
	public void addMatchingQuestion(String backgroundText, String questionText, ArrayList<String> possibleAnswers, ArrayList<String> actualAnswers){
		MatchingQuestion newQuestion = new MatchingQuestion(0, this.getConnection());
		newQuestion.createModelObject(this.getID(), backgroundText, questionText, possibleAnswers, actualAnswers);
	}
	
	public void addMultiMultipleChoiceQuestion(String backgroundText, String questionText, ArrayList<String> possibleAnswers, ArrayList<Integer> actualAnswers){
		MultiMultipleChoiceQuestion newQuestion = new MultiMultipleChoiceQuestion(0, this.getConnection());
		newQuestion.createModelObject(this.getID(), backgroundText, questionText, possibleAnswers, actualAnswers);
	}
	
	public void addMultipleChoiceQuestion(String backgroundText, String questionText, ArrayList<String> possibleAnswers, int actualAnswer){
		MultipleChoiceQuestion newQuestion = new MultipleChoiceQuestion(0,this.getConnection());
		newQuestion.createModelObject(this.getID(), backgroundText, questionText, possibleAnswers, actualAnswer);
	}
	
	public void addOrderedMultiTextQuestion(String backgroundText, String questionText, ArrayList<ArrayList<String>> actualAnswers){
		OrderedMultiTextQuestion newQuestion = new OrderedMultiTextQuestion(0, this.getConnection());
		newQuestion.createModelObject(this.getID(), backgroundText, questionText, actualAnswers);
	}
	
	public void addPictureResponseQuestion(String backgroundText, String questionText, String pictureURL, ArrayList<String> actualAnswers){
		PictureResponseQuestion newQuestion = new PictureResponseQuestion(0, this.getConnection());
		newQuestion.createModelObject(this.getID(), backgroundText, questionText, pictureURL, actualAnswers);
	}
	
	public void addResponseQuestion(String backgroundText, String questionText, ArrayList<String> actualAnswers){
		ResponseQuestion newQuestion = new ResponseQuestion(0,this.getConnection());
		newQuestion.createModelObject(this.getID(), backgroundText, questionText, actualAnswers);
	}
	
	public void addUnorderedMultiTextQuestion(String backgroundText, String questionText, ArrayList<ArrayList<String>> actualAnswers){
		UnorderedMultiTextQuestion newQuestion = new UnorderedMultiTextQuestion(0, this.getConnection());
		newQuestion.createModelObject(this.getID(), backgroundText, questionText, actualAnswers);
	}
	
	public void deleteQuestion(Question question) {
		question.delete();
	}
	
	/**
	 * Should get called whenever this quiz is taken.
	 * @param session
	 */
	public void quizTaken(QuizSession session) {
		if (!(session instanceof PracticeQuizSession)) {
			Score score = new Score(0, this.getConnection());
			score.createModelObject(session.getScore(), session.getRootQuiz().getID(), session.getTaker().getID(), session.getDateTaken(), session.getDuration());
			this.setValue("total_times_taken", this.getInt("total_times_taken") + 1);
		}
	}
		
	public void clearHistory() {
		this.getConnection().executeUpdate("DELETE FROM scores WHERE quiz_id = " + this.getID());
		this.setValue("total_times_taken", 0);
	}
	
	//ratings and reviews
	
	public void wasReviewed(int rating, String text){
		Review newReview = new Review(0, this.getConnection());
		newReview.createModelObject(this.getID(), rating, text);		
		if (rating > 0) {
			int oldMeanRating = this.getInt("mean_rating");
			int oldTimesRated = this.getInt("times_rated");
			int newTimesRated = oldTimesRated + 1;
			int newMeanRating = rating;
			if (newTimesRated > 0) {
				newMeanRating = ((oldMeanRating * oldTimesRated) + rating) / newTimesRated;
			}
			this.setValue("mean_rating", newMeanRating);
			this.setValue("times_rated", newTimesRated);
		}
	}
	
	public int getNumTimesRated(){
		return this.getInt("times_rated");
	}
	
	public int getMeanRating(){
		return this.getInt("mean_rating");
	}
	
	public int getNumTimesReviewed() {
		ResultSet rs = this.getConnection().executeQuery("SELECT COUNT(*) FROM reviews WHERE text != \"\" AND quiz_id = " + this.getID()+ ";");
		try {
			if (rs.next()) {
				return rs.getInt("COUNT(*)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ArrayList<Review> getRecentReviews() {
		ArrayList<Review> reviews = new ArrayList<Review>();
		
		ResultSet rs = this.getConnection().executeQuery("SELECT * FROM reviews WHERE text != \"\" AND quiz_id = " + this.getID() + " ORDER BY date DESC LIMIT 5;");

		try {
			while (rs.next()) {
				reviews.add(new Review(rs.getInt("id"), this.getConnection()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return reviews;
	}
	
	@Override
	public String getTableName() {
		return "quizzes";
	}
	
}
