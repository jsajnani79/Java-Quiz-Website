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
 * Servlet implementation class CreateQuestionServlet
 */
@WebServlet("/CreateQuestionServlet")
public class CreateQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		RequestDispatcher dispatch = request.getRequestDispatcher("create-quiz.jsp?id="+id);
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String question = request.getParameter("questionSelect");
		RequestDispatcher dispatch;
	if(question.equals("Question Response")){
		dispatch = request.getRequestDispatcher("input-question-response.jsp?id="+id);
	} else if(question.equals("Fill in the Blank")){
		dispatch = request.getRequestDispatcher("input-fill-in-the-blank.jsp?id="+id);
	} else if(question.equals("Multiple Choice")){
		dispatch = request.getRequestDispatcher("input-multiple-choice.jsp?id="+id);
	} else if(question.equals("Multi-Answer Multiple Choice")){
		dispatch = request.getRequestDispatcher("input-multi-multiple-choice.jsp?id="+id);
	} else if(question.equals("Picture Response")){
		dispatch = request.getRequestDispatcher("input-picture-response.jsp?id="+id);
	} else if(question.equals("Ordered Multiple Answer")){
		dispatch = request.getRequestDispatcher("input-ordered-multi-text.jsp?id="+id);
	} else if(question.equals("Unordered Multiple Answer")){
		dispatch = request.getRequestDispatcher("input-unordered-multi-text.jsp?id="+id);
	} else if(question.equals("Matching")){
		dispatch = request.getRequestDispatcher("input-matching.jsp?id="+id);
	} else {
		dispatch = request.getRequestDispatcher("create-quiz.jsp?id="+id);
	}
		dispatch.forward(request, response);
	}

}
