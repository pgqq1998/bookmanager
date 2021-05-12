package com.imooc.bookmanager.biz;

import com.imooc.bookmanager.entity.Category;

import java.util.List;

public interface CategoryBiz {
    void add(Category category);

    void remove(int id);

    List<Category> getAll();

    void edit(Category category);

    Category get(int id);

    void descadeRemove(int id,String rootPath);
}
