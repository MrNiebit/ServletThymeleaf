# Servlet配置Thymeleaf

```java
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

```


UserServlet.java

```java
package cn.lacknb.servlet;

import cn.lacknb.beans.User;
import cn.lacknb.thymeleaf.WebApplication;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebApplication webapp = WebApplication.getInstance(this.getServletContext());
        // 设置用户数据
        // thymeleaf中context
        Context context = new Context();
        context.setVariable("users", getUsers());
        // 获取模板引擎实例
        TemplateEngine engine = webapp.getTemplateEngine();
        String result = engine.process("user", context);
        resp.setContentType("text/html; charset=utf-8");
        // 输出渲染后的HTML
        resp.getWriter().print(result);

//        新建一个user.html, 在WEB-INF/templates/
    }

    private List<User> getUsers(){
        List<User> users = new ArrayList<User>();
        users.add(new User(1, "测试1"));
        users.add(new User(2, "测试2"));
        users.add(new User(3, "测试3"));
        users.add(new User(4, "测试4"));
        users.add(new User(5, "测试5"));
        return users;
    }


}

```