<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="Servlets.*" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>
    <%@ page import="java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Your Results</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" href="QuizletStylesheet.css"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.js"></script>
<style>
h1{
text-align: center;
}
</style>
</head>
<body>
<!-- Initialize User -->
<%	 	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY); 
		QuizSession quizSession = (QuizSession) session.getAttribute(QuizSession.QUIZ_SESSION_ATTRIBUTE_NAME);
%>
<div id = "main">
<!-- Print Complete Navigation Bar --> 
<%String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>
<%	int id = Integer.parseInt(request.getParameter("id"));
if(quizSession instanceof PracticeQuizSession){%>
	<div class="jumbotron">
	  <h1>Practice Mode Complete</h1>
	  <br>
	  <p align="center">You have answered each question successfully three times</p>
	  <p align="center"><a class="btn btn-primary btn-lg" role="button" href="<%= HTMLHelper.hrefForQuizSummary(new Quiz(id,user.getConnection()))%>">Return to Quiz Summary</a>
	</div>
<%}else{
	int score = Integer.parseInt(request.getParameter("score"));
	String time = Utilities.convertLongToTime(quizSession.getDuration());
	int maxScore = Integer.parseInt(request.getParameter("maxScore"));
%>

<div class="jumbotron">
  <h1>Quiz Results</h1>
  <br>
  <p>You have completed this quiz</p>
  <p>Score: <%=quizSession.getScore()%> out of <%=quizSession.getRootQuiz().getMaxScore()%></p>
  <p>Time Taken (hr:min:sec): <%=time%></p>
  <p><a class="btn btn-primary btn-lg" role="button" href="<%= HTMLHelper.hrefForQuizSummary(new Quiz(id,user.getConnection()))%>">Retake Quiz</a>
  
  <!-- <a class="btn btn-primary btn-lg pull-right" role="button">View Detailed Results</a> --></p>
</div>

<form action="RateQuizServlet" method="post">
<input name="id" type="hidden" value="<%=id%>"/>
	<div class="panel panel-default">
	  <div class="panel-heading"> 
	  	<h3 class="panel-title">Rate this quiz</h3>
	  </div>
		<div class="panel-body">
		<div id="questionTable">
			<br>
			<p>Write your review of the quiz here:</p>
		  <textarea class="form-control" maxlength="1000" rows="3" name="ratingText" placeholder="Please enter any comments on the quiz here"></textarea>
		  <p>Select the appropriate rating. 1 is the lowest value. 5 is the highest.</p>
		<div class="row">
		    <div class="col-lg-6">
		    	
		    	<div class="input-group">
		    	<input type="radio" name="rating" value="1" /> 1
		    	</div>
		    	<div class="input-group">
		    	<input type="radio" name="rating" value="2" /> 2
		    	</div>
		    	<div class="input-group">
		    	<input type="radio" name="rating" value="3" /> 3
		    	</div>
		    	<div class="input-group">
		    	<input type="radio" name="rating" value="4" /> 4
		    	</div>
		    	<div class="input-group">
		    	<input type="radio" name="rating" value="5" /> 5
		    	</div>
		    	
		    </div> <!-- col 6 -->
		</div> <!-- row -->
		</div> <!-- questionTable -->
		<button type="submit" class="btn btn-info pull-right" value = "Submit">Submit Rating</button>
		</div>
	</div> <!-- panel -->
</form>

<!-- List of the users past performance on this specific quiz.   -->
<h3>Past Performance</h3>
<div class="panel panel-default">
  <div class="panel-heading">Your Past Performance</div>
  <div class="panel-body">
  <%ArrayList<Score> userPerformance = user.getScoresForQuiz(user.getConnection(), id); %>
  <p><h4>You have taken this quiz a total of <%=userPerformance.size()%> times.</h4></p>
  </div>
  <!-- Table -->
  <table class="table">
     <thead>
	<%	int userPerformerIndex = 1;
	if(userPerformance.size()==0){%>
		<tr><h6 align="center"> There is no performance data to display </h6></tr>
		</thead><tbody>
	<%}else{%>
	
        <tr>
        <th>#</th>
        <th>Score</th>
        <th>Total Time</th>
        <th>Day Taken</th>
      </tr>
    </thead>
    <tbody>
	
	<%for(Score pastScore: userPerformance){%>
	        <tr>
	        <td><%=userPerformerIndex%></td>
	        <td><%=pastScore.getNumericalScore()%></td>
	        <td><%=Utilities.convertLongToTime(pastScore.getDuration())%></td>
	        <td><%=Utilities.convertLongToDate(pastScore.getDateTaken())%></td>
	      </tr>
	<%  userPerformerIndex++;}}	%>	

        </tbody>
  </table>
</div>

<!-- List of the friends performance  -->
<div class="panel panel-default">
  <div class="panel-heading">Friend's Performance</div>

  <!-- Table -->
  <table class="table">
     <thead>
 
 	<% Quiz fQuiz = new Quiz(id, user.getConnection());
 		ArrayList<Score> friendsPerformance = user.friendsRecentScores(fQuiz);
		int friendsPerformanceIndex = 1;
		if(friendsPerformance.size()==0){%>
		<tr><h6 align="center"> There is no performance data to display </h6></tr>
		</thead><tbody>
	<%}else{%>
	
         <tr>
            <th>#</th>
            <th>User</th>
            <th>Score</th>
            <th>Total Time</th>
            <th>Day Taken</th>
          </tr>
        </thead>
        <tbody>
    
	<%	for(Score pastScore: friendsPerformance){%>
	        <tr>
	        <td><%=friendsPerformanceIndex%></td>
	        <td><%=HTMLHelper.linkforUser(new User(pastScore.getTakerId(), user.getConnection()))%></td>
	        <td><%=pastScore.getNumericalScore()%></td>
	        <td><%=Utilities.convertLongToTime(pastScore.getDuration())%></td>
	        <td><%=Utilities.convertLongToDate(pastScore.getDateTaken())%></td>
	      </tr>
	<%  friendsPerformanceIndex++;}}	%>	
        </tbody>
  </table>
</div>


<!-- List of the highest performers of all time.  -->
<div class="panel panel-default">
  <div class="panel-heading">All Time Highest Performers</div>

  <!-- Table -->
  <table class="table">
     <thead>
 
 	<% Quiz hpQuiz = new Quiz(id, user.getConnection());
 		ArrayList<Score> highestPerformers = hpQuiz.getBestAllTimeScores(); 
		int highestPerformerIndex = 1;
		if(highestPerformers.size()==0){%>
		<tr><h6 align="center"> There is no performance data to display </h6></tr>
		</thead><tbody>
	<%}else{%>
	
         <tr>
            <th>#</th>
            <th>User</th>
            <th>Score</th>
            <th>Total Time</th>
            <th>Day Taken</th>
          </tr>
        </thead>
        <tbody>
    
	<%	for(Score pastScore: highestPerformers){%>
	        <tr>
	        <td><%=highestPerformerIndex%></td>
	        <td><%=HTMLHelper.linkforUser(new User(pastScore.getTakerId(), user.getConnection()))%></td>
	        <td><%=pastScore.getNumericalScore()%></td>
	        <td><%=Utilities.convertLongToTime(pastScore.getDuration())%></td>
	        <td><%=Utilities.convertLongToDate(pastScore.getDateTaken())%></td>
	      </tr>
	<%  highestPerformerIndex++;}}	%>	
        </tbody>
  </table>
</div>
<%}%>

</div> <!-- end of main div -->

</body>
</html>