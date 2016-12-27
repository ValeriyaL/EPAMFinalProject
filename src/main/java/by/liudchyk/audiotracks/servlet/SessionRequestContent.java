package by.liudchyk.audiotracks.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 26.11.2016.
 */
public class SessionRequestContent {
    private HashMap<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private HashMap<String, Object> sessionAttributes;

    public void extractValues(HttpServletRequest request) {
        requestParameters = request.getParameterMap();
        requestAttributes = new HashMap<String, Object>();
        Enumeration<String> attr = request.getAttributeNames();
        while (attr.hasMoreElements()) {
            String name = attr.nextElement();
            requestAttributes.put(name, request.getAttribute(name));
        }
        sessionAttributes = new HashMap<String, Object>();
        HttpSession session = request.getSession(true);
        attr = session.getAttributeNames();
        while (attr.hasMoreElements()) {
            String name = attr.nextElement();
            sessionAttributes.put(name, session.getAttribute(name));
        }
    }

    public void insertAttributes(HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public void setRequestParameters(Map<String, String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public String getParameter(String key) {
        if(requestParameters.isEmpty()){
            return null;
        }
        return requestParameters.get(key)[0];
    }

    public Object getAttribute(String key) {
        return requestAttributes.get(key);
    }

    public Object getSessionAttribute(String key) {
        return sessionAttributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        requestAttributes.put(key, value);
    }

    public void setSessionAttribute(String key, Object value) {
        sessionAttributes.put(key, value);
    }

}


