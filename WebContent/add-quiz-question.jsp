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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" href="QuizletStylesheet.css"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.js"></script>
 <style></style>
</head>
<body>
<!-- Initialize User -->
<%	 	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY); 
		int id = Integer.parseInt(request.getParameter("id"));
		Quiz quiz = new Quiz(id, user.getConnection());		%>

<!-- Print Complete Navigation Bar --> 
<%String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>

<div id="main">
<h2>Create A New Quiz</h2>
<div class="row"><!-- Quiz Title Div -->
  <!-- <div class="col-xs-10"> -->
  	<!-- PRINT QUIZ TITLE -->
  	<h3><%= quiz.getName()%></h3>
  <!-- </div> -->
</div> <!-- End Quiz Title Div -->
<div class="well"><%=quiz.getDescription() %></div>
<br>

<!-- PRINT ALL CREATED QUESTIONS -->
<% 
ArrayList<Question> questions = quiz.getQuestions();
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
<%	}else if(question instanceof MultiMultipleChoiceQuestion){ %>
		<%=DisplayQuestionHelper.textforMultiMultipleChoice((MultiMultipleChoiceQuestion) question, questionIndex,answerIndex, numAnswers) %>
<%	}else if(question instanceof MultipleChoiceQuestion){ %>
		<%=DisplayQuestionHelper.textforMultipleChoice((MultipleChoiceQuestion) question, questionIndex,answerIndex, numAnswers) %>
<%	}else if(question instanceof OrderedMultiTextQuestion){ %>
		<%=DisplayQuestionHelper.textforOrderedMultiText((OrderedMultiTextQuestion) question, questionIndex,answerIndex,numAnswers) %>
<%	}else if(question instanceof PictureResponseQuestion){ %>
		<%=DisplayQuestionHelper.textforPictureResponse((PictureResponseQuestion) question, questionIndex,answerIndex,numAnswers) %>
<%	}else if(question instanceof UnorderedMultiTextQuestion){ %>
		<%=DisplayQuestionHelper.textforUnorderedMultiText((UnorderedMultiTextQuestion) question, questionIndex,answerIndex,numAnswers) %>
<%	} 	answerIndex+=numAnswers;
}
%>

<form class="form-horizontal" role="form" action = "CreateQuestionServlet" method = "post">
<input name="id" type="hidden" value="<%=id%>"/> 
  <div class="form-group">
    <label for="questionSelect" class="col-sm-4 control-label">Choose Question Type: </label>
     <div class="col-sm-4">
 	<select id="questionSelect" class="form-control" name="questionSelect">
  		<option>Question Response</option>
  		<option>Fill in the Blank</option>
  		<option>Multiple Choice</option>
  		<option>Multi-Answer Multiple Choice</option>
  		<option>Picture Response</option>
  		<option>Ordered Multiple Answer</option>
  		<option>Unordered Multiple Answer</option>
  		<option>Matching</option>
	</select>   
	</div>
  <button type="submit" class="btn btn-default" class="col-sm-2">Add New Question</button>
  </div>
</form>
<form action="<%=HTMLHelper.hrefForQuizSummary(quiz) %> %>" post"">
	<input name="id" type="hidden" value="<%=id%>"/>
	<button type="submit" class="btn btn-info pull-right" value = "Submit">Submit</button>
</form>
</div> <!-- end of main div -->
</body>
</html>