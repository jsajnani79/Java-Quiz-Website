<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="QuizProject.UserPackage.MessagePackage.*" %>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="Servlets.*" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>
    <%@ page import="java.util.Set" %>
    <%@ page import="QuizProject.UserPackage.AchievementPackage.*" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quizlet++</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" href="QuizletStylesheet.css"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.js"></script>
<style>
h2 {text-align:left;}
h5 {text-align:center;}
/* white: #FFFFFF
Gray: #7F909A or B5B5B5
Blue: #00CBE9 or #00DDD8 or #1D2F3E*/
</style>
</head>
<body>
<!-- Initialize User -->
<%	 	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY);  %>

<!-- Print Complete Navigation Bar --> 
<%String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>

<div id = "main">
<div id="leftMainColumn">
<%ArrayList<Announcement> announcements = Announcement.getAllAnnouncements(user.getConnection());%>
<div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"><a href="#">Announcements <span class="badge pull-right"><%=announcements.size()%><% %></span></a></h2>
  </div>
  <div class="panel-body">
        <ul class="list-group">
	 <%  	 
        if(announcements.size()==0){ %>
        	<li class="list-group-item">There are no announcements to display</li>
       <% }	else{	
        for (Announcement announcement: announcements) {
        	String announcementText = announcement.getMessage(); %>
        	<li class="list-group-item"><%=announcementText%></li>
        <% }}%>
  	 </ul>
  </div>
</div>

<%ArrayList<Achievement> achievements = user.getAchievements();%>
<div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"><a href="#">Achievements <span class="badge pull-right"><%=achievements.size()%></span></a></h2>
  </div>
  <div class="panel-body">
    <ul class="list-group">
	 <%  	 
        if(achievements.size()==0){ %>
        	<li class="list-group-item">You have not earned any achievements</li>
       <% }	else{	
        for (Achievement achievement: achievements) {
        	String achievementType = achievement.name(); %>
        	<li class="list-group-item"><table><tr><td><img style="height:auto; width:auto; max-width:50px; max-height:50px;" src="<%=achievement.iconURL()%>" class="img-responsive" title="<%=achievement.toolTip()%>" alt="achievement icon"></td><td>    <%=achievementType%></td></tr></table></li>
        <% }}%>
  	 </ul>
  </div>
</div>

<%ArrayList<Message> messages = (ArrayList<Message>) user.getMessagesReceived();%>
<div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"><a href="inbox.jsp">Inbox <span class="badge pull-right"><%= user.getNumUnreadMessages() %></span></a></h2>
  </div>
  <div class="panel-body">
     <ul class="list-group">
	 <%  
        if(messages.size()==0){ %>
        	<li class="list-group-item">There are no messages to display </li>
       <% }	else{	
        for (Message message : messages) {
        	User sender = message.getSender();
        	long date = message.getDateSent();
        	String senderName = sender.getUsername(); %>
        	<li class="list-group-item"><a href="inbox.jsp">Message: </a> <%=message.getTitle()%></li>
        <% }}%>
  	 </ul>
  </div>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title">Friend's Recent Activity</h2>
  </div>
  <div class="panel-body">
         <ul class="list-group">
	 <%  
		ArrayList<ActivityEvent> friendActivity = user.getFriendsRecentActivity();	 
        if(friendActivity.size()==0){ %>
        	<li class="list-group-item">There is no activity to display</li>
       <% }	else{	
        for (ActivityEvent activityEvent : friendActivity) {
        	if(activityEvent.getUnderlying() instanceof Score){%>
            	<li class="list-group-item"><%=HTMLHelper.linkforUser(activityEvent.getUser())%> took the quiz: <%=HTMLHelper.linkforQuizSummary(((Score) activityEvent.getUnderlying()).getQuiz())%></li>
        	<%}else if(activityEvent.getUnderlying() instanceof Quiz){%>
        		<li class="list-group-item"><%=HTMLHelper.linkforUser(activityEvent.getUser())%> created the quiz: <%=HTMLHelper.linkforQuizSummary(((Quiz)activityEvent.getUnderlying()))%></li>
        	<%}else if(activityEvent.getUnderlying() instanceof Achievement){%>
        		<li class="list-group-item"><%=HTMLHelper.linkforUser(activityEvent.getUser())%> earned the achievement: <%=((Achievement)(activityEvent.getUnderlying())).name()%></li>
        	<%}
         }}%>
  	 </ul>
  </div>
</div>
</div> 

<div id="rightMainColumn">
<div class="list-group">
  <li class="list-group-item">
    <h5 class="list-group-item-heading">Popular Quizzes</h4>
    <p class="list-group-item-text">
      
    <% ArrayList<Quiz> popularQuizzes = Quiz.getMostPopularQuizzes(user.getConnection());
    	if(popularQuizzes.size()==0){ %>
		<li class="list-group-item">There are no quizzes to display</li>
		<% } else{	
				for (Quiz popularQuiz : popularQuizzes) {
				//String quizName = popularQuiz.getName(); 
				%>
				<li class="list-group-item"><%=HTMLHelper.linkforQuizSummary(popularQuiz)%></li>
				<% }}%>
    </p>
</li>
    <li class="list-group-item">
    <h5 class="list-group-item-heading">Recently Created Quizzes</h4>
    <p class="list-group-item-text">
        <% ArrayList<Quiz> recentQuizzes = Quiz.getRecentlyCreatedQuizzes(user.getConnection());
    	if(recentQuizzes.size()==0){ %>
		<li class="list-group-item">There are no quizzes to display</li>
		<% } else{	
				for (Quiz recentQuiz : recentQuizzes) {
				//String quizName = recentQuiz.getName(); 
				%>
				<li class="list-group-item"><%=HTMLHelper.linkforQuizSummary(recentQuiz)%></li>
				<% }}%>
    </p>
  </li>
    <li class="list-group-item">
    <h5 class="list-group-item-heading">My Recent Quiz Taking Activity</h4>
    <p class="list-group-item-text">
        <% ArrayList<Quiz> recentQuizzesTaken = user.getRecentlyTakenQuizzes(user.getConnection());
    	if(recentQuizzesTaken.size()==0){ %>
		<li class="list-group-item">There are no quizzes to display</li>
		<% } else{	
				for (Quiz recentQuizTaken : recentQuizzesTaken) {
				//String quizName = recentQuizTaken.getName(); 
				%>
				<li class="list-group-item"><%=HTMLHelper.linkforQuizSummary(recentQuizTaken)%></li>
				<% }}%>
    </p>
  </li>
    <li class="list-group-item">
    <h5 class="list-group-item-heading">My Recent Quiz Creating Activity</h4>
    <p class="list-group-item-text">
        <% ArrayList<Quiz> recentQuizzesCreated = user.getRecentlyCreatedQuizzes(user.getConnection());
    	if(recentQuizzesCreated.size()==0){ %>
		<li class="list-group-item">There are no quizzes to display</li>
		<% } else{	
				for (Quiz recentQuizCreated : recentQuizzesCreated) {
				//String quizName = recentQuizCreated.getName(); 
				%>
				<li class="list-group-item"><%=HTMLHelper.linkforQuizSummary(recentQuizCreated) %></li>
				<% }}%>    
    </p>
  </li>
</div>

</div>

</div>

</body>
</html>