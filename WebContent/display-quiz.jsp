	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="QuizProject.UserPackage.MessagePackage.*" %>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="Servlets.*" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>
    <%@ page import="QuizProject.QuestionPackage.*" %>
    <%@ page import="java.util.Set" %>
    <%@ page import="QuizProject.UserPackage.AchievementPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- Initialize User -->
<%	 	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY); 
		int id = Integer.parseInt(request.getParameter("id"));
		Quiz quiz = new Quiz(id, user.getConnection());
		long startTime = System.currentTimeMillis();	%>
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
<div class="well"><%=quiz.getDescription() %></div>
<br>

<form class="form-horizontal" role="form" action = "GradeQuizServlet" method = "post">
<input name="id" type="hidden" value="<%=id%>"/>
<input name="startTime" type="hidden" value="<%=startTime%>"/> 
<!-- PRINT ALL CREATED QUESTIONS -->
<% String type = request.getParameter("type");
ArrayList<Question> questions = new ArrayList<Question>();
QuizSession quizSession = null;
if(request.getParameter("startedNew") != null && request.getParameter("startedNew").equals("true")){
	if(type.equals("practice")){
		quizSession = new PracticeQuizSession(quiz, user);
	}else{
		quizSession = new QuizSession(quiz, user);
	}
	session.setAttribute(QuizSession.QUIZ_SESSION_ATTRIBUTE_NAME, quizSession);
	quizSession.startQuiz();
}else{
	quizSession = (QuizSession) session.getAttribute(QuizSession.QUIZ_SESSION_ATTRIBUTE_NAME);
}

questions = quizSession.getQuestions();

int answerIndex=0;
int questionIndex=0;
int numAnswers;
for(Question question: questions){
	numAnswers=question.getNumAnswers();
	questionIndex++;
	if(question instanceof ResponseQuestion){ %>
		<%=DisplayQuestionHelper.textforQuestionResponse((ResponseQuestion) question, questionIndex,answerIndex, numAnswers) %>
<%	}else if(question instanceof FillInTheBlankQuestion){ %>
		<%=DisplayQuestionHelper.textforFillInTheBlank((FillInTheBlankQuestion) question, questionIndex,answerIndex, numAnswers) %>
<%	}else if(question instanceof MatchingQuestion){ %>
		<%=DisplayQuestionHelper.textforMatching((MatchingQuestion) question, questionIndex,answerIndex, numAnswers) %>
<%	}else if(question instanceof MultiMultipleChoiceQuestion){%>
		<%=DisplayQuestionHelper.textforMultiMultipleChoice((MultiMultipleChoiceQuestion) question, questionIndex,answerIndex, numAnswers) %>
<%	}else if(question instanceof MultipleChoiceQuestion){ %>
		<%=DisplayQuestionHelper.textforMultipleChoice((MultipleChoiceQuestion) question, questionIndex,answerIndex, numAnswers) %>
<%	}else if(question instanceof OrderedMultiTextQuestion){ %>
		<%=DisplayQuestionHelper.textforOrderedMultiText((OrderedMultiTextQuestion) question, questionIndex,answerIndex,numAnswers) %>
<%	}else if(question instanceof PictureResponseQuestion){ %>
		<%=DisplayQuestionHelper.textforPictureResponse((PictureResponseQuestion) question, questionIndex,answerIndex,numAnswers) %>
<%	}else if(question instanceof UnorderedMultiTextQuestion){ %>
		<%=DisplayQuestionHelper.textforUnorderedMultiText((UnorderedMultiTextQuestion) question, questionIndex,answerIndex,numAnswers) %>
<%	}
	answerIndex+=numAnswers;
}
%>
<button type="submit" class="btn btn-info pull-right" value = "Submit">Submit</button>
</form>
<form action="browse-quizzes.jsp">
	<button type="submit" class="btn btn-warning pull-right" value = "Submit">Cancel</button>
</form>
</div> <!-- end of main div -->
</body>
</html>