package by.liudchyk.audiotracks.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Class {@code SessionRequestContent} is used to get and store all request params,
 * session ans request attributes.
 *
 * @author Liudchyk Valeriya
 * @see HttpServletRequest
 */
public class SessionRequestContent {
    /**
     * Servlet request's attributes
     */
    private HashMap<String, Object> requestAttributes;
    /**
     * Servlet request's parameters
     */
    private Map<String, String[]> requestParameters;
    /**
     * Servlet session's attributes
     */
    private HashMap<String, Object> sessionAttributes;

    /**
     * Store request attributes to HashMap
     *
     * @param request is servlet's request
     */
    public void extractValues(HttpServletRequest request) {
        requestParameters = request.getParameterMap();
        requestAttributes = new HashMap<>();
        Enumeration<String> attr = request.getAttributeNames();
        while (attr.hasMoreElements()) {
            String name = attr.nextElement();
            requestAttributes.put(name, request.getAttribute(name));
        }
        sessionAttributes = new HashMap<>();
        HttpSession session = request.getSession(true);
        attr = session.getAttributeNames();
        while (attr.hasMoreElements()) {
            String name = attr.nextElement();
            sessionAttributes.put(name, session.getAttribute(name));
        }
    }

    /**
     * Sets all request and session attributes to request
     *
     * @param request is servlet's request
     */
    public void insertAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Stores all request's parameters to Map
     *
     * @param requestParameters is request's params
     */
    public void setRequestParameters(Map<String, String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    /**
     * Gets parameter value by key
     *
     * @param key is parameter's key
     * @return value for this key
     */
    public String getParameter(String key) {
        if (requestParameters.isEmpty()) {
            return "";
        }
        return requestParameters.containsKey(key)?requestParameters.get(key)[0]:"";
    }

    /**
     * Checks does parameter with such key exist
     *
     * @param key is parameter's key
     * @return true if parameter exists,
     * false otherwise
     */
    public boolean isParameter(String key) {
        return requestParameters.containsKey(key);
    }

    /**
     * Gets request attribute by key
     *
     * @param key is attribute's key
     * @return value for this key
     */
    public Object getAttribute(String key) {
        return requestAttributes.get(key);
    }

    /**
     * Gets session attribute by key
     *
     * @param key is attribute's key
     * @return value for this key
     */
    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    /**
     * Sets attribute with key and value to HashMap
     *
     * @param key   is attribute's key
     * @param value is attribute's value
     */
    public void setAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    /**
     * Sets session attribute with key and value to HashMap
     *
     * @param key   is attribute's key
     * @param value is attribute's value
     */
    public void setSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }

}


