package by.liudchyk.audiotracks.servlet;

import by.liudchyk.audiotracks.command.ActionCommand;
import by.liudchyk.audiotracks.command.ActionFactory;
import by.liudchyk.audiotracks.command.client.CommentAddCommand;
import by.liudchyk.audiotracks.command.client.DownloadCommand;
import by.liudchyk.audiotracks.database.ConnectionPool;
import by.liudchyk.audiotracks.manager.ConfigurationManager;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class {@code Controller} is a servlet class used as a controller of the application.
 *
 * @author Liudchyk Valeriya
 * @see HttpServlet
 */
@WebServlet("/controller")
public class Controller extends HttpServlet implements ServletContextListener {
    private final String PATH_TRACK = "path.page.track";
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    /**
     * Close ConnectionPool when servlet destroyed
     *
     * @param servletContextEvent is servlet event
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().closePool();
    }

    /**
     * Uses in get requests
     *
     * @param request  is servlet's request
     * @param response is servlet's response
     * @throws ServletException if there are servlet errors
     * @throws IOException      if there are input/output errors
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Uses in post requests
     *
     * @param request  is servlet's request
     * @param response is servlet's response
     * @throws ServletException if there are servlet errors
     * @throws IOException      if there are input/output errors
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Actions after taking request
     *
     * @param request  is servlet's request
     * @param response is servlet's response
     * @throws ServletException if there are servlet errors
     * @throws IOException      if there are input/output errors
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
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
            if(ConfigurationManager.getProperty(PATH_TRACK).equals(page)){
                response.sendRedirect(request.getContextPath()+page);
            }else{
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
                dispatcher.forward(request, response);
            }
        }
    }

}

