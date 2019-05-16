package com.it.code.Service;

import com.it.code.Service.SqlSessionManager;
import com.it.code.dao.TopDataDao;
import com.it.code.model.TopData;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

/**
 * @author wenjianwu
 * @date 2019/3/22 0022 下午 2:27
 */

public class TopDataService extends SqlSessionManager {

    public static   TopData getTopdataByName(String name){
        SqlSession sqlSession=createSqlSession();
        return  sqlSession.getMapper(TopDataDao.class).getTopDataByName(name);
    }

}
