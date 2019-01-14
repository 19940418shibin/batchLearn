package com.amarsoft.batchlearn.reader.jdbc;

import com.amarsoft.batchlearn.model.Person;
import com.amarsoft.batchlearn.util.PersonRowMapper;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * jdbc基于分页读取数据库
 */
public class JdbcPageItemReader extends JdbcPagingItemReader<Person> {
    public JdbcPageItemReader(DataSource dataSource) {
        this.setDataSource(dataSource);
        this.setQueryProvider(new MySqlPagingQueryProvider(){{
            setSelectClause("select ID,NAME,AGE,NATION,ADDRESS");
            setFromClause("from person");
            setWhereClause("where ID between :begin and :end");
            Map<String,Order> map = new HashMap<String, Order>();
            map.put("ID",Order.DESCENDING);
            setSortKeys(map);
        }});
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("begin","11");
        map.put("end","15");
        this.setParameterValues(map);
        this.setPageSize(3);
        this.setRowMapper(new PersonRowMapper());
    }
}
