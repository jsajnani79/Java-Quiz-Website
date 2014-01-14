package QuizProject.UserPackage;

import QuizProject.QuizPackage.Quiz;
import QuizProject.QuizPackage.Score;
import QuizProject.UserPackage.AchievementPackage.Achievement;

public class ActivityEvent implements Comparable<ActivityEvent> {
	
	private Object underlying;
	private long date;
	private User user;
	
	public ActivityEvent(Object underlying, long date, User user) {
		this.underlying = underlying;
		this.date = date;
		this.user = user;
	}
	
	public Object getUnderlying() {
		return this.underlying;
	}
	
	public long getDate() {
		return this.date;
	}
	
	public User getUser() {
		return this.user;
	}

	@Override
	public int compareTo(ActivityEvent other) {
		if (this.date > other.date) return -1; /// right?
		if (this.date < other.date) return -1;
		return 0;
	}
	
}
