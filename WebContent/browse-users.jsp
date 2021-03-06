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
<title>Browse Friends</title>
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
		String type = "";
		String value = "";
		if(request.getParameter("type")!=null&&!request.getParameter("type").equals("")){
			type = request.getParameter("type");
		}
		if(request.getParameter("value")!=null&&!request.getParameter("value").equals("")){
			value = request.getParameter("value");
		}%>

<!-- Print Complete Navigation Bar --> 
<%String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>

<div id="main">
<h2>Browse Users</h2>
	<table align="center">
		<tr>
			<form action="BrowseUsersServlet" method="post">
				<input type="hidden" name="type" value="searchByName">
				<td><input type="text" class="form-control" name="value" placeholder="Enter User Name"></td>
				<td><button type="submit" class="btn btn-info pull-right" value = "Submit">Search by Name</button></td>
			</form>
			<td></td>
			<form action="BrowseUsersServlet" method="post">
				<input type="hidden" name="type" value="searchAll">
				<td><button type="submit" class="btn btn-primary pull-right" value = "Submit">Show All Users</button></td>
			</form>
		</tr>
	</table>
<br>

	<%ArrayList<User> users = new ArrayList<User>();
	if(type.equals("searchByName")){
		users = User.getUsersLikeName(user.getConnection(), value);
	} else{
		users = User.getAllUsers(user.getConnection());
	}%>
	
  <div class="panel panel-default">
  <div class="panel-heading">Users</div>
  <!-- Table -->
  <table class="table">
         <% 
         if(users.size()==0){ %>
        	<tbody>
        	<li class="list-group-item">There are no users to display</li>
       <% }	else{	%>
    	     <thead>
             <tr>
               <th>#</th>
               <th>Username</th>            
               <th></th>
               <th></th>
               <th></th>
             </tr>
           </thead>
           <tbody>  
        <% int userIndex = 1;
        for (User currUser: users) { %>
          <tr>
            <td><%=userIndex %></td> <!-- quiz number -->
            <td><%=HTMLHelper.linkforUser(currUser)%></td> <!-- quiz title -->           
            <td></td> <!-- quiz creator -->
            <!-- Initiate Quiz.  -->
			<td>
				<%if(user.getID()==currUser.getID()){%>
					<form action="edit-profile.jsp" method="post">
					<input name="id" type="hidden" value="<%=currUser.getID()%>"/>
					<button type="submit" id="editProfile" class="btn btn-info pull-right">Edit Profile</button>
					</form>
				<%} else if(!user.isFriendsWith(currUser.getID())){
						if(user.hasFriendRequestPending(user.getConnection(), currUser.getID())){%>
							<button type="button" id="requestPending" class="btn btn-default pull-right">Request Pending</button>
						<%}else if (user.hasFriendRequestInbound(user.getConnection(), currUser.getID())) {%>
							<button type="button" id="requestPending" class="btn btn-default pull-right">Request Inbound</button>
						<%} else {%>
							<form action="SendFriendRequestMessageServlet" method="post">
							<input name="id" type="hidden" value="<%=currUser.getID()%>"/>
							<button type="submit" id="addFriend" class="btn btn-info pull-right">Add Friend</button>
							</form>
						<%}
				} else if(user.isFriendsWith(currUser.getID())){%>
						<button type="button" id="addFriend" class="btn btn-info pull-right">Friends!</button>
				<%}%>
			</td>
			<!-- Initiate Quiz in Practice Mode -->
			<!-- <td><button type="button" class="btn btn-default pull-right">Start Practice Mode</button></td> -->
          </tr>
        <% userIndex++;}
        }%> 
        </tbody>
  </table>
</div>
</div>

</body>
</html>