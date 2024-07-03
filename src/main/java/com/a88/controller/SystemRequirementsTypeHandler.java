package com.a88.controller;

import com.a88.Pojo.SystemRequirements;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(SystemRequirements.class)
public class SystemRequirementsTypeHandler extends BaseTypeHandler<SystemRequirements> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SystemRequirements parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, parameter == null ? null : objectMapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public SystemRequirements getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return json == null ? null : objectMapper.readValue(json, SystemRequirements.class);
    }

    @SneakyThrows
    @Override
    public SystemRequirements getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return json == null ? null : objectMapper.readValue(json, SystemRequirements.class);
    }

    @SneakyThrows
    @Override
    public SystemRequirements getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return json == null ? null : objectMapper.readValue(json, SystemRequirements.class);
    }
}
