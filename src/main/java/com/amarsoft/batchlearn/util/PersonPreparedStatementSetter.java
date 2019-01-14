package com.amarsoft.batchlearn.util;

import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonPreparedStatementSetter implements PreparedStatementSetter{

    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1,"10");
    }
}
