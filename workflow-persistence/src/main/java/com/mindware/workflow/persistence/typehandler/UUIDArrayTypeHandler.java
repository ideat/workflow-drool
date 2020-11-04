package com.mindware.workflow.persistence.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.util.UUID;

@MappedJdbcTypes(JdbcType.ARRAY)
public class UUIDArrayTypeHandler extends BaseTypeHandler<UUID[]> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, UUID[] parameter, JdbcType jdbcType)
			throws SQLException {
		
		Array uuids = ps.getConnection().createArrayOf("uuid", parameter);
		ps.setArray(i, uuids);
		
	}

	@Override
	public UUID[] getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return (UUID[]) rs.getArray(columnName).getArray();
	}

	@Override
	public UUID[] getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return (UUID[]) rs.getArray(columnIndex).getArray();
	}

	@Override
	public UUID[] getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return (UUID[]) cs.getArray(columnIndex).getArray();
	}

}
