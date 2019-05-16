package com.it.code.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wenjianwu
 * @date 2019/3/24 0024 下午 2:11
 */

public class TableInfo {
    private  String fields;
    private  String fields_;
    private  String field_value;
    private  String field_type;
    private  List<String>field_List;
    private  List<String> field_typeList;
    private  List<String>field_valueList;
    private  List<String>fieldList;

    public List<String> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getFields_() {
        return fields_;
    }

    public void setFields_(String fields_) {
        this.fields_ = fields_;
    }

    public List<String> getField_List() {
        return field_List;
    }

    public void setField_List(List<String> field_List) {
        this.field_List = field_List;
    }

    public String getField_value() {
        return field_value;
    }

    public void setField_value(String field_value) {
        this.field_value = field_value;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public List<String> getField_typeList() {
        return field_typeList;
    }

    public void setField_typeList(List<String> field_typeList) {
        this.field_typeList = field_typeList;
    }

    public List<String> getField_valueList() {
        return field_valueList;
    }

    public void setField_valueList(List<String> field_valueList) {
        this.field_valueList = field_valueList;
    }

    public  TableInfo(){}
    public  TableInfo(List<Map>mapList,OpConfig opConfig){
        List<String> t_fieldList=new ArrayList<String>();
        List<String> t_field_List=new ArrayList<String>();
        List<String> t_field_typeList=new ArrayList<String>();
        List<String> t_field_valueList=new ArrayList<String>();
        String t_fields="";
        String  t_fields_="";
        for(Map tMap:mapList){
            String name=tMap.get("fieldname").toString();
            String type=tMap.get("typename").toString();
            String len=tMap.get("typelen").toString();
            String name_=name+"_";
            t_fieldList.add(name);
            t_field_List.add(name+"_");
            t_field_typeList.add(name+"_ "+type);
            t_field_valueList.add(name_+":=rec.value->>'"+name+"'");
        }
         this.setFieldList(t_fieldList);
         this.setFields(ListToString(t_fieldList,opConfig.getSelectSplit()));
         this.setFields_(ListToString(t_field_List,opConfig.getSelectSplit()));
         this.setField_List(t_field_List);
         this.setField_valueList(t_field_valueList);
         this.setField_type(ListToString(t_field_typeList,opConfig.getFieldSplit()));
         this.setField_value(ListToString(t_field_valueList,opConfig.getFieldValeSplit()));
         this.setField_typeList(t_field_typeList);

    }
    public  String ListToString(List<String> strList,String split){
        String retStr="";
        for(String str:strList){
            retStr=retStr.equals("")?str:retStr+split+str;
        }
        return  retStr;
    }
    public  TableInfo(List<Map>mapList,OpConfig opConfig,List<String> includeFields){
        List<String> t_fieldList=new ArrayList<String>();
        List<String> t_field_List=new ArrayList<String>();
        List<String> t_field_typeList=new ArrayList<String>();
        List<String> t_field_valueList=new ArrayList<String>();
        String t_fields="";
        String  t_fields_="";
        for(Map tMap:mapList){
            if(!includeFields.contains(tMap.get("name").toString()))
                continue;
            String name=tMap.get("name").toString();
            String type=tMap.get("typeName").toString();
            String len=tMap.get("typelen").toString();
            String name_=name+"_";
            t_fieldList.add(name);
            t_field_List.add(name+"_");
            t_field_typeList.add(name+"_ "+type);
            t_field_valueList.add(name_+":=rec.value->>'"+name+"'");
        }
        this.setFields(ListToString(t_fieldList,opConfig.getFieldSplit()));
        this.setFields_(ListToString(t_field_List,opConfig.getFieldSplit()));
        this.setField_List(t_field_List);
        this.setField_valueList(t_field_valueList);
        this.setField_type(ListToString(t_field_typeList,opConfig.getFieldValeSplit()));
        this.setField_value(ListToString(t_field_valueList,opConfig.getFieldValeSplit()));
        this.setField_typeList(t_field_typeList);

    }
}
