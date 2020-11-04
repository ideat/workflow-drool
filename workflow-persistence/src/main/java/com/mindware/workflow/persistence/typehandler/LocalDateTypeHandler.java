package com.mindware.workflow.persistence.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Calendar;

@MappedJdbcTypes(JdbcType.DATE)
public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate>{
	
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws SQLException {
        ps.setDate(i, localDateToSqlDate(parameter));
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return sqlDateToLocalDate(rs.getDate(columnName));
    }

    @Override
    public LocalDate getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return sqlDateToLocalDate(rs.getDate(columnIndex));
    }

    @Override
    public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return sqlDateToLocalDate(cs.getDate(columnIndex));
    }
    
	@SuppressWarnings("deprecation")
	private Date localDateToSqlDate(LocalDate date){
		//Date is assumed to be in UTC
		LocalDateTime ldt = LocalDateTime.of(date, LocalTime.of(0, 1)); // time doesn't matter, anything will do
    	return new Date(ldt.get(ChronoField.YEAR) - 1900, ldt.get(ChronoField.MONTH_OF_YEAR) - 1, ldt.get(ChronoField.DAY_OF_MONTH)); //Preserve y-m-d of date
    }
    
	private LocalDate sqlDateToLocalDate(Date sqlDate){    	
	    	LocalDate result = null;
	    	if (sqlDate != null) {
	    		Calendar calendar = Calendar.getInstance();
	    		calendar.setTime(sqlDate);
	    		result = LocalDate.of(calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));  		
	    	}
	    	return result;
    }
}
