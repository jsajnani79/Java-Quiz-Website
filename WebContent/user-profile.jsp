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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Initialize User -->
<%	 	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY); 
		int id = Integer.parseInt(request.getParameter("id"));
		User profileUser = new User(id, user.getConnection());
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%= profileUser.getUsername() %>'s Profile</title>
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
<h2><%= profileUser.getUsername() %>'s Profile
	<!-- Add as a Friend  -->
				<%if(user.getID()==profileUser.getID()){%>
					<form action="edit-profile.jsp" method="post">
					<input name="id" type="hidden" value="<%=profileUser.getID()%>"/>
					<button type="submit" id="editProfile" class="btn btn-info pull-right">Edit Profile</button>
					</form>
				<%} else if(!user.isFriendsWith(profileUser.getID())){
						if(user.hasFriendRequestPending(user.getConnection(), profileUser.getID())){%>
							<button type="button" id="requestPending" class="btn btn-default pull-right">Request Pending</button>
						<%}else if (user.hasFriendRequestInbound(user.getConnection(), profileUser.getID())) {%>
							<button type="button" id="requestPending" class="btn btn-default pull-right">Request Inbound</button>
						<%} else {%>
							<form action="SendFriendRequestMessageServlet" method="post">
							<input name="id" type="hidden" value="<%=profileUser.getID()%>"/>
							<button type="submit" id="addFriend" class="btn btn-info pull-right">Add Friend</button>
							</form>
						<%}
				} else if(user.isFriendsWith(profileUser.getID())){%>
						<button type="button" id="addFriend" class="btn btn-info pull-right">Friends!</button>
				<%}%>
</h2>	
<br><br>
<!-- Edit Quiz, if User is Owner -->

<!-- List of the users past performance on this specific quiz.   -->
<div class="panel panel-default">
  <div class="panel-heading">Past Performance</div>
  <div class="panel-body">
  <p align="center"><b>Total Number of Quizzes Taken: <%=user.getNumQuizzesTaken() %></b></p>
  </div>
  <!-- Table -->
  <table class="table">
     <thead>
 
        <%ArrayList<Score> quizScores = user.getQuizScores();
		if(quizScores.size()==0){%>
		<tr><h6 align="center"> There is no quiz data to display </h6></tr>
		</thead><tbody>
	<%}else{%>
         <tr>
            <th>Quiz</th>
            <th>Score</th>
            <th>Total Time</th>
            <th>Day Taken</th>
          </tr>
        </thead>
        <tbody>
	<%	for(Score score:quizScores){
		%>
	        <tr>
	        <td><%=HTMLHelper.linkforQuizSummary(new Quiz(score.getQuizId(),user.getConnection()))%></td>
	        <td><%=score.getNumericalScore()%></td>
	        <td><%=Utilities.convertLongToTime(score.getDuration())%></td>
	        <td><%=Utilities.convertLongToDate(score.getDateTaken())%></td>
	      </tr>
	<% }}	%>	
          
        </tbody>
  </table>
</div>

<!-- List of the user's friends  -->
<div class="panel panel-default">
  <div class="panel-heading">Friends</div>
	<%Set<User> Friends = profileUser.getFriends();
		if(Friends.size()==0){%>
		<h6 align="center"> There is no friend data to display </h6>
	<%}else{%>
  <!-- List group -->
  <ul class="list-group">
  <%for(User friend: Friends){%>
	  <li class="list-group-item"><%=HTMLHelper.linkforUser(friend) %></li>  
  <%} }%>
  </ul>
</div>

</div> <!-- End of Main Div -->

</body>
</html>