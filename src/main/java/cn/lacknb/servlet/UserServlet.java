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
