package com.imooc.bookmanager.global;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;


public class DaoFactory {
    private static DaoFactory daoFactory;
    private SqlSessionFactory sqlSessionFactory;
    private DaoFactory(){
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        try {
            sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsReader("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DaoFactory getInstance(){
        if(daoFactory == null)
            daoFactory = new DaoFactory();
        return daoFactory;
    }

    public <T> T getDao(Class<T> tClass){
        return sqlSessionFactory.openSession(true).getMapper(tClass);
    }
}
