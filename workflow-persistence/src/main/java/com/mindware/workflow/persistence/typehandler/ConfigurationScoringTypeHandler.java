package com.mindware.workflow.persistence.typehandler;

import com.google.common.collect.Lists;
import com.mindware.workflow.core.entity.creditScoring.ConfigurationScoring;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.List;

public class ConfigurationScoringTypeHandler extends BaseTypeHandler<List<ConfigurationScoring>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<ConfigurationScoring> parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public List<ConfigurationScoring> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toList(rs.getArray(columnName));
    }

    private List<ConfigurationScoring> toList(Array pgArray) throws SQLException {
        if (pgArray == null) return Lists.newArrayList();

        ConfigurationScoring[] strings = (ConfigurationScoring[]) pgArray.getArray();
        return containsOnlyNulls(strings) ? Lists.<ConfigurationScoring>newArrayList() : Lists.newArrayList(strings);
    }

    private boolean containsOnlyNulls(ConfigurationScoring[] strings) {
        for (ConfigurationScoring s : strings) {
            if (s != null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ConfigurationScoring> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toList(rs.getArray(columnIndex));
    }

    @Override
    public List<ConfigurationScoring> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toList(cs.getArray(columnIndex));
    }
}
