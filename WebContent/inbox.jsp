<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="QuizProject.UserPackage.MessagePackage.*" %>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="Servlets.*" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Inbox</title>
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
<%	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY);  %>

<!-- Print Complete Navigation Bar --> 
<% String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>

<div id="main">
<!-- Button trigger modal -->
<h2> Messages  
<button type="button" data-toggle="modal" data-target="#composeModal" class="btn btn-info pull-right"><span class="glyphicon glyphicon-pencil"></span> Compose</button>
</h2>

<br>
<!-- Nav tabs -->
<ul class="nav nav-tabs">
  <li><a href="#inbox" data-toggle="tab"><h4> Inbox    <span class="badge"><%=user.getNumUnreadMessages()%></span></h4></a></li>
  <li><a href="#outbox" data-toggle="tab"><h4> Outbox </h4></a></li>
</ul>
<!-- Tab panes -->
<div class="tab-content">
	<div class="tab-pane active" id="inbox">
		<div class="panel panel-default">
			<div class="panel-group" id="accordion">
			<!-- display separate messages here -->
			<%ArrayList<Message> messages = (ArrayList<Message>) user.getMessagesReceived();
			int inboxIndex=0;
	        if(messages.size()==0){ %>
	        <ul>
        	<li class="list-group-item">There are no messages to display </li>
        	</ul>
	       <% }	else{	
	        for (Message message : messages) {
	        	inboxIndex++;
	        	String date = Utilities.convertLongToDate(message.getDateSent());
	        	User sender = message.getSender();
	        	String messageHeader = message.getTitle();
	        	String messageBody = "";
	        	
	        	if (message instanceof NoteMessage){ 
	        		messageBody = ((NoteMessage) message).getMessage();
	        		
	        	} else if (message instanceof ChallengeMessage) {
	        		ChallengeMessage challenge = (ChallengeMessage) message;
	        		
	        		if (challenge.hasBeenAccepted()) {
	        			messageBody = "You have accepted " + HTMLHelper.linkforUser(sender) + "'s challenge to take quiz: " + HTMLHelper.linkforQuizSummary(challenge.getQuiz());
	        		} else {
	        			messageBody = HTMLHelper.linkforUser(sender) + " has challenged you to take quiz: " + HTMLHelper.linkforQuizSummary(challenge.getQuiz());
	        		}
					
	        	} else if (message instanceof FriendRequestMessage){	
	        		messageBody = HTMLHelper.linkforUser(sender) + " has sent you a friend request." + 
			        		"<br><form action=\"AcceptFriendServlet\" method=\"post\">"+
			        		"<input name=\"id\" type=\"hidden\" value=\""+message.getID()+"\"/>";
	        	}
	        	
	        	if (sender.exists()) {
	        		String senderName = sender.getUsername(); 
		        	if (message instanceof FriendRequestMessage){	        			        		
		        		String acceptButton = "";
		        		if (user.isFriendsWith(sender.getID())) {
		        			acceptButton = "<button type=\"button\" class=\"btn btn-default pull-right\">Friend Confirmed!</button></form>"; 
		        		} else {
		        			acceptButton = "<button type=\"submit\" class=\"btn btn-info pull-right\">Accept Friend</button></form>";
		        		}
		        		
		        		messageBody += acceptButton;
		        	}
	        	}
	        	
	        	message.wasRead();
	        	
	        	%>
	        	<div class="panel panel-info">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			        <a data-toggle="collapse" data-parent="#accordion" href="#inbox<%=inboxIndex%>">
			          <%=messageHeader%>
			        </a>
			      </h4>
			    </div> <!-- panel heading div -->

			    <div id="inbox<%=inboxIndex%>" class="panel-collapse collapse in">
			      <div class="panel-body">
			         <%=messageBody%> 
			      </div> <!-- panel body div -->
			    </div> <!-- collapse panel body div -->
			  </div> <!-- panel panel-info div -->
			 <% }}%>
			</div> <!-- accordion panel group div -->
		</div> <!-- overall panel div -->
	</div> <!-- inbox div -->

<div class="tab-pane" id="outbox">
	<div class="panel panel-default">
	<div class="panel-group" id="OutboxAccordion">
	
			<%ArrayList<Message> outboxMessages = (ArrayList<Message>) user.getMessagesSent();
			int outboxIndex = 0;
	        if(outboxMessages.size()==0){ %>
	        <ul>
        	<li class="list-group-item">There are no messages to display </li>
        	</ul>
	       <% }	else{	
	        for (Message message : outboxMessages) {
	        	outboxIndex++;
	        	String date = Utilities.convertLongToDate(message.getDateSent());
	        	User recipient = message.getRecipient();
	        	String recipientName = recipient.getUsername(); 
	        	String messageBody = "";
	        	String messageHeader = "";
	        	if (message instanceof NoteMessage){ 
	        		messageBody = ((NoteMessage) message).getMessage();
	        		messageHeader = "Message to " + recipientName + "   "+ date;
	        	}
	        	else if (message instanceof ChallengeMessage) {
	        		messageBody = "You challenged " + HTMLHelper.linkforUser(recipient) +" to take quiz: " + HTMLHelper.linkforQuizSummary(((ChallengeMessage) message).getQuiz());
					messageHeader = "Challenged " + recipientName + "   "+ date;
	        	}else if (message instanceof FriendRequestMessage){
	        		messageHeader =  "Friend request to " + recipientName + "  "+ date;
	        		messageBody = "You sent " + HTMLHelper.linkforUser(recipient) + " a friend request." + 
	        		"<br><form action=\"AcceptFriendServlet\" method=\"post\"> <button type=\"submit\" class=\"btn btn-info pull-right\">Accept Friend</button></form>";
	        	}
	        	%>
	        	<div class="panel panel-info">
			    <div class="panel-heading">
			      <h4 class="panel-title">
			        <a data-toggle="collapse" data-parent="#accordion" href="#outbox<%=outboxIndex%>">
			          <%=messageHeader%>
			        </a>
			      </h4>
			    </div> <!-- panel heading div -->

			    <div id="outbox<%=outboxIndex %>" class="panel-collapse collapse in">
			      <div class="panel-body">
			         <%=messageBody%> 
			      </div> <!-- panel body div -->
			    </div> <!-- collapse panel body div -->
			  </div> <!-- panel panel-info div -->
			 <%}}%>
	  
	</div>
	</div>
</div> <!-- Outbox Div -->
</div> <!-- Tab Content Div -->
</div> <!-- End of Main div -->

<!-- Compose Modal -->
<div class="modal fade" id="composeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">Compose Message</h4>
      </div>
      <div class="modal-body">

<form class="form-horizontal" role="form" action = "SendMessageServlet" method="post">
  <div class="form-group">
    <label for="inputToUsername" class="col-sm-1 control-label">To:</label>
    <div class="col-sm-10">
      <input type="text" maxlength="200" class="form-control" name="recipient" id="recipient" placeholder="Username">
    </div>
  </div>
<hr>
<!-- <input type="text" class="form-control" id="composedText" name="composedText" maxlength="1000" placeholder="Enter message text here"> -->
<textarea class="form-control" rows="3" maxlength="1000" placeholder="Enter message text here" name = "composedText" id="composedText"></textarea>
      </div> <!-- End of body div -->
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="submit" class="btn btn-info">Send</button>
      </div>
</form>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.compose-modal -->

</body>
</html>