package com.amarsoft.batchlearn.reader.jdbc;

import com.amarsoft.batchlearn.model.Person;
import com.amarsoft.batchlearn.util.PersonPreparedStatementSetter;
import com.amarsoft.batchlearn.util.PersonRowMapper;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * jdbc基于游标读取数据库
 */
public class JdbcItemReader extends JdbcCursorItemReader<Person> {
    public JdbcItemReader(DataSource dataSource) {
        this.setDataSource(dataSource);
        //不传参数
//        this.setSql("select id,name,age,nation,address from person where id<10");
        //动态sql
        this.setSql("select id,name,age,nation,address from person where id<?");
        //自定义一个PreparedStatementSetter
        this.setPreparedStatementSetter(new PersonPreparedStatementSetter());
        //BeanPropertyRowMapper 注意构造器初始化实例
//        reader.setRowMapper(new BeanPropertyRowMapper(Person.class));
        //可以自定义RowMapper
        this.setRowMapper(new PersonRowMapper());
    }
}
