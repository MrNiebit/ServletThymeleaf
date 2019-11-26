package cn.lacknb.thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

/*
*  对于一个应用, 只需要配置一次Thymeleaf就可以了. 每次请求
*  都可以使用同一个TemplateEngine实例. 可以使用单例模式
*  代码如下:
* */

public class WebApplication {

    private TemplateEngine templateEngine;
    private static WebApplication webApplication;

    /*
    * 私有构造器
    * */

    private WebApplication(ServletContext servletContext){
        // 创建模板解析器
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        // 设置模板模式为HTML
        templateResolver.setTemplateMode(TemplateMode.HTML);
        // 设置前缀与后缀
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        this.templateEngine = new TemplateEngine();
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    public static WebApplication getInstance(ServletContext servletContext){
        if (webApplication == null){
            webApplication = new WebApplication(servletContext);
        }
        return webApplication;
    }

    public TemplateEngine getTemplateEngine(){
        return this.templateEngine;
    }

}
