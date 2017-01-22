package by.liudchyk.audiotracks.filter;

import org.junit.Test;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class {@code LoginFilter} is used to filter all logined client's jsp.
 * Controls access to clients jsps only from logined status.
 *
 * @author Liudchyk Valeriya
 * @version 1.0
 * @see Filter
 */
@WebFilter(urlPatterns = {"/jsp/client/*"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class LoginFilter implements Filter {
    private final String PATH_PARAMETER = "INDEX_PATH";
    private final String TRUE = "true";
    private final String IS_LOGIN_ATTRIBUTE = "isLogin";
    private String indexPath;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter(PATH_PARAMETER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (!TRUE.equals(httpRequest.getSession().getAttribute(IS_LOGIN_ATTRIBUTE))) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}