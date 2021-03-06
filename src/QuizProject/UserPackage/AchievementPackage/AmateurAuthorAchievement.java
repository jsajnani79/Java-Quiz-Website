package QuizProject.UserPackage.AchievementPackage;

import QuizProject.UserPackage.*;

public class AmateurAuthorAchievement extends Achievement {
	
	public static final int THRESHOLD = 1;
	
	public String name(){
		return "Amateur Author";
	}
	
	public  String iconURL() {
		return "AchievementIcons/blue.png";
	}
	
	public String toolTip() {
		return "Created a quiz";
	}
	
	public static boolean isEligible(User user) {
		if (user.hasAmateurAuthorAchievement()) return false;
		if (user.getNumQuizzesCreated() >= THRESHOLD) return true;
		return false;
	}
}
