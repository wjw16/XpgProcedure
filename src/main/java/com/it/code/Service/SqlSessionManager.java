package com.it.code.Service;

import com.it.code.dao.PgTableInfoDao;
import com.it.code.dao.SystemDao;
import com.it.code.model.JdbcConfig;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author wenjianwu
 * @date 2019/3/22 0022 下午 3:08
 */

public class SqlSessionManager {
    public static SqlSession sqlSession;
    public static SqlSessionFactory sqlSessionFactory = null;
    //数据库配置文件
    public final static String configuration = "mybatis_config.xml";
    public  static  JdbcConfig jdbcConfig=null;
    public  void destroyConnection() {

        sqlSession.close();
        sqlSession=null;

    }
    public static SqlSession createSqlSession(JdbcConfig jdbcConfig){
        Properties properties = new Properties();
        properties.setProperty("jdbc.driver",jdbcConfig.getDriver());
        properties.setProperty("jdbc.url", jdbcConfig.getUrl());
        properties.setProperty("jdbc.username",jdbcConfig.getUsername());
        properties.setProperty("jdbc.password",jdbcConfig.getPassword());
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(configuration);
        } catch (IOException e) {

            e.printStackTrace();
            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = builder.build(reader, properties);
        return  sqlSessionFactory.openSession();
    }
    public static SqlSession createSqlSession(){
        if(sqlSession!=null)return  sqlSession;
        if(jdbcConfig==null)return  null;
        return  createSqlSession(jdbcConfig);
    }
    public static Boolean testConnect(){
        SqlSession sqlSession=createSqlSession();
        Map map;
        try {
            map = sqlSession.getMapper(SystemDao.class).testConnect();
        }
        catch (Exception ex){
            map =null;
            ex.printStackTrace();
        }
        return map==null?false:true;
    }
}
