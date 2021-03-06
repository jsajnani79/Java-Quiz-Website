	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="QuizProject.UserPackage.MessagePackage.*" %>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="Servlets.*" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>
    <%@ page import="QuizProject.QuestionPackage.*" %>
    <%@ page import="java.util.*" %>
    <%@ page import="QuizProject.UserPackage.AchievementPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- Initialize User -->
<%	 	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY); 
		int id = Integer.parseInt(request.getParameter("id"));
		Quiz quiz = new Quiz(id, user.getConnection());
		long startTime;
		int answerIndex;
		int currScore;
		int accumulatedScore;
		int gradingAnswerIndex;
		QuizSession quizSession = null;
		int questionIndex = Integer.parseInt(request.getParameter("questionIndex"));
		if(request.getParameter("startedNew") != null && request.getParameter("startedNew").equals("true")){
			String type = request.getParameter("type");
			if(type.equals("practice")){
				quizSession = new PracticeQuizSession(quiz, user);
			}else if(questionIndex == 0){
				quizSession = new QuizSession(quiz, user);
			}
			session.setAttribute(QuizSession.QUIZ_SESSION_ATTRIBUTE_NAME, quizSession);
			quizSession.startQuiz();
		}
		
		if(questionIndex == 0){
			startTime = System.currentTimeMillis();
			answerIndex=currScore=accumulatedScore=gradingAnswerIndex=0;
		} else{
			startTime = Long.parseLong(request.getParameter("startTime"));
			answerIndex = Integer.parseInt(request.getParameter("answerIndex"));
			currScore = Integer.parseInt(request.getParameter("currScore"));
			accumulatedScore = Integer.parseInt(request.getParameter("accumulatedScore"));
			gradingAnswerIndex = Integer.parseInt(request.getParameter("gradingAnswerIndex"));
		}
		quizSession = (QuizSession) session.getAttribute(QuizSession.QUIZ_SESSION_ATTRIBUTE_NAME);
		%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= quiz.getName()%></title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" href="QuizletStylesheet.css"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <script src="bootstrap/js/bootstrap.js"></script>
 <style></style>
</head>
<body>

<!-- Print Complete Navigation Bar --> 
<%String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>

<div id="main">
<div class="row"><!-- Quiz Title Div -->
  <!-- <div class="col-xs-10"> -->
  	<!-- PRINT QUIZ TITLE -->
  	<h3><%= quiz.getName()%></h3>
  <!-- </div> -->
</div> <!-- End Quiz Title Div -->
<%if(questionIndex == 0){ %>
<div class="well"><%=quiz.getDescription() %></div>
<%}%>
<br>

<%if(quiz.immediateCorrection()&&questionIndex!=0&&(!(quizSession instanceof PracticeQuizSession))){ %>
	<div class="panel panel-default">
	  <div class="panel-heading">Progress</div>
	  <div class="panel-body">
	  <!-- List group -->
	  <ul class="list-group">
	    <li class="list-group-item">Score on last question: <%=currScore%></li>
	    <li class="list-group-item">Total score thus far: <%=accumulatedScore%> out of <%=quizSession.getRootQuiz().getMaxScore()%></li>
	  </ul>
	</div>
	</div>
<%}%>

<form class="form-horizontal" role="form" action = "GradeMultipageQuizServlet" method = "post"> 
<!-- PRINT ALL CREATED QUESTIONS -->
<%
	ArrayList<Question> questions = quizSession.getQuestions();
	if (questionIndex < questions.size()) {
	Question question = questions.get(questionIndex);
	int numAnswers=question.getNumAnswers();
	if(question instanceof ResponseQuestion){ %>
		<%=DisplayQuestionHelper.textforQuestionResponse((ResponseQuestion) question, questionIndex+1,answerIndex, numAnswers) %>
<%	}else if(question instanceof FillInTheBlankQuestion){ %>
		<%=DisplayQuestionHelper.textforFillInTheBlank((FillInTheBlankQuestion) question, questionIndex+1,answerIndex, numAnswers) %>
<%	}else if(question instanceof MatchingQuestion){ %>
		<%=DisplayQuestionHelper.textforMatching((MatchingQuestion) question, questionIndex+1,answerIndex, numAnswers) %>
<%	}else if(question instanceof MultiMultipleChoiceQuestion){ %>
		<%=DisplayQuestionHelper.textforMultiMultipleChoice((MultiMultipleChoiceQuestion) question, questionIndex+1,answerIndex, numAnswers) %>
<%	}else if(question instanceof MultipleChoiceQuestion){ %>
		<%=DisplayQuestionHelper.textforMultipleChoice((MultipleChoiceQuestion) question, questionIndex+1,answerIndex, numAnswers) %>
<%	}else if(question instanceof OrderedMultiTextQuestion){ %>
		<%=DisplayQuestionHelper.textforOrderedMultiText((OrderedMultiTextQuestion) question, questionIndex+1,answerIndex,numAnswers) %>
<%	}else if(question instanceof PictureResponseQuestion){ %>
		<%=DisplayQuestionHelper.textforPictureResponse((PictureResponseQuestion) question, questionIndex+1,answerIndex,numAnswers) %>
<%	}else if(question instanceof UnorderedMultiTextQuestion){ %>
		<%=DisplayQuestionHelper.textforUnorderedMultiText((UnorderedMultiTextQuestion) question, questionIndex+1,answerIndex,numAnswers) %>
<%	}
	answerIndex+=numAnswers;
	}
%>
<input name="id" type="hidden" value="<%=id%>"/>
<input name="startTime" type="hidden" value="<%=startTime%>"/>
<input name="questionIndex" type="hidden" value="<%=questionIndex%>"/>
<input name="answerIndex" type="hidden" value="<%=answerIndex%>"/>
<input name="gradingAnswerIndex" type="hidden" value="<%=gradingAnswerIndex%>"/>
<input name="currScore" type="hidden" value="<%=currScore%>"/>
<input name="accumulatedScore" type="hidden" value="<%=accumulatedScore%>"/>
<button type="submit" class="btn btn-info pull-right" value = "Submit">Submit</button>
</form>
<form action="browse-quizzes.jsp">
	<button type="submit" class="btn btn-warning pull-right" value = "Submit">Cancel</button>
</form>
</div> <!-- end of main div -->
</body>
</html>