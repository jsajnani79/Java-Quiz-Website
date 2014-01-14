<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.Set" %>
    <%@ page import="QuizProject.UserPackage.MessagePackage.*" %>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="Servlets.*" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Friends</title>
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
<%	 	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY);  %>

<!-- Print Complete Navigation Bar --> 
<%String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>

<div id="main">
<h2>My Friends</h2>
	
  <div class="panel panel-default">
  <div class="panel-heading">Friends</div>
  <!-- Table -->
  <table class="table">
         <%Set<User> friends = user.getFriends(); 
        if (friends.size() == 0) { %>
        	<tbody>
        	<li class="list-group-item">There are no friends to display</li>
       <% }	else{	%>
    	     <thead>
             <tr>
               <th>#</th>
               <th>Name</th>            
               <th></th>
               <th></th>
               <th></th>
             </tr>
           </thead>
           <tbody>  
        <% int friendIndex = 1;
        for (User friend: friends) { %>
          <tr>
            <td><%=friendIndex %></td> <!-- friend number -->
            <td><%=HTMLHelper.linkforUser(friend)%></td> <!-- friend name -->           
            <td></td> 
			<td>
				<form action="RemoveFriendServlet" method="post">
				<input name="id" type="hidden" value="<%=friend.getID()%>"/>
				<button type="submit" id="unfriend" class="btn btn-info pull-right">Remove Friend</button>
				</form>
			</td>
          </tr>
        <% friendIndex++;}
        }%> 
        </tbody>
  </table>
</div>
</div>

</body>
</html>