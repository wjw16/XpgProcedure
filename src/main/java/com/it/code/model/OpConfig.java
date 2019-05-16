package com.it.code.model;

import java.util.List;

/**
 * @author wenjianwu
 * @date 2019/3/24 0024 下午 1:52
 */

public class OpConfig {
    private  String opType;
    private List<String> declareList;
    private  String tableName;
    private  String fieldSplit=";\r\n";
    private String  fieldValeSplit=";\r\n";
    private String  lineSplit="\r\n";
    private  String selectSplit=",";
    private String includeFields;
    private String whereFields;
    private String conditions;
    private TableInfo tableInfo;
    public TableInfo getTableInfo() {
        return tableInfo;
    }
    public void setTableInfo(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getLineSplit() {
        return lineSplit;
    }
    public String getLineSplit(int num) {
        for(int i=0;i<num;i++){
            if(i==0)
                lineSplit="\r\n";
            else
                lineSplit+="\r\n";
        }
        return lineSplit;
    }

    public void setLineSplit(String lineSplit) {
        this.lineSplit = lineSplit;
    }

    public String getSelectSplit() {
        return selectSplit;
    }

    public void setSelectSplit(String selectSplit) {
        this.selectSplit = selectSplit;
    }

    public String getIncludeFields() {
        return includeFields;
    }

    public void setIncludeFields(String includeFields) {
        this.includeFields = includeFields;
    }

    public String getWhereFields() {
        return whereFields;
    }

    public void setWhereFields(String whereFields) {
        this.whereFields = whereFields;
    }

    public String getFieldSplit() {
        return fieldSplit;
    }

    public void setFieldSplit(String fieldSplit) {
        this.fieldSplit = fieldSplit;
    }

    public String getFieldValeSplit() {
        return fieldValeSplit;
    }

    public void setFieldValeSplit(String fieldValeSplit) {
        this.fieldValeSplit = fieldValeSplit;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOpType() {
        return opType;
    }

    public void setOpType(String opType) {
        this.opType = opType;
    }

    public List<String> getDeclareList() {
        return declareList;
    }

    public void setDeclareList(List<String> declareList) {
        this.declareList = declareList;
    }
}
