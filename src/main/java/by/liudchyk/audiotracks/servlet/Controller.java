package by.liudchyk.audiotracks.servlet;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.command.ActionFactory;
import by.liudchyk.audiotracks.command.client.CommentAddCommand;
import by.liudchyk.audiotracks.command.client.DownloadCommand;
import by.liudchyk.audiotracks.database.ConnectionPool;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/controller")
public class Controller extends HttpServlet implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
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
        String page;
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        Map<String, String[]> requestParameters = new HashMap<>();
        if (isMultipart) {
            ServerFileCreator fileCreator = new ServerFileCreator();
            requestParameters = fileCreator.createServerFile(request);
        }
        SessionRequestContent sessionRequestContent = new SessionRequestContent();
        sessionRequestContent.extractValues(request);
        if (isMultipart) {
            sessionRequestContent.setRequestParameters(requestParameters);
        }
        ActionFactory client = new ActionFactory();
        ActionCommand command = client.defineCommand(sessionRequestContent);
        if (command instanceof DownloadCommand) {
            String filePath = command.execute(sessionRequestContent);
            Downloader downloader = new Downloader();
            downloader.downloadTrack(filePath, response, getServletContext());
        } else {
            page = command.execute(sessionRequestContent);
            sessionRequestContent.insertAttributes(request);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }

}

