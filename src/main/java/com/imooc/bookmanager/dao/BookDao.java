package com.imooc.bookmanager.dao;

import com.imooc.bookmanager.entity.Book;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookDao {
    @Insert("<script> " +
            "insert into book(category_id,name,level,price,img_path,create_time,update_time) values" +
            "<foreach collection='list' item='book' separator=','> " +
            "(#{book.categoryId},#{book.name},#{book.level},#{book.price},#{book.imgPath},#{book.createTime},#{book.updateTime})" +
            "</foreach> " +
            "</script>")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    void batchInsert(List<Book> list);

    @Delete("delete from book where id=#{id}")
    void delete(int id);

    @Select("select b.*,c.name cname from book b " +
            "left join category c " +
            "on b.category_id=c.id " +
            "where b.category_id=#{categoryId} " +
            "order by update_time desc ")
    @Results(id = "all",value = {
            @Result(column = "id",property = "id",id = true),
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "name",property = "name"),
            @Result(column = "level",property = "level"),
            @Result(column = "price",property = "price"),
            @Result(column = "img_path",property = "imgPath"),
            @Result(column = "create_time",property = "createTime"),
            @Result(column = "update_time",property = "updateTime")
    })
    List<Book> selectByCid(int categoryId);

    @Select("select b.*,c.name cname from book b " +
            "left join category c " +
            "on b.category_id=c.id " +
            "order by update_time desc ")
    @ResultMap("all")
    List<Book> selectAll();

    @Select("select b.*,c.name cname from book b " +
            "left join category c " +
            "on b.category_id=c.id " +
            "where b.id=#{id} " +
            "order by update_time desc ")
    @ResultMap("all")
    Book select(int id);

    @Update("update book set category_id=#{categoryId},name=#{name},level=#{level},price=#{price},img_path=#{imgPath},update_time=#{updateTime} " +
            "where id = #{id} ")
    void update(Book book);

    @Delete("delete from book where category_id=#{categoryId}")
    void deleteByCid(int categoryId);
}
