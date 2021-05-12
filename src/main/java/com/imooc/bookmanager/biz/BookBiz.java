package com.imooc.bookmanager.biz;

import com.github.pagehelper.PageInfo;
import com.imooc.bookmanager.entity.Book;

import java.util.List;

public interface BookBiz {
    void batchAdd(List<Book> list);

    void remove(int id);

    void remove(int id,String rootPath);

    PageInfo<Book> getForCategory(int categoryId,int pageNum,int limit);

    Book get(int id);

    void edit(Book book);

    void edit(Book book,String rootPath);

    void removeForCategory(int categoryId);

    void removeForCategory(int categoryId,String rootPath);

    PageInfo<Book> getAll(int pageNum,int limit);
}
