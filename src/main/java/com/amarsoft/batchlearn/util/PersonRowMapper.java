package com.amarsoft.batchlearn.util;

import com.amarsoft.batchlearn.model.Person;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自定义的RowMapper
 */
public class PersonRowMapper implements RowMapper<Person>{

    @Nullable
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        System.out.println("============:"+i);
        Person person = new Person();
        person.setName(resultSet.getString("name"));
        person.setAge(resultSet.getInt("age"));
        person.setNation(resultSet.getString("nation"));
        person.setAddress(resultSet.getString("address"));
        return person;
    }
}
