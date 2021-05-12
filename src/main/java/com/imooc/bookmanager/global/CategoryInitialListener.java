package com.imooc.bookmanager.global;

import com.imooc.bookmanager.biz.impl.CategoryBizImpl;
import com.imooc.bookmanager.entity.Category;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.List;

@WebListener
public class CategoryInitialListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public CategoryInitialListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //    将categoryList保存到ServletContext
        List<Category> categoryList = CategoryBizImpl.getInstance().getAll();
        sce.getServletContext().setAttribute("categoryList",categoryList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
