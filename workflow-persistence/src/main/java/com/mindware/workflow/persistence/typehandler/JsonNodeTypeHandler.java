package com.mindware.workflow.persistence.typehandler;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.ibatis.executor.result.ResultMapException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Map JSON string as value container with JsonNode.
 * Should always return not null value.
 * Use JSON string representation as intermediate data format.
 *
 * @see JsonNodeValue
 */
@MappedTypes({JsonNode.class})
public class JsonNodeTypeHandler extends BaseTypeHandler<JsonNode> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
        if (Objects.nonNull(parameter)) {
            String json;
            try {
                json = ReaderWriter.write(parameter);
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage(), ex);
            }
            ps.setString(i, json);
        } else {
            ps.setString(i, null);
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String jsonSource = rs.getString(columnName);
        return JsonNodeValue.fromDb(jsonSource).get();
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String jsonSource = rs.getString(columnIndex);
        return JsonNodeValue.fromDb(jsonSource).get();
    }

    @Override
    public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String jsonSource = cs.getString(columnIndex);
        return JsonNodeValue.fromDb(jsonSource).get();
    }

    /*
    Override BaseTypeHandler in such way that result will never be null
     */
    @Override
    public JsonNode getResult(ResultSet rs, String columnName) throws SQLException {
        try {
            return getNullableResult(rs, columnName);
        } catch (Exception e) {
            throw new ResultMapException("Error attempting to get column '" + columnName + "' from result set.  Cause: " + e, e);
        }
    }

    @Override
    public JsonNode getResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            return getNullableResult(rs, columnIndex);
        } catch (Exception e) {
            throw new ResultMapException("Error attempting to get column #" + columnIndex + " from result set.  Cause: " + e, e);
        }
    }

    @Override
    public JsonNode getResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            return getNullableResult(cs, columnIndex);
        } catch (Exception e) {
            throw new ResultMapException("Error attempting to get column #" + columnIndex + " from callable statement.  Cause: " + e, e);
        }
    }
}
