package web.spring.mvc.viewresolver;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * XML视图解析器
 */
public class XmlViewResolver implements ViewResolver {
    private View view;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale)
            throws Exception {
        return this.view;
    }
}
