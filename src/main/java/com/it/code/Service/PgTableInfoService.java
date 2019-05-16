package com.it.code.Service;

import com.it.code.dao.PgTableInfoDao;
import com.it.code.model.FieldInfo;
import com.it.code.utils.BeanUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wenjianwu
 * @date 2019/3/23 0023 上午 10:55
 */

public class PgTableInfoService extends  SqlSessionManager {
    public static   List<Map> getAllTableNames(String schemaname){
        SqlSession sqlSession=createSqlSession();
       List<Map> mapList=sqlSession.getMapper(PgTableInfoDao.class).getAllTableNames(schemaname);
       return  mapList;

    }
    public static   List<Map> getTableColNames(String tableName){
        SqlSession sqlSession=createSqlSession();
        List<Map> mapList=sqlSession.getMapper(PgTableInfoDao.class).getTableColNames(tableName);
        return  mapList;
    }
    public static  <T> List<T> getTableColNames(String tableName,Class<T> target){
        SqlSession sqlSession=createSqlSession();
        List<Map> mapList=sqlSession.getMapper(PgTableInfoDao.class).getTableColNames(tableName);
        Class c =target.getClass();
        List<T>fiList=new ArrayList<T>();
        for(Map map:mapList){
            fiList.add((T)BeanUtil.map2Bean(target,map));
        }
        return  fiList;
    }
    public  static  List<Map>getTableKey(String tableName){
        SqlSession sqlSession=createSqlSession();
        List<Map> mapList=sqlSession.getMapper(PgTableInfoDao.class).getTableKey(tableName);
        return  mapList;
    }
}
