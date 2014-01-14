//package QuizProject.UserPackage;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Set;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import QuizProject.QuestionPackage.FillInTheBlankQuestion;
//import QuizProject.QuestionPackage.MatchingQuestion;
//import QuizProject.QuestionPackage.MultiMultipleChoiceQuestion;
//import QuizProject.QuestionPackage.MultipleChoiceQuestion;
//import QuizProject.QuestionPackage.OrderedMultiTextQuestion;
//import QuizProject.QuestionPackage.Question;
//import QuizProject.QuestionPackage.UnorderedMultiTextQuestion;
//import QuizProject.QuizPackage.Quiz;
//import QuizProject.QuizPackage.QuizSession;
//import QuizProject.UserPackage.AchievementPackage.AmateurAuthorAchievement;
//
//
//
//public class UserTest {
//
//	User saam;
//	User jai;
//	Quiz germanCars;
//	Question question1;
//	Question question2;
//	Question question3;
//	Question question4;//matching
//	Question question5;//ordered multianswer
//	Question question6;//unordered multianswer
//	ArrayList<Question> questionSet; 
//	AmateurAuthorAchievement amateurauthor = new AmateurAuthorAchievement();
//	
//	
//	@Before
//	public void setUp() throws Exception{
//		//Creating Multiple Choice Question
//		ArrayList<String> question1Choices = new ArrayList<String>();
//		question1Choices.add("Berlin");
//		question1Choices.add("Munich");
//		question1Choices.add("Paris");
//		question1 = new MultipleChoiceQuestion("What city is the capital of Germany?",
//				question1Choices, 1);
//		
//		//Creating Fill In The Blank Question 
//		Set<String> question2Choices = new HashSet<String>();
//		question2Choices.add("r8");
//		question2Choices.add("Audi r8");
//		question2Choices.add("Audi R8");
//		question2 = new FillInTheBlankQuestion("What is the best german car?", question2Choices);
//	
//		//Creating MultiMultiple Choice Questions
//		Set<Integer> answerIndicies = new HashSet<Integer>();
//		answerIndicies.add(0);
//		answerIndicies.add(1);
//		question3 = new MultiMultipleChoiceQuestion("Which cities are in Germany?", question1Choices,
//				answerIndicies);
//		
//		//Creating Matching Questions
//		ArrayList<String> states = new ArrayList<String>();
//		states.add("Texas");
//		states.add("California");
//		states.add("Delaware");
//		ArrayList<String> capitals = new ArrayList<String>();
//		capitals.add("Sacramento");
//		capitals.add("Austin");
//		capitals.add("Dover");
//		ArrayList<Integer> correctIndexArray = new ArrayList<Integer>();
//		correctIndexArray.add(2);
//		correctIndexArray.add(1);
//		correctIndexArray.add(3);
//		question4 = new MatchingQuestion("Match the states with their capitals", states, capitals, correctIndexArray);
//		
//		//Creating Ordered MultiText Questions
//		Set<String> answer1 = new HashSet<String>();
//		answer1.add("Berlin");
//		answer1.add("Bn");
//		Set<String> answer2 = new HashSet<String>();
//		answer2.add("Frankfurt");
//		answer2.add("ft");
//		Set<String> answer3 = new HashSet<String>();
//		answer3.add("Munich");
//		answer3.add("Mh");
//		ArrayList<Set<String>> answers = new ArrayList<Set<String>>();
//		answers.add(answer1);
//		answers.add(answer2);
//		answers.add(answer3);
//		question5 = new OrderedMultiTextQuestion("In alphabetical order, name the three biggest German cities:",answers);
//	
//		//Created Unordered MultiText Questions
//		question6 = new UnorderedMultiTextQuestion("In any order, name the three biggest German cities:", answers);
//		
//		//Creating user
//		//saam = new User ("saam", "texas");
//		//jai = new User ("jai", "new york");
//		
//		//Creating questionSet for Quiz
//		questionSet = new ArrayList<Question>();
//		questionSet.add(question1);
//		questionSet.add(question2);
//		questionSet.add(question3);
//		questionSet.add(question4);
//		questionSet.add(question5);
//		questionSet.add(question6);
//		
//		//Creating quiz
//		//germanCars = new Quiz("German Cars", "Quz about Germany and German Cars", saam, questionSet);
//		
//	}
//	
//	@Test
//	public void test1(){
////		assertEquals(true,saam.checkPassword("texas"));
////		assertEquals(false, jai.checkPassword("NEW YORK"));
//		assertEquals(0,saam.getNumQuizzesTaken());
//	}
//	
//	@Test
//	public void testFillInTheBlank(){
//		assertEquals(1, question2.getScore("r8"));
//		assertEquals(1, question2.getScore("audi r8"));
//		assertEquals(0, question2.getScore("bmw 650"));
//		assertEquals(1, question2.getMaxScore());
//	}
//	
//	@Test
//	public void testMultipleChoiceQuestion(){
//		assertEquals(1, question1.getScore(1));
//		assertEquals(1, question1.getMaxScore());
//	}
//	
//	@Test
//	public void testMultiMultipleChoiceQuestion(){
//		Set<Integer> answerChoices = new HashSet<Integer>();
//		answerChoices.add(0);
//		answerChoices.add(1);
//		assertEquals(1, question3.getScore(answerChoices));
//		answerChoices.remove(0);
//		assertEquals(0, question3.getScore(answerChoices));
//		assertEquals(1, question3.getMaxScore());
//	}
//	
//	@Test
//	public void testMatchingQuestion(){
//		ArrayList<Integer> correctIndexArray = new ArrayList<Integer>();
//		correctIndexArray.add(2);
//		correctIndexArray.add(1);
//		correctIndexArray.add(3);
//		assertEquals(1, question4.getScore(correctIndexArray));
//		assertEquals(1, question4.getMaxScore());
//		correctIndexArray.set(2, 1);
//		assertEquals(0, question4.getScore(correctIndexArray));
//	}
//	
//	@Test
//	public void testOrderedMultiTextQuestion(){
//		ArrayList<String> userAnswer = new ArrayList<String>();
//		userAnswer.add("BN");
//		userAnswer.add("Frankfurt");
//		userAnswer.add("munich");
//		assertEquals(3, question5.getScore(userAnswer));
//		assertEquals(3, question5.getMaxScore());
//		userAnswer.remove(2);
//		assertEquals(2, question5.getScore(userAnswer));
//		userAnswer.add("MUNICH");
//		userAnswer.remove(1);
//		assertEquals(1, question5.getScore(userAnswer));
//	}
//	
//	@Test
//	public void testUnorderedMultiTextQuestion(){
//		ArrayList<String> userAnswer = new ArrayList<String>();
//		userAnswer.add("munich");
//		userAnswer.add("BN");
//		userAnswer.add("Frankfurt");
//		assertEquals(3, question6.getScore(userAnswer));
//		assertEquals(3, question6.getMaxScore());
//		userAnswer.remove(1);
//		assertEquals(2, question6.getScore(userAnswer));
//		userAnswer.remove(1);
//		userAnswer.remove(0);
//		assertEquals(0, question6.getScore(userAnswer));
//	}
//	
//	/**
//	 * ThisW test checks basic functions of the quiz
//	 */
//	@Test
//	public void basicQuizTest(){
//		germanCars.setName("test name");
//		assertEquals("test name", germanCars.getName());
//		germanCars.setName("German Cars");
//		assertEquals("German Cars", germanCars.getName());
//		germanCars.setDescription("test description");
//		assertEquals("test description", germanCars.getDescription());
//		germanCars.setDescription("Quz about Germany and German Cars");
//		assertEquals(10, germanCars.getMaxScore());
//	}
//	
//	@Test
//	public void basicQuizSessionTest(){
//		QuizSession session = new QuizSession(germanCars, saam);
//		session.startQuiz();
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e){
//			e.printStackTrace();
//		}
//		session.questionAnswered(question2, "r8");
//		//assertEquals(0,saam.getAchievements().size());
//		session.endQuiz();
//		//assertEquals(1,saam.getAchievements().size());
//		assertEquals(1,session.getScore());
//		assertEquals(1,saam.getNumQuizzesTaken());
//		
//	}
//	
//	
//	
//}
