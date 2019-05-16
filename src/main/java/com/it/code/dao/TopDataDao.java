package com.it.code.dao;

import com.it.code.model.TopData;

import java.util.List;
import java.util.Map;

/**
 * @author wenjianwu
 * @date 2019/3/22 0022 下午 2:26
 */

public interface TopDataDao {


    public void createTopData(TopData topData);



    public void createTopDataByBatch(List<TopData> topDataList);



    public void updateTopData(TopData topData);



    //public void updateTopDataByBatch(List<com.it.code.model.TopData> topDataList);



    public List<TopData> getTopDataList(Map<String, Object> queryMap);

    public TopData getTopDataByName(String msuicName);
}
