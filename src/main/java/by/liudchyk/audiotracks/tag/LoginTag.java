package by.liudchyk.audiotracks.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Class {@code LoginTag} is used to determine is user logined.
 *
 * @author Liudchyk Valeriya
 * @version 1.0
 * @see TagSupport
 */
@SuppressWarnings("serial")
public class LoginTag extends TagSupport {
    private final String TRUE = "true";
    private final String IS_LOGINED_ATTR = "isLogin";

    /**
     * Skips body if user doesn't logined,
     * uncludes tag's body otherwise
     *
     * @return skip or include body
     * @throws JspException if there are some jsp errors
     */
    @Override
    public int doStartTag() throws JspException {
        if (TRUE.equals(pageContext.getSession().getAttribute(IS_LOGINED_ATTR))) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }
}


