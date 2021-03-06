package Servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class orderedQuestionServlet
 */
@WebServlet("/orderedQuestionServlet")
public class orderedQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public orderedQuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("numAnswers").equals("")){
			RequestDispatcher dispatch = request.getRequestDispatcher("input-ordered-multi-text.jsp");
			dispatch.forward(request, response);
		}else{
		String partialNavBar = HTMLHelper.generatePartialNavBar();
		int id = Integer.parseInt(request.getParameter("id"));
		int numAnswers = Integer.parseInt(request.getParameter("numAnswers"));
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter(); 
		
		//out.println("<%@ page language=\"java\" contentType=\"text/html; charset=ISO-8859-1\" pageEncoding=\"ISO-8859-1\"%>");
			out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">");
			out.println("<title>Add Question");
			out.println("</title>");
			out.println("<link rel=\"stylesheet\" href=\"bootstrap/css/bootstrap.css\"/>");
			    out.println("<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->");
			    out.println("<script src=\"https://code.jquery.com/jquery.js\">");
			    out.println("</script>");
			    out.println("<!-- Include all compiled plugins (below), or include individual files as needed -->");
			    out.println("<script src=\"bootstrap/js/bootstrap.js\">");
			    out.println("</script>");
			out.println("<style>");
			out.println("body {");
			out.println("padding-top: 0px;");
			out.println("overflow: auto;");
			out.println("font-family: sans-serif;");
			out.println("}");

			out.println("#questionTable { ");
			out.println("margin-left:auto;");
			out.println("margin-right:auto;");
			out.println("width: 90%;	 ");
			out.println("} ");
			out.println("#main { ");
			out.println("margin-left:auto;");
			out.println("margin-right:auto;");
			out.println("width: 600px;	");
			out.println("font-family: sans-serif; ");
			out.println("} ");

			out.println("</style>");
			out.println("</head>");
			out.println("<body>");

			out.println(partialNavBar);

		out.println("<div id = \"main\">");
		out.println("<form action=\"AddQuestionToQuiz\" method=\"post\">");
		out.println("<input name=\"id\" type=\"hidden\" value=\""+id+"\"/>");
		out.println("<input name=\"numAnswers\" type=\"hidden\" value=\""+numAnswers+"\"/>");
		out.println("<input name=\"questionType\" type=\"hidden\" value=\"OrderedMultiTextQuestion\"/>");
		out.println("<!-- ORDERED MULTI-TEXT  --><!-- UNORDERED MULTI-TEXT -->");
		out.println("<div class=\"panel panel-default\">");
		out.println("<div class=\"panel-heading\"> ");
		out.println("<h3 class=\"panel-title\">5. Multi-Text Ordered</h3>");
		out.println(" </div>");
		out.println("<div class=\"panel-body\">");
		out.println("<textarea class=\"form-control\" maxlength=\"1000\" rows=\"2\" name=\"questionBackground\" placeholder=\"Add any background info on the question here\"></textarea>");
		out.println("</div>");
		out.println("<div id=\"questionTable\">");
		out.println("<textarea class=\"form-control\" maxlength=\"1000\" rows=\"3\" name=\"questionText\" placeholder=\"Type question text here\"></textarea>");
		out.println("<br>");
		out.println("<div class=\"row\">");
		out.println("<div class=\"col-lg-6\">");
		out.println("<div class=\"input-group\">");
		
		for(int i = 0;i<numAnswers;i++){
			int number = i+1;
			out.println("Answer # "+ number);
			out.println("<textarea class=\"form-control\" maxlength=\"1000\" rows=\"2\" name=\"answer"+i+"\" placeholder=\"Add each possible answer on a separate line here (Press ENTER after each answer)\"></textarea>");
		}		
		out.println("</div>");
		out.println("</div>");
		out.println("<br>");
		out.println("</div>");
		out.println("</div>");
		out.println("</div>");
		out.println("<button type=\"submit\" class=\"btn btn-info pull-right\" value = \"Submit\">Submit</button>");
		out.println("</form>");
		out.println("<form action=\"AddQuestionToQuiz\" method=\"get\">");
		out.println("<input name=\"id\" type=\"hidden\" value=\""+id+"\"/>");
		out.println("<button type=\"submit\" class=\"btn btn-warning pull-right\" value = \"Submit\">Cancel</button>");
		out.println("</form>");
		out.println("</div> <!-- end of main div -->");
		out.println("</body>");
		out.println("</html>");
		}
	}

}
