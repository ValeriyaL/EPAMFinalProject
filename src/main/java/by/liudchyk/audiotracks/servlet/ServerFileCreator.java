package by.liudchyk.audiotracks.servlet;

import by.liudchyk.audiotracks.exception.LoadException;
import by.liudchyk.audiotracks.exception.LogicException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Class {@code ServerFileCreator} is used to load file to the server
 *
 * @author Liudchyk Valeriya
 */
public class ServerFileCreator {
    private static final Logger LOG = LogManager.getLogger();
    private final String ITEM_ATTRIBUTE = "item";
    private final String ENCODING = "UTF-8";

    /**
     * Creates file on server and gets other request params
     *
     * @param request is servlet's request
     * @return HashMap with server params
     */
    public HashMap<String, String[]> createServerFile(HttpServletRequest request) {
        HashMap<String, String[]> requestParameters = new HashMap<>();
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        List items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            LOG.error(e);
        }
        Iterator iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();
            if (item.isFormField()) {
                String value = "";
                try {
                    value = item.getString(ENCODING);
                } catch (UnsupportedEncodingException e) {
                    LOG.warn("Can't encode parameter");
                }
                requestParameters.put(item.getFieldName(), new String[]{value});
            } else {
                byte[] data = item.get();
                FileOutputStream fos;
                try {
                    request.setAttribute(ITEM_ATTRIBUTE, item.getName());
                    fos = new FileOutputStream(new File(PATH + item.getName()));
                    fos.write(data, 0, data.length);
                    fos.flush();
                } catch (IOException e) {
                    LOG.error("Mistake in file creating", e);
                }
            }
        }
        return requestParameters;
    }

    public static final String PATH = "D:\\Apache Software Foundation\\Tomcat 8.0\\tracks\\";
}
