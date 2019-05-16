package com.it.code.dao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * @author wenjianwu
 * @date 2019/3/23 0023 上午 10:49
 */

public interface PgTableInfoDao {
public List<Map> getAllTableNames(@Param("schemaname")String schemaname);
public List<Map> getTableColNames(@Param("tableName")String tableName);
public List<Map>getTableKey(@Param("tableName")String tableName);
}
