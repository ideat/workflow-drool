package com.mindware.workflow.persistence.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.time.LocalTime;
import java.util.Calendar;

@MappedJdbcTypes(JdbcType.TIME)
public class LocalTimeTypeHandler extends BaseTypeHandler<LocalTime>{
	
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, localTimeToSqlTimestamp(parameter));
    }

    @Override
    public LocalTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return sqlTimestampToLocalTime(rs.getTimestamp(columnName));
    }

    @Override
    public LocalTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return sqlTimestampToLocalTime(rs.getTimestamp(columnIndex));
    }

    @Override
    public LocalTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return sqlTimestampToLocalTime(cs.getTimestamp(columnIndex));
    }
    
	private Timestamp localTimeToSqlTimestamp(LocalTime time){
		//Set time date @ epoch as Date does not matter
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(0);
		calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
		calendar.set(Calendar.MINUTE, time.getMinute());

		return new Timestamp(calendar.getTimeInMillis()); 
    }
    
	private LocalTime sqlTimestampToLocalTime(Timestamp sqlTimestamp){
	    	LocalTime result = null;
	    	if (sqlTimestamp != null) {
	    		Calendar calendar = Calendar.getInstance();
	    		calendar.setTime(sqlTimestamp);
	    		result = LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));	
	    	}
	    	return result;
    } 
    
}
