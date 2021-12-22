package com.mindware.workflow.persistence.typehandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonTypeHandler  <T extends Object> extends BaseTypeHandler<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonTypeHandler.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    private Class<T> clazz;

    static {
        mapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public JsonTypeHandler(Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("Type argument cannot be null");
        }
        this.clazz = clazz;
    }

    /**
     * object转json string
     * @param object
     * @return
     */
    private String toJSON(T object) {
        try {
            String string = mapper.writeValueAsString(object);
            LOGGER.info(">>>> json handler string:{} <<<<",string);
            return string;
        } catch (Exception e) {
            LOGGER.error(">>>> covert object to json string failed, error message: <<<<",e.getMessage());
        }
        return null;
    }

    /**
     * json转object
     * @param json
     * @param clazz
     * @return
     */
    private T toObject(String json, Class<T> clazz) throws IOException {
        if (json != null && json != "") {
            return mapper.readValue(json,clazz);
        }
        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i,toJSON(parameter));
        } catch (Exception e) {
            LOGGER.error(">>>> preparedStatement set string failed, error message:{} <<<<",e.getMessage());
        }
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            return toObject(rs.getString(columnName), clazz);
        } catch (IOException e) {
            LOGGER.error(">>>> convert json string to object failed, error message:{} <<<<",e.getMessage());
        }
        return null;
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            return toObject(rs.getString(columnIndex), clazz);
        } catch (IOException e) {
            LOGGER.error(">>>> convert json string to object failed, error message:{} <<<<",e.getMessage());
        }
        return null;
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            return toObject(cs.getString(columnIndex), clazz);
        } catch (IOException e) {
            LOGGER.error(">>>> convert json string to object failed, error message:{} <<<<",e.getMessage());
        }
        return null;
    }
}
