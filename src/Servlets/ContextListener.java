package Servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import QuizProject.DBConnection;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

	public static final String CONNECTION_KEY = "connection";

    /**
     * Default constructor. 
     */
    public ContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        ServletContext context = arg0.getServletContext();
        DBConnection connection = new DBConnection();
        context.setAttribute(CONNECTION_KEY, connection);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	// close data base connection
    	ServletContext context = arg0.getServletContext();
    	DBConnection connection = (DBConnection) context.getAttribute(CONNECTION_KEY);
    	if (connection != null) {
    		connection.retire();
    	}
    }
	
}
