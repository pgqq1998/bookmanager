package com.imooc.bookmanager.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.bookmanager.biz.BookBiz;
import com.imooc.bookmanager.biz.impl.BookBizImpl;
import com.imooc.bookmanager.entity.Book;
import com.imooc.bookmanager.entity.Category;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookController {
    private BookBiz bookBiz = BookBizImpl.getInstance();

    //    /list.do
    public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNum = request.getParameter("pageNum");
        if(pageNum == null)
            pageNum = "1";
        int categoryId;
        //    获得categoryId，用于选择显示哪一类图书
        //    第一优先级：request中传入的categoryId参数
        if(request.getParameter("categoryId") != null) {
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
        }
        //    第二优先级：session中的categoryId
        else if(request.getSession().getAttribute("categoryId")!=null) {
            categoryId = (Integer)request.getSession().getAttribute("categoryId");
        }
        //    第三优先级：servletContext中选择第一个的categoryId
        else{
            List<Category> list = (List<Category>)request.getServletContext().getAttribute("CategoryList");
            categoryId = list.get(0).getId();
        }
        //    categoryId存入Session中，下一次访问list不需要另外传入
        request.getSession().setAttribute("categoryId",categoryId);
        //    获得该类图书列表
        PageInfo<Book> pageInfo = bookBiz.getForCategory(categoryId,Integer.parseInt(pageNum),10);
        request.setAttribute("pageInfo",pageInfo);
        request.getRequestDispatcher("/WEB-INF/pages/admin/book_list.jsp").forward(request,response);
    }

    //    /toAdd.do
    public void toAdd(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/admin/book_add.jsp").forward(request,response);
    }

    //    /add.do
    public void add(HttpServletRequest request,HttpServletResponse response) throws IOException, FileUploadException {
        List<Book> bookList = getBookList(request);
        bookBiz.batchAdd(bookList);
        response.sendRedirect("list.do");
    }

    //    /remove.do
    public void remove(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        //    删除图书及存储的图片
        bookBiz.remove(id,request.getServletContext().getRealPath("/"));
        response.sendRedirect("list.do");
    }

    //    toEdit.do
    public void toEdit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookBiz.get(id);
        request.setAttribute("book",book);
        request.getRequestDispatcher("/WEB-INF/pages/admin/book_edit.jsp").forward(request,response);
    }

    //    edit.do
    public void edit(HttpServletRequest request,HttpServletResponse response) throws IOException, FileUploadException {
        List<Book> bookList = getBookList(request);
        Book book = bookList.get(0);
        //    修改图书信息，若图片有变化，删除本地旧图片
        bookBiz.edit(book,request.getServletContext().getRealPath("/"));
        response.sendRedirect("list.do");
    }

    /**
     * 从带有上传文件的request中获得 List<Book>
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws FileUploadException
     */
    public List<Book> getBookList(HttpServletRequest request) throws UnsupportedEncodingException, FileUploadException {

        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        List<FileItem> fileItems = servletFileUpload.parseRequest(request);
        //    储存Book对象
        List<Book> bookList = new ArrayList<>();
        //    临时存放key和value，利用buildBook()函数获得Book对象
      Map<String,String> map = new HashMap<String,String>();
        for(FileItem fileItem : fileItems){
            //    普通属性
            if(fileItem.isFormField())
            {
                String key = fileItem.getFieldName();
                String value = fileItem.getString("UTF-8");
                //    end_flag位于最后面，当接收到该标志意味着前面数据都已接受完，可以转化为Book对象
                //    一个表单的值都获取到了(包括文件)
                if("end_flag".equals(key) && "true".equals(value)) {
                    //    转化为book对象并存入list中
                    bookList.add(buildBook(map));
                    //    清空map，继续存储数据准备下一次转化为Book对象
                    map.clear();
                }
                //    一个表单值还未获取完
                else {
                    //    存入map中
                    map.put(key,value);
                }
            }
            //    文件
            else{
                //     文件存在并且为所需要的图片
                if(fileItem.getFieldName().equals("smallImg") && fileItem.getName().length() > 1){
                    String rootPath = request.getServletContext().getRealPath("/");
                    String path = fileItem.getName();
                    String type = ".jpg";
                    if(path.lastIndexOf(",")!=-1)
                        type = path.substring(path.lastIndexOf("."));
                    path = "/download/images/" + System.currentTimeMillis() + type;
                    try {
                        fileItem.write(new File(rootPath+path));
                        map.put("imgPath",path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return bookList;
    }

    /**
     * getBookList()的辅助函数，负责将map中的值取出赋值到新的Book对象中，并返回Book对象
     * @param map
     * @return
     */
    public Book buildBook(Map<String,String> map){
        Book book = new Book();
        //    id, 可能不存在
        if(map.get("id")!=null)
            book.setId(Integer.parseInt(map.get("id")));
        //    categoryId
        book.setCategoryId(Integer.parseInt(map.get("categoryId")));
        //    name
        book.setName(map.get("name"));
        //    level
        book.setLevel(Integer.parseInt(map.get("level")));
        //    price, 可能为小数
        book.setPrice((int)Double.parseDouble(map.get("price")));
        //    imgPath
        book.setImgPath(map.get("imgPath"));
        //    createTime and updateTime, 赋值为当前时间
        book.setCreateTime(new Timestamp(System.currentTimeMillis()));
        book.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return book;
    }
}
