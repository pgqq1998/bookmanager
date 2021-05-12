package com.imooc.bookmanager.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.bookmanager.biz.BookBiz;
import com.imooc.bookmanager.dao.BookDao;
import com.imooc.bookmanager.entity.Book;
import com.imooc.bookmanager.global.DaoFactory;

import java.io.File;
import java.util.List;

public class BookBizImpl implements BookBiz {
    private static BookBizImpl bookBizImpl;
    private BookDao bookDao;
    private BookBizImpl(){
        bookDao = DaoFactory.getInstance().getDao(BookDao.class);
    }
    public static BookBizImpl getInstance(){
        if(bookBizImpl == null)
            bookBizImpl = new BookBizImpl();
        return bookBizImpl;
    }
    @Override
    public void batchAdd(List<Book> list) {
        bookDao.batchInsert(list);
    }

    @Override
    public void remove(int id) {
        bookDao.delete(id);
    }

    @Override
    public void remove(int id, String rootPath) {
        Book book = bookDao.select(id);
        String path = book.getImgPath();
        File imgFile = new File(rootPath + path);
        if(imgFile.exists())
            imgFile.delete();
        remove(id);
    }


    @Override
    public PageInfo<Book> getForCategory(int categoryId,int pageNum,int limit) {
        PageHelper.startPage(pageNum,limit);
        return PageInfo.of(bookDao.selectByCid(categoryId));
    }

    @Override
    public Book get(int id) {
        return bookDao.select(id);
    }

    @Override
    public void edit(Book book) {
        bookDao.update(book);
    }

    @Override
    public void edit(Book book, String rootPath) {
        Book oldBook = bookDao.select(book.getId());
        //    上传了新照片
        if(oldBook.getImgPath()!=null && !oldBook.getImgPath().equals(book.getImgPath()))
        {
            //    删除原有旧照片
            String path = oldBook.getImgPath();
            File imgFile = new File(rootPath + path);
            if(imgFile.exists())
                imgFile.delete();
        }
        edit(book);
    }

    @Override
    public void removeForCategory(int categoryId) {
        bookDao.deleteByCid(categoryId);
    }

    @Override
    public void removeForCategory(int categoryId, String rootPath) {
        List<Book> bookList = bookDao.selectByCid(categoryId);
        //    删除所有该分类下的图片
        for(Book book : bookList){
            String path = book.getImgPath();
            File imgFile = new File(rootPath + path);
            if(imgFile.exists())
                imgFile.delete();
        }
        //    删除所有该分类下的图书
        removeForCategory(categoryId);
    }

    @Override
    public PageInfo<Book> getAll(int pageNum, int limit) {
        PageHelper.startPage(pageNum,limit);
        return PageInfo.of(bookDao.selectAll());
    }
}
