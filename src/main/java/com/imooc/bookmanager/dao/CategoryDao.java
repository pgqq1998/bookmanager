package com.imooc.bookmanager.dao;

import com.imooc.bookmanager.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CategoryDao {
    @Insert("insert into category(name,create_time,update_time) " +
            "values(#{name},#{createTime},#{updateTime})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void insert(Category category);

    @Delete("delete from category where id=#{id}")
    void delete(int id);

    @Select("select * from category")
    @Results(id = "all",value = {
            @Result(column = "id",property = "id",id = true),
            @Result(column = "name",property = "name"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "update_time",property = "updateTime")
    })
    List<Category> selectAll();

    @Update("update category set name=#{name},update_time=#{updateTime} " +
            "where id=#{id}")
    void update(Category category);

    @Select("select * from category where id=#{id} ")
    @ResultMap("all")
    Category select(int id);
}
