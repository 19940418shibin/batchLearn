package com.amarsoft.batchlearn.writer;

import com.amarsoft.batchlearn.model.Person;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import javax.sql.DataSource;

/**
 * 写入数据库
 */
public class JdbcItemWriter extends JdbcBatchItemWriter<Person> {
    public JdbcItemWriter(DataSource dataSource) {
        this.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        String sql = "insert into person " + "(name,age,nation,address) "
                + "values(:name, :age, :nation,:address)";
        //在此设置要执行批处理的sql语句
        this.setSql(sql);
        this.setDataSource(dataSource);
    }
}
