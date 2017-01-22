package by.liudchyk.audiotracks.servlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Class {@code Downloader} is used for shape user's response
 * to download file
 *
 * @author Liudchyk Valeriya
 */
class Downloader {
    private final String MIME_TYPE = "application/octet-stream";
    private final String HEADER_KEY = "Content-Disposition";
    private final String ATTACHMENT = "attachment; filename=\"%s\"";
    private final int END = -1;
    private final int BUFFER_LENGTH = 4096;
    private final int START = 0;

    /**
     * Shapes user's response.
     *
     * @param filePath is path to downloads file
     * @param response is servlet's response
     * @param context  is servlet's context
     */
    void downloadTrack(String filePath, HttpServletResponse response, ServletContext context) {
        try {
            File downloadFile = new File(filePath);
            FileInputStream inStream = new FileInputStream(downloadFile);
            String mimeType = context.getMimeType(filePath);
            if (mimeType == null) {
                mimeType = MIME_TYPE;
            }
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());
            String headerValue = String.format(ATTACHMENT, downloadFile.getName());
            response.setHeader(HEADER_KEY, headerValue);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[BUFFER_LENGTH];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer)) != END) {
                outStream.write(buffer, START, bytesRead);
            }
        } catch (IOException e) {
            //TODO
        }
    }
}
