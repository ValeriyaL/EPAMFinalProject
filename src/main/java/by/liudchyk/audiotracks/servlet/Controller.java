package by.liudchyk.audiotracks.servlet;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.command.ActionFactory;
import by.liudchyk.audiotracks.database.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class Controller extends HttpServlet implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        int i=0;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().closePool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;
        SessionRequestContent sessionRequestContent = new SessionRequestContent();
        sessionRequestContent.extractValues(request);
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(sessionRequestContent);
        page = command.execute(sessionRequestContent);
        sessionRequestContent.insertAttributes(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);

    }

}

