<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ page import="Servlets.*" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="QuizProject.UserPackage.MessagePackage.*" %>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>
    <%@ page import="QuizProject.QuestionPackage.*" %>
    <%@ page import="java.util.Set" %>
    <%@ page import="QuizProject.UserPackage.AchievementPackage.*" %>
    <%@ page import="java.util.Date" %>
    <%@ page import="java.text.SimpleDateFormat" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- Initialize User -->
<%	 	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY); 
		int id = Integer.parseInt(request.getParameter("id"));
		Quiz quiz = new Quiz(id, user.getConnection());
		%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= quiz.getName()%> Summary</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" href="QuizletStylesheet.css"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.js"></script>
<style></style>
</head>
<body>

<!-- Print Complete Navigation Bar --> 
<%String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>

<div id="main">
<h2><%= quiz.getName()%></h2>
<br>
<h3>Quiz Summary</h3>

<!-- Quiz Description -->
<div class="well">
	<h4>Quiz Description</h4>
	<%=quiz.getDescription() %>
	<br>
	<br>
	Category: <%=quiz.getCategoryName() %>
	<%ArrayList<String> tagNames = quiz.getTagNames(); 
	String tags = "";
	for(String tag : tagNames){
		tags += ", " + tag;
	}
	if (tags.length() > 1 && tags.charAt(0) == ',') {
		tags = tags.substring(1);
	}
	%>
	<%if(tagNames.size()>0){%>
		<br>
		Tags: <%=tags%>
	<%} %> 
	<br>
	<br>
	<!-- Quiz Creator -->
	<p>This quiz was created by <%=HTMLHelper.linkforUser(quiz.getCreator()) %> on <%=Utilities.convertLongToDate(quiz.getDateCreated())%></p>
	<%if(quiz.getNumTimesRated() >0){%>
	<p>This quiz's average rating is <%=quiz.getMeanRating()%> based on <%=quiz.getNumTimesRated()%> ratings.</p>
	<%} %>
</div>

<!-- Initiate Quiz.  -->
<table align="center">
<tr>
<form action=<%=HTMLHelper.hrefForQuiz(quiz)%>>
<input name="id" type="hidden" value="<%=id%>"/> 
<input name="questionIndex" type="hidden" value="0"/> 
<input name="type" type="hidden" value="normal"/>
<input name="startedNew" type="hidden" value="true"/>
<td><button type="submit" class="btn btn-info">Start Quiz</button></td>
</form>
<!-- Initiate Quiz in Practice Mode -->
<%if(quiz.canTakePracticeMode()){ %>
	<form action=<%=HTMLHelper.hrefForQuiz(quiz)%>>
		<input name="id" type="hidden" value="<%=id%>"/> 
		<input name="questionIndex" type="hidden" value="0"/> 
		<input name="type" type="hidden" value="practice"/>
		<input name="startedNew" type="hidden" value="true"/>
	<td><button type="submit" class="btn btn-info">Start Practice Mode</button></td>
	</form>
<%}%>
<td><button type="button" data-toggle="modal" data-target="#challengeModal" class="btn btn-warning pull-right"> Challenge A Friend</button></td>
<td>
	<%if(!quiz.isReported()){%>
		<form action="ReportQuizServlet" method="post">
		<input name="id" type="hidden" value="<%=id%>"/> 
		<button type="submit" class="btn btn-danger pull-right">Report Quiz</button>
		</form>		
	<%} else{%>
		<button type="submit" class="btn btn-default pull-right">Quiz was Reported</button>
	<%}%>
</td>
<tr>
</table>
<!-- Edit Quiz, if User is Owner -->

<!-- List of the users past performance on this specific quiz.   -->
<h3>Reviews</h3>
<div class="panel panel-default">
  <div class="panel-heading">Recent Reviews</div>
  <div class="panel-body">
    <!-- Table -->
  <table class="table">
     <thead>
	<%	int ratingIndex = 1;
 	ArrayList<Review> reviews = quiz.getRecentReviews(); 
  	if(quiz.getNumTimesRated()>0){%>
  <p><h4>This quiz has been reviewed <%=quiz.getNumTimesReviewed()%> times.</h4></p>
      <tr>
        <th>#</th>
        <th>Review</th>
        <th>Rating</th>
        <th>Date Reviewed</th>
      </tr>
    </thead>
    	<tbody>
		<%for(Review review:reviews){%>
		        <tr>
		        <td><%=ratingIndex%></td>
		        <td><%=review.getText()%></td>
		        <td><%=review.getRating()%></td>
		        <td><%=Utilities.convertLongToDate(review.getDate())%></td>
		      </tr>
		<%  ratingIndex++;}	%>	
	  <%}else{%>
		  <tr><h6 align="center"> There is no rating data to display </h6></tr>
		</thead><tbody>
 	 <%}%>
        </tbody>
  </table>
</div>
</div>

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
	
	<%for(Score score: userPerformance){%>
	        <tr>
	        <td><%=userPerformerIndex%></td>
	        <td><%=score.getNumericalScore()%></td>
	        <td><%=Utilities.convertLongToTime(score.getDuration())%></td>
	        <td><%=Utilities.convertLongToDate(score.getDateTaken())%></td>
	      </tr>
	<%  userPerformerIndex++;}}	%>	

        </tbody>
  </table>
