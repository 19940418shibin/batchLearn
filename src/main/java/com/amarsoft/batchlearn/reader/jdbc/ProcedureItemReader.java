package com.amarsoft.batchlearn.reader.jdbc;

import com.amarsoft.batchlearn.model.Person;
import org.springframework.batch.item.database.StoredProcedureItemReader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;

/**
 * 存储过程基于游标读取数据库
 */
public class ProcedureItemReader extends StoredProcedureItemReader<Person> {
    public ProcedureItemReader(DataSource dataSource) {
        this.setDataSource(dataSource);
        this.setProcedureName("query_person");
        this.setRowMapper(new BeanPropertyRowMapper(Person.class));
    }
}
