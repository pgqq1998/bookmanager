package com.imooc.bookmanager.global;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

public class C3P0DataSource extends UnpooledDataSourceFactory {
    public C3P0DataSource(){
        this.dataSource = new ComboPooledDataSource();
    }
}
