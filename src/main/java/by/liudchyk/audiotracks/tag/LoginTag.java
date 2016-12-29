package by.liudchyk.audiotracks.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class LoginTag extends TagSupport {
    private final String ADMIN = "true";
    private final String IS_LOGINED_ATTR = "isLogin";

    @Override
    public int doStartTag() throws JspException {
        if (ADMIN.equals(pageContext.getSession().getAttribute(IS_LOGINED_ATTR))) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}


