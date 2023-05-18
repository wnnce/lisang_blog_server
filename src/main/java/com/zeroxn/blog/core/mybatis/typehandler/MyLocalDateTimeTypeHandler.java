package com.zeroxn.blog.core.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.postgresql.util.PGTimestamp;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * @author ran
 * @date
 * 自定义的Mybatis类型转换器 将Postgresql中的 timestamptz 类型转换为LocalDateTime
 */
@MappedTypes(LocalDateTime.class)
@MappedJdbcTypes(JdbcType.OTHER)
public class MyLocalDateTimeTypeHandler extends BaseTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object obj, JdbcType jdbcType) throws SQLException {
        Timestamp stamp = null;
        if (obj != null){
            if (obj instanceof LocalDateTime) {
                stamp = PGTimestamp.valueOf((LocalDateTime) obj);
            }
        }
        ps.setObject(i, stamp);
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return toLocalDateTime(resultSet.getObject(s));
    }

    @Override
    public Object getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return toLocalDateTime(resultSet.getObject(i));
    }

    @Override
    public Object getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return toLocalDateTime(callableStatement.getObject(i));
    }
    private Object toLocalDateTime(Object obj){
        if (obj != null){
            if (obj instanceof PGTimestamp){
                PGTimestamp stamp = (PGTimestamp) obj;
                return stamp.toLocalDateTime();
            }
            if (obj instanceof Timestamp){
                return ((Timestamp) obj).toLocalDateTime();
            }
        }
        return obj;
    }
}
