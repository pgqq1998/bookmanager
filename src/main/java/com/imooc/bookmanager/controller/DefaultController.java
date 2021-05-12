package com.imooc.bookmanager.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.bookmanager.biz.BookBiz;
import com.imooc.bookmanager.biz.impl.BookBizImpl;
import com.imooc.bookmanager.entity.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultController {
    private BookBiz bookBiz = BookBizImpl.getInstance();
    //    /index.do
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNum = request.getParameter("pageNum");
        if(pageNum == null)
            pageNum = "1";
        PageInfo<Book> pageInfo = bookBiz.getAll(Integer.parseInt(pageNum),8);
        request.setAttribute("pageInfo",pageInfo);
        request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request,response);
    }
    //    /list.do
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNum = request.getParameter("pageNum");
        if(pageNum == null)
            pageNum = "1";
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        PageInfo<Book> pageInfo = bookBiz.getForCategory(categoryId,Integer.parseInt(pageNum),8);
        request.setAttribute("pageInfo",pageInfo);
        request.setAttribute("categoryId",categoryId);
        request.getRequestDispatcher("/WEB-INF/pages/list.jsp").forward(request,response);
    }
}
