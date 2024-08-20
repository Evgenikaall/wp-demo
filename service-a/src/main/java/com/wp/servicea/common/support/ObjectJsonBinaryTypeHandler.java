package com.wp.servicea.common.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

// SHOULD BE IN WP-LIBS/LIB-WP-DATABASE
// USED JUST FOR EXAMPLE
// !!!!!!!
@MappedTypes(Object.class)
public class ObjectJsonBinaryTypeHandler extends BaseTypeHandler<Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter, Types.OTHER);
    }

    @SneakyThrows
    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return objectMapper.readValue(rs.getString(columnName), Object.class);
    }

    @SneakyThrows
    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return objectMapper.readValue(rs.getString(columnIndex), Object.class);
    }

    @SneakyThrows
    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return objectMapper.readValue(cs.getString(columnIndex), Object.class);
    }
}
