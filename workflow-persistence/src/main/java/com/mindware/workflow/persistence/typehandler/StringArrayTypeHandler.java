package com.mindware.workflow.persistence.typehandler;

import com.google.common.collect.Lists;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.List;

public class StringArrayTypeHandler  extends BaseTypeHandler<List<String>> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<String> strings, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return toList(resultSet.getArray(s));
    }

    @Override
    public List<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return toList(resultSet.getArray(i));
    }

    @Override
    public List<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return toList(callableStatement.getArray(i));
    }

    private List<String> toList(Array pgArray) throws SQLException {
        if (pgArray == null) return Lists.newArrayList();

        String[] strings = (String[]) pgArray.getArray();
        return containsOnlyNulls(strings) ? Lists.<String>newArrayList() : Lists.newArrayList(strings);
    }

    private boolean containsOnlyNulls(String[] strings) {
        for (String s : strings) {
            if (s != null) {
                return false;
            }
        }
        return true;
    }
}
