package by.liudchyk.audiotracks.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class NotLoginTag extends TagSupport {
    private final String FALSE = "false";
    private final String IS_LOGINED_ATTR = "isLogin";

    @Override
    public int doStartTag() throws JspException {
        String isLogin =(String) pageContext.getSession().getAttribute(IS_LOGINED_ATTR);
        if (isLogin==null || FALSE.equals(isLogin)) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}


