package QuizProject.UserPackage.AchievementPackage;

import java.util.ArrayList;

import QuizProject.UserPackage.*;
import QuizProject.QuizPackage.*;

public class IAmTheGreatestAchievement extends Achievement {
	
	public  String name() {
		return "I Am The Greatest";
	}
	
	public  String iconURL() {
		return "AchievementIcons/green.png";
	}
	
	public String toolTip() {
		return "Received the highest score on a quiz";
	}
	
	public static boolean isEligible(User user) {
		if (user.hasIAmTheGreatestAchievement()) return false;
		
		ArrayList<Score> scores = user.getQuizScores();
		for (Score score : scores) {
			Quiz rootQuiz = new Quiz(score.getQuizId(), user.getConnection());
			ArrayList<Score> bestScores = rootQuiz.getBestAllTimeScores();
			if (bestScores.size() > 0) {
				Score bestScore = bestScores.get(0);
				if (bestScore.getTakerId() == user.getID()) return true;
			}
		}
		return false;
	}
	
}
