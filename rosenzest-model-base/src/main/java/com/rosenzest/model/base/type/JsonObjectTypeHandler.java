package com.rosenzest.model.base.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

@MappedTypes(JSONObject.class)
@MappedJdbcTypes(JdbcType.LONGVARCHAR)
public class JsonObjectTypeHandler extends BaseTypeHandler<JSONObject> {

	/**
	 * 设置非空参数
	 *
	 * @param ps
	 * @param i
	 * @param parameter
	 * @param jdbcType
	 * @throws SQLException
	 */

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, String.valueOf(JSON.toJSONString(parameter, SerializerFeature.WriteMapNullValue)));
	}

	/**
	 * 根据列名，获取可以为空的结果
	 *
	 * @param rs
	 * @param columnName
	 * @return
	 * @throws SQLException
	 */

	@Override
	public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String sqlJson = rs.getString(columnName);
		if (null != sqlJson) {
			return JSONObject.parseObject(sqlJson);
		}
		return null;
	}

	/**
	 * 根据列索引，获取可以为空的结果
	 *
	 * @param rs
	 * @param columnIndex
	 * @return
	 * @throws SQLException
	 */

	@Override
	public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String sqlJson = rs.getString(columnIndex);
		if (null != sqlJson) {
			return JSONObject.parseObject(sqlJson);
		}
		return null;
	}

	@Override
	public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String sqlJson = cs.getString(columnIndex);
		if (null != sqlJson) {
			return JSONObject.parseObject(sqlJson);
		}
		return null;
	}
}