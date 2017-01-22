package by.liudchyk.audiotracks.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class {@code AdminFilter} is used to filter all admin's jsp.
 * Controls access to admin's jsps only from admin role.
 *
 * @author Liudchyk Valeriya
 * @version 1.0
 * @see Filter
 */

@WebFilter(urlPatterns = {"/jsp/admin/*"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class AdminFilter implements Filter {
    private final String PATH_PARAMETER = "INDEX_PATH";
    private final String ROLE_ATTRIBUTE = "role";
    private final String ADMIN = "Admin";
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
        if (!ADMIN.equals(httpRequest.getSession().getAttribute(ROLE_ATTRIBUTE))) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}