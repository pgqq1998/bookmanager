package com.imooc.bookmanager.controller;

import checkers.units.quals.C;
import com.github.pagehelper.PageInfo;
import com.imooc.bookmanager.biz.BookBiz;
import com.imooc.bookmanager.biz.CategoryBiz;
import com.imooc.bookmanager.biz.impl.BookBizImpl;
import com.imooc.bookmanager.biz.impl.CategoryBizImpl;
import com.imooc.bookmanager.entity.Book;
import com.imooc.bookmanager.entity.Category;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class CategoryController {
    private CategoryBiz categoryBiz = CategoryBizImpl.getInstance();
    private BookBiz bookBiz = BookBizImpl.getInstance();

    //    /list.do
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //    获得分类列表
        List<Category> categoryList =  categoryBiz.getAll();
        request.getServletContext().setAttribute("categoryList",categoryList);
        request.getRequestDispatcher("/WEB-INF/pages/admin/category_list.jsp").forward(request,response);
    }

    //    /toAdd.do
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/admin/category_add.jsp").forward(request,response);
    }

    //    /add.do
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        //    封装对象
        Category category = new Category();
        category.setName(name);
        category.setCreateTime(new Timestamp(System.currentTimeMillis()));
        category.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        categoryBiz.add(category);
        response.sendRedirect("list.do");
    }

    //    /remove.do
    public void remove(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        PageInfo bookList = bookBiz.getForCategory(id,1,1);
        //    分类下还有书籍，不允许删除
        if(bookList.getList().size() > 0){
            request.setAttribute("msg","该分类下还有书籍，不允许删除");
            list(request,response);
            return;
        }
        //    分类下无书籍，删除分类后跳转到list.do
        categoryBiz.remove(id);
        response.sendRedirect("list.do");
    }

    //    /descadeRemove
    public void descadeRemove(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        //    删除该分类所有书籍以及本地图片，最后删除该分类
        categoryBiz.descadeRemove(id,request.getServletContext().getRealPath("/"));
        response.sendRedirect("list.do");
    }

    //    /toEdit.do
    public void toEdit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Category category = categoryBiz.get(id);
        request.setAttribute("category",category);
        request.getRequestDispatcher("/WEB-INF/pages/admin/category_edit.jsp").forward(request,response);
    }

    //    /edit.do
    public void edit(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        //    封装数据
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setUpdateTime(updateTime);
        //    修改分类信息
        categoryBiz.edit(category);
        response.sendRedirect("list.do");
    }
}
