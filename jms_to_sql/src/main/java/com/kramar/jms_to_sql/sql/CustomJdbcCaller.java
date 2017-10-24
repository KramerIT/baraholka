package com.kramar.jms_to_sql.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomJdbcCaller {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String[]> getCustomResult(String sql) {
        return (List) jdbcTemplate.queryForObject(sql, new CustomResultRowMapper());
    }

    private class CustomResultRowMapper implements RowMapper {
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            int rows = rs.getMetaData().getColumnCount();
            List<String[]> allRows = new ArrayList<>();
            allRows.add(new String[]{rs.getMetaData().getTableName(1)});
            String[] fieldsName = new String[rows];
            for (int i = 1; i <= rows; i++) {
                fieldsName[i - 1] = (rs.getMetaData().getColumnName(i));
            }
            allRows.add(fieldsName);
            do {
                String[] currentRow = new String[rows];
                for (int i = 1; i <= rows; i++) {
                    String string = rs.getString(i);
                    currentRow[i - 1] = string != null ? string : "";
                }
                allRows.add(currentRow);
            } while (rs.next());
            return allRows;
        }
    }
}
