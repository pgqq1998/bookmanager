package com.imooc.bookmanager.biz.impl;

import com.imooc.bookmanager.biz.BookBiz;
import com.imooc.bookmanager.biz.CategoryBiz;
import com.imooc.bookmanager.dao.BookDao;
import com.imooc.bookmanager.dao.CategoryDao;
import com.imooc.bookmanager.entity.Category;
import com.imooc.bookmanager.global.DaoFactory;

import java.util.List;

public class CategoryBizImpl implements CategoryBiz {
    private static CategoryBizImpl categoryBiz;

    private CategoryDao categoryDao;
    private CategoryBizImpl(){
        categoryDao = DaoFactory.getInstance().getDao(CategoryDao.class);
    }
    public static CategoryBizImpl getInstance(){
        if(categoryBiz == null)
            categoryBiz = new CategoryBizImpl();
        return categoryBiz;
    }
    @Override
    public void add(Category category) {
        categoryDao.insert(category);
    }

    @Override
    public void remove(int id) {
        categoryDao.delete(id);
    }

    @Override
    public List<Category> getAll() {
        return categoryDao.selectAll();
    }

    @Override
    public void edit(Category category) {
        categoryDao.update(category);
    }

    @Override
    public Category get(int id) {
        return categoryDao.select(id);
    }

    @Override
    public void descadeRemove(int id,String rootPath) {
        BookBiz bookBiz = BookBizImpl.getInstance();
        bookBiz.removeForCategory(id,rootPath);
        remove(id);
    }


}