</div>

<!-- List of the highest performers of all time.  -->
<div class="panel panel-default">
  <div class="panel-heading">All Time Highest Performers</div>

  <!-- Table -->
  <table class="table">
     <thead>
 
 	<%ArrayList<Score> highestPerformers = quiz.getBestAllTimeScores(); 
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
    
	<%	for(Score score: highestPerformers){%>
	        <tr>
	        <td><%=highestPerformerIndex%></td>
	        <td><%=HTMLHelper.linkforUser(new User(score.getTakerId(), user.getConnection()))%></td>
	        <td><%=score.getNumericalScore()%></td>
	        <td><%=Utilities.convertLongToTime(score.getDuration())%></td>
	        <td><%=Utilities.convertLongToDate(score.getDateTaken())%></td>
	      </tr>
	<%  highestPerformerIndex++;}}	%>	
        </tbody>
  </table>
</div>

<!-- List of top performers in the last day. -->
<div class="panel panel-default">
  <div class="panel-heading">Top Performers in the Last Day</div>
  <!-- Table -->
  <table class="table">
     <thead>
        
	<%ArrayList<Score> intervalPerformers = quiz.getBestIntervalScores(); 
		int intervalPerformerIndex = 1;
		if(intervalPerformers.size()==0){%>
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
    
	<%	for(Score score: intervalPerformers){%>
	        <tr>
	        <td><%=intervalPerformerIndex%></td>
	        <td><%=HTMLHelper.linkforUser(new User(score.getTakerId(), user.getConnection()))%></td>
	        <td><%=score.getNumericalScore()%></td>
	        <td><%=Utilities.convertLongToTime(score.getDuration())%></td>
	        <td><%=Utilities.convertLongToDate(score.getDateTaken())%></td>
	      </tr>
	<%  intervalPerformerIndex++;}	}%>	

        </tbody>
  </table>
</div>

<!-- List showing the performance of recent test takers -->
<div class="panel panel-default">
  <div class="panel-heading">Recent User Performance</div>
  <!-- Table -->
  <table class="table">
     <thead>
        
	<%ArrayList<Score> recentPerformers = quiz.getMostRecentScores(); 
		int recentPerformerIndex = 1;
		if(recentPerformers.size()==0){%>
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
    
	<%	for(Score score: recentPerformers){%>
	        <tr>
	        <td><%=recentPerformerIndex%></td>
	        <td><%=HTMLHelper.linkforUser(new User(score.getTakerId(), user.getConnection()))%></td>
	        <td><%=score.getNumericalScore()%></td>
	        <td><%=Utilities.convertLongToTime(score.getDuration())%></td>
	        <td><%=Utilities.convertLongToDate(score.getDateTaken())%></td>
	      </tr>
	<%  recentPerformerIndex++;}	}%>

        </tbody>
  </table>
</div>

<!-- Summary statistics on how well all users have performed on the quiz.  -->
<div class="panel panel-default">
	  <div class="panel-heading">Overall Performance Statistics</div>
	<!-- Summary List -->
	<% 
	%>
		<ul class="list-group">
			<li class="list-group-item">Highest Score: <%=quiz.getHighestScore() >= 0 ? quiz.getHighestScore() : "Quiz has not yet been taken"%> </li>
			<li class="list-group-item">Lowest Score: <%=quiz.getLowestScore() >= 0 ? quiz.getLowestScore() : "Quiz has not yet been taken"%></li>
			<li class="list-group-item">Average Score: <%=quiz.getMeanScore() >= 0 ? quiz.getMeanScore() : "Quiz has not yet been taken"%></li>
			<li class="list-group-item">Longest Time Taken: <%= quiz.getLongestTime() >= 0 ? Utilities.convertLongToTime(quiz.getLongestTime()) : "Quiz has not yet been taken"%></li>
			<li class="list-group-item">Shortest Time Taken: <%= quiz.getShortestTime() >= 0 ? Utilities.convertLongToTime(quiz.getShortestTime()) : "Quiz has not yet been taken"%></li>
			<li class="list-group-item">Average Time Taken: <%= quiz.getAverageTime() >= 0 ? Utilities.convertLongToTime(quiz.getAverageTime()) : "Quiz has not yet been taken"%></li>
			<li class="list-group-item">Total Times Taken: <%=quiz.getTotalTimesTaken()%></li>
			<li class="list-group-item">Total Time Spent on This Quiz: <%=Utilities.convertLongToTime(quiz.getTotalTimeSpentOnQuiz()) %></li>
		</ul>
	</div>
</div>

<!-- Challenge Modal -->
<div class="modal fade" id="challengeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Challenge a Friend!</h4>
      </div>
      <div class="modal-body">

<form class="form-horizontal" role="form" action = "SendChallengeMessageServlet" method="post">
  <div class="form-group">
    <label for="challengeUsername" class="col-sm-3 control-label">Friend's Username:</label>
    <div class="col-sm-8">
      <input type="text" class="form-control" name="challengeUsername" id="challengeUsername" placeholder="Username">
    </div>
  </div>
      </div> <!-- End of body div -->
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="submit" class="btn btn-warning">Challenge</button>
      </div>
      <input name="id" type="hidden" value="<%=id%>"/> 
</form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.challenge-modal -->

</body>
</html>