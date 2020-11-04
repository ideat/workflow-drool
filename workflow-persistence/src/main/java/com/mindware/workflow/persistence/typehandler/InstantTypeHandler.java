package com.mindware.workflow.persistence.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;

@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class InstantTypeHandler extends BaseTypeHandler<Instant> {
	private static final ZoneId UTC_ZONE_ID = ZoneId.of("Etc/GMT-0");

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Instant parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setTimestamp(i, instantToSqlTimestamp(parameter));
	}

	@Override
	public Instant getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return sqlTimestampToInstant(rs.getTimestamp(columnName));
	}

	@Override
	public Instant getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return sqlTimestampToInstant(rs.getTimestamp(columnIndex));
	}

	@Override
	public Instant getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return sqlTimestampToInstant(cs.getTimestamp(columnIndex));
	}

	@SuppressWarnings("deprecation")
	private Timestamp instantToSqlTimestamp(Instant instant) {
		// Instant is assumed to be in UTC
		LocalDateTime ldt = LocalDateTime.ofInstant(instant, UTC_ZONE_ID);

		return new Timestamp(ldt.get(ChronoField.YEAR) - 1900, ldt.get(ChronoField.MONTH_OF_YEAR) - 1,
				ldt.get(ChronoField.DAY_OF_MONTH), ldt.get(ChronoField.HOUR_OF_DAY),
				ldt.get(ChronoField.MINUTE_OF_HOUR), ldt.get(ChronoField.SECOND_OF_MINUTE),
				ldt.get(ChronoField.NANO_OF_SECOND)); // Preserve y-m-d hh:mm:ss:nnnn of date
	}

	private Instant sqlTimestampToInstant(Timestamp sqlTimestamp) {
		Instant result = null;
		if (sqlTimestamp != null) {
			Instant instant = Instant.ofEpochMilli(sqlTimestamp.getTime());
			result = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).atZone(UTC_ZONE_ID).toInstant();
		}
		return result;
	}
}
