Quizlet++, A website to create, take, and challenge friends on various types of quizzes writen using the Java Model-View-Controller method of jsps and servlets. 

Names:
Jai Sajnani
Ashkon Farhangi
Saam Motamedi

●	Professional-Grade UI
○	We have an extensive professional-grade UI that permeates the entire project; the extension is too big to describe succinctly here, and navigating to all the pages in the website can give the user a good sense for our UI work 
○	Twitter Bootstrap was used as a base model and further customized with stylesheets and tailored types

●	Achievements
○	Implemented all suggested achievements in the assignment handout
○	Achievements are properly awarded, have custom-made unique icons, and tooltips which appear when user hovers over achievement icon

●	Users
○	Adding salts to password hashing
○	Let users sign out, and a new user then sign in
○	Edit Profile Page that lets you:
■	Change your username
■	Change your password
●	For changing password, it acquires re-authentication 

●	Browse Users Page
○	Professional UI
○	Shows all users and allows you to search for a specific user

●	Browse Quizzes Page
○	Professional UI
○	Shows all quizzes
○	Search all quizzes by name or by tag
○	View all quizzes or view quizzes grouped by category

●	Edit Friends Page
○	Shows all of your friends and lets you remove a friend from the page

●	Friends
○	Whenever an add friend button would appear (whether on a user page or in the inbox), we configure the button text based on the relationship (request pending, request accepted, friend removed) 

●	Messages
○	We have a dedicated inbox and outbox
○	Inbox
■	There are “accepted” properties for challenge and friend request messages
■	There is a “read” property for all messages
■	The properties are utilized from a front-end perspective by using different message descriptions based on read/unread and accepted/unaccepted states
■	on the home page, the count of messages in the message section is the count of unread messages
●	the same is true for the inbox
■	Count of numbers of unread messages, announcements, etc

○	Outbox
■	Users have an outbox that shows all messages they have sent
○	You can view message from the homepage and click directly into inbox as well as accessing inbox from our selection menu

●	Questions
○	4 additional question types fully implemented. Namely:
■	multi-multiple choice
■	ordered multi-answer
■	unordered multi-answer
■	matching
○	We allow for multiple possible strings for each answer (even for multi answer questions)
■	i.e. allows the quiz creator to allow for different answers to a question, which can be useful for different spellings, abbreviations, etc
○	We allow for user creator to add background text to each question in addition to question text

●	Quizzes
○	Practice Mode
○	We keep track of and show a user’s performance on each quiz
●	Graceful Error Handling
○	Bad-Input Page
■	In a number of cases, when bad or malinformed input is entered, we forward the user to a bad input page
■	For example, if you try to create a question with no question text
○	Case Sensitivities
■	We are case insensitive, so quiz takers do not have to be concerned with the case of their answers
○	We enforce character limits on everytime the user inputs text to make sure it doesn’t overload our DB
○	Elegant handling of dependency issues
■	When an object that is depended on is deleted permanently, we prevent crashes; we check to make sure for all objects we link to still exist
○	Friends Error Handling
■	You can’t add someone who is already your friend
■	You can’t send an add to someone who you have already requested or who has already requested you
○	Bad Resource Page
■	A page we intentionally created that the user is sent to if they click on a link to an object that has been deleted

●	Tags and Categories
○	Quiz is put into one of a website administrator list of categories. Quizzes can be sorted by category in the browse quiz page.
○	Quizzes can also be allocated tags. Quizzes can be searched for by tag
○	Tags and categories are displayed on the quiz summary page

●	Ratings and Reviews
○	After taking a quiz, the user is prompted to assign a numerical rating between one and five and write a review of the quiz
○	One of the summary ratings presented  in the quiz summary is the mean review score based on X ratings
○	Recent reviews are showed on the quiz summary page with rating, review text, and date the review was done

●	Reporting Quizzes
○	On quiz summary, there is a button that lets the user report a quiz
○	On the administration page, there is a table that shows the reported quizzes and lets the admin delete reported quizzes
○	If a quiz has already been reported, users are not allowed to report the quiz again and the button indicates that the quiz has been reported

●	Administration
○	Create announcements 
○	Remove user accounts
○	Remove quizzes
○	See site statistics; number of users, number of quizzes taken, number of quizzes created
○	Promote other users to admin
○	Manage Quizzes
■	See reported quizzes and delete reported quizzes
■	See all quizzes and clear history information for a particular quiz

●	Announcements	
○	Admins can create announcements which are published to all users and are printed by date
○	All users will see announcements that an admin has created on the homepage
○	A count of announcements is also displayed on the homepage

●	Practice Mode
○	Quiz has a property which allows it to be taken in practice mode
○	User can choose to take any quiz in practice mode (given that the creator desired this feature for their quiz), and if he/she chooses to do so, implementation is handled as detailed in the handout


