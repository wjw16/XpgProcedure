package com.it.code.application;

import com.it.code.Service.PgTableInfoService;
import com.it.code.Service.SqlSessionManager;
import com.it.code.Service.TopDataService;
import com.it.code.model.JdbcConfig;
import com.it.code.model.TopData;
import java.util.Map;
import java.util.List;

/**
 * @author wenjianwu
 * @date 2019/3/23 0023 上午 8:39
 */

public class Portal {


   /* public  static  void main(String[] args){
    testPgSql();
    }*/

    public  static void  testMysql(){
        JdbcConfig jdbcConfig=new JdbcConfig("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/FormDB?useUnicode=true&amp;characterEncoding=UTF-8","root","root","");
        SqlSessionManager.jdbcConfig=jdbcConfig;
        TopData topData=TopDataService.getTopdataByName("500miles");
        if(topData!=null)
            System.out.println(topData.getMusicAuthor()+"------------------");
    }
    public  static  void testPgSql(){

        JdbcConfig jdbcConfig=new JdbcConfig("org.postgresql.Driver","jdbc:postgresql://192.168.0.199:8433/rcgtest2018","rcg_test","A./$mz658","");
        SqlSessionManager.jdbcConfig=jdbcConfig;
        List<Map> mapList=PgTableInfoService.getAllTableNames("dbo");
        System.out.println(mapList.size());
       
    }
}
