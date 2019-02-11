package me.js;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {
    // 서블릿 컨텍스트의 라이프사이클 이벤트

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("context initialized");
        sce.getServletContext().setAttribute("name", "jongseon");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("context destory");
    }
}
