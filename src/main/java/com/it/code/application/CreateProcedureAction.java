package com.it.code.application;

import com.it.code.Service.PgTableInfoService;
import com.it.code.model.OpConfig;
import com.it.code.model.TableInfo;

import java.util.List;
import java.util.Map;

/**
 * @author wenjianwu
 * @date 2019/3/24 0024 下午 1:51
 */

public class CreateProcedureAction {
    public StringBuilder selectCommonFields;
    public StringBuilder selectCommonValues;
    public StringBuilder deleteCommonFields;
    public StringBuilder deleteCommonValues;
    public  StringBuilder updateCommonValues;
    public  StringBuilder updateCommonFields;
    private  List<Map> mapList;
    private OpConfig opConfig = null;
    public TableInfo tableInfo;

    public CreateProcedureAction(OpConfig opConfig) {
        this.opConfig = opConfig;
        this.tableInfo = getTableInfo();


    }

    private TableInfo getTableInfo() {
        List<Map> mapList = PgTableInfoService.getTableColNames(opConfig.getTableName());
        tableInfo = new TableInfo(mapList, opConfig);
        return tableInfo;
    }

    private StringBuilder getSelectCommonFields(OpConfig opConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append("sql_ varchar(4000);").append(opConfig.getLineSplit());
        sb.append("pageindex_ int4;").append(opConfig.getLineSplit());
        sb.append("pagesize_ int4;").append(opConfig.getLineSplit());
        sb.append(" rec record;").append(opConfig.getLineSplit());
        sb.append("orderby_ varchar;").append(opConfig.getLineSplit());
        sb.append("action_ varchar;").append(opConfig.getLineSplit());
        sb.append("orgid_ varchar;").append(opConfig.getLineSplit());
        sb.append("org_id_ varchar;").append(opConfig.getLineSplit());
       // sb.append(tableInfo.getField_type());

        String wh = "";
        String preStr = " ";
        if(opConfig.getConditions()==null||"".equals(opConfig.getConditions()))
            return  sb;
        String[] conArray = opConfig.getConditions().split(",");
        for (String fieldStr:conArray){
            String [] fieldArray=fieldStr.split("-");
            sb.append(fieldArray[fieldArray.length-1]+"_ "+fieldArray[0]+";").append(opConfig.getLineSplit());

        }
        return sb;
    }

    private StringBuilder getSelectCommonValues(OpConfig opConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * into rec FROM jsonb_array_elements(rbc.pub_tojson(datajson_));").append(opConfig.getLineSplit());
        sb.append("pageindex_:=rec.value ->>'page'; ").append(opConfig.getLineSplit());
        sb.append("pagesize_:=rec.value ->>'rows';").append(opConfig.getLineSplit());
        sb.append("orderby_ := rec.value->>'orderby';").append(opConfig.getLineSplit());
        sb.append("action_ := rec.value->>'action';").append(opConfig.getLineSplit());
        sb.append("orgid_:= rec.value->>'orgid';").append(opConfig.getLineSplit());
        sb.append("org_id_:= rec.value->>'orgcode';").append(opConfig.getLineSplit());
       // sb.append(tableInfo.getField_value());
        if(opConfig.getConditions()==null||"".equals(opConfig.getConditions()))
            return  sb;
        String[] conArray = opConfig.getConditions().split(",");
        for (String fieldStr:conArray){
            String [] fieldArray=fieldStr.split("-");
            sb.append(fieldArray[fieldArray.length-1]+"_:=rec.value->>'"
                    +fieldArray[fieldArray.length-1]+"';").append(opConfig.getLineSplit());

        }
        return sb;
    }

    private StringBuilder getDeleteCommonFields() {
        StringBuilder sb = new StringBuilder();
        sb.append("action_ varchar;").append(opConfig.getLineSplit());
        sb.append("orgid_ varchar").append(opConfig.getLineSplit());
        sb.append("org_id varchar").append(opConfig.getLineSplit());
        sb.append("rec record;").append(opConfig.getLineSplit());
        sb.append("msg_ varchar;").append(opConfig.getLineSplit());
        return sb;
    }

    private StringBuilder getDeleteCommonValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * into rec FROM jsonb_array_elements(rbc.pub_tojson(datajson_));").append(opConfig.getLineSplit());
        sb.append("action_ := rec.value->>'action';").append(opConfig.getLineSplit());
        sb.append("orgid_:= rec.value->>'orgid';").append(opConfig.getLineSplit());
        sb.append("org_id_:= rec.value->>'org_id';").append(opConfig.getLineSplit());
        return sb;
    }
    private StringBuilder getUpdateCommonFields() {
        StringBuilder sb = new StringBuilder();
        sb.append("action_ varchar;").append(opConfig.getLineSplit());
        sb.append("orgid_ varchar;").append(opConfig.getLineSplit());
        sb.append("org_id_ varchar;").append(opConfig.getLineSplit());
        sb.append("rec record;").append(opConfig.getLineSplit());
        sb.append("msg_ varchar;").append(opConfig.getLineSplit());
        sb.append(tableInfo.getField_type());
        return sb;
    }

    private StringBuilder getUpdateCommonValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * into rec FROM jsonb_array_elements(rbc.pub_tojson(datajson_));").append(opConfig.getLineSplit());
        sb.append("action_ := rec.value->>'action';").append(opConfig.getLineSplit());
        sb.append("orgid_:= rec.value->>'orgid';").append(opConfig.getLineSplit());
        sb.append("org_id_:= rec.value->>'orgcode';").append(opConfig.getLineSplit());
        sb.append(tableInfo.getField_value());
        return sb;
    }


    public String create() {
        String retunSql = "";
        switch (opConfig.getOpType()) {
            case "select": {
                retunSql = create_select();
                break;
            }
            case "insert": {
                retunSql = create_insert(opConfig);
                break;
            }
            case "update": {
                retunSql = create_update(opConfig);
                break;
            }
            case "delete": {
                retunSql = create_delete(opConfig);
                break;
            }
        }
        return retunSql;
    }

    public String create_select() {
        selectCommonFields = getSelectCommonFields(opConfig);
        selectCommonValues = getSelectCommonValues(opConfig);
         mapList = PgTableInfoService.getTableColNames(opConfig.getTableName());
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE OR REPLACE FUNCTION \"dbo\".\"" + opConfig.getTableName() + "_q\"(\"datajson_\" varchar)");
        sb.append("\r\n");
        sb.append("  RETURNS \"pg_catalog\".\"jsonb\" AS $BODY$\r\n\r\n");
        sb.append("DECLARE\r\n");
        sb.append("-- 常用字段\r\n");
        sb.append(selectCommonFields).append(opConfig.getLineSplit()).append("\r\n");
        sb.append("BEGIN").append("\r\n");
        sb.append(selectCommonValues).append(opConfig.getLineSplit());
        sb.append("sql_=").append("\'select ").append(tableInfo.getFields()).append(",");
        sb.append(" count(1) over() as zrow from ");
        sb.append(opConfig.getTableName());
        sb.append(" where 1=1\'").append(opConfig.getFieldSplit()).append(opConfig.getLineSplit());
        String conditons=produceConditions(opConfig);
        sb.append(conditons).append(opConfig.getLineSplit());
        sb.append("return rbc.pub_exec_sql(sql_,orderby_,pageindex_,pagesize_)").append(opConfig.getFieldSplit());
        sb.append("END\r\n$BODY$").append(opConfig.getLineSplit());
        sb.append("LANGUAGE plpgsql VOLATILE").append(opConfig.getLineSplit());
        sb.append("  COST 100");
        return sb.toString();
    }

    private String produceConditions(OpConfig opConfig) {
        String wh = "";
        String preStr = " ";
        String[] conArray = opConfig.getConditions().split(",");
        if (opConfig.getOpType().equals("select")) {
            preStr = "sql_=sql_||";
            for (String str : conArray) {
                String[] strArray = str.split("-");
                if (strArray.length == 3) {
                    wh += " if(" + strArray[2]+"_" + " is not null and " + strArray[2]+"_" + " !='') then\r\n";
                    wh += preStr + "' and " + strArray[2] + " like ''%'||" + strArray[2] + "_||'%''';\r\n";
                    wh += "end if;\r\n";
                } else if (strArray.length == 2 && (strArray[0].equals("varchar") || strArray[0].equals("char"))) {
                    wh += " if(" + strArray[1] + "_ is not null and " + strArray[1] + "_ !='') then\r\n";
                    wh += preStr + "' and " + strArray[1] + " = '''||" + strArray[1] + "_||'''';\r\n";
                    wh += "end if;\r\n";
                } else if (strArray.length == 2) {
                    wh += " if(" + strArray[1] + "_ is not null ) then\r\n";
                    wh += preStr + "' and " + strArray[1] + " = '''||" + strArray[1] + "_||'''';\r\n";
                    wh += "end if;\r\n";
                }
            }
        }
        else{
           for(String str:conArray){
               String[] strArray = str.split("-");
               if(strArray.length==3)
                   wh+=" and "+strArray[2]+"="+strArray[2]+"_";
               else if(strArray.length==2)
                   wh+=" and "+strArray[1]+"="+strArray[1]+"_";
           }
          wh=wh.equals("")?"":wh+";";
        }
        return wh;
    }

    public String create_insert(OpConfig opConfig) {
        updateCommonFields = getUpdateCommonFields();
        updateCommonValues = getUpdateCommonValues();
        if(mapList==null)
             mapList = PgTableInfoService.getTableColNames(opConfig.getTableName());
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE OR REPLACE FUNCTION \"dbo\".\"" + opConfig.getTableName() + "_u\"(\"datajson_\" varchar)");
        sb.append("\r\n");
        sb.append("  RETURNS \"pg_catalog\".\"jsonb\" AS $BODY$\r\n");
        sb.append("DECLARE\r\n\r\n");
        sb.append("-- 常用字段\r\n");
        sb.append(updateCommonFields).append(opConfig.getFieldSplit()).append(opConfig.getLineSplit());
        sb.append("BEGIN").append("\r\n");
        sb.append(updateCommonValues).append(opConfig.getFieldSplit()).append(opConfig.getLineSplit());
        sb.append("insert into ").append(opConfig.getTableName());
        sb.append("(" + tableInfo.getFields() + ")");
        sb.append("values(" + tableInfo.getFields_() + ")");
        sb.append(";").append(opConfig.getLineSplit());
        sb.append("msg_=\"更新成功\";").append("\r\n\r\n");
        sb.append("RETURN rbc.pub_builaryytjsb(0,msg_);").append("\r\n");
        sb.append("END\r\n$BODY$").append(opConfig.getLineSplit());
        sb.append("LANGUAGE plpgsql VOLATILE").append(opConfig.getLineSplit());
        sb.append("  COST 100");
        return sb.toString();
    }
    public  String create_update(OpConfig opConfig){
        updateCommonFields = getUpdateCommonFields();
        updateCommonValues = getUpdateCommonValues();
        if(mapList==null)
            mapList = PgTableInfoService.getTableColNames(opConfig.getTableName());
        StringBuilder sb = getProHead("_u");
        sb.append("-- 常用字段\r\n");
        sb.append(updateCommonFields).append(opConfig.getFieldSplit()).append(opConfig.getLineSplit());
        sb.append("BEGIN").append("\r\n");
        sb.append(updateCommonValues).append(opConfig.getFieldSplit()).append(opConfig.getLineSplit());
        sb.append("update ").append(opConfig.getTableName());
        String sql=" set ";
        for (int i=0;i<tableInfo.getFieldList().size();i++){
            String field=tableInfo.getFieldList().get(i);
            if(i!=tableInfo.getFieldList().size()-1){
               sql+=field+"=COALESCE("+field+"_,"+field+"),";
            }
            else
                sql+=field+"=COALESCE("+field+"_,"+field+")";
        }
        sb.append(sql);
        String conditions=produceConditions(opConfig);
        if(!conditions.equals(""))
            sb.append(" where 1=1").append(conditions).append(opConfig.getLineSplit());
        else
            sb.append(";").append(opConfig.getLineSplit());
        sb.append("msg_=\"更新成功\";").append("\r\n\r\n");
        sb.append("RETURN rbc.pub_builaryytjsb(0,msg_);").append("\r\n");
        sb.append("END\r\n$BODY$").append(opConfig.getLineSplit());
        sb.append("LANGUAGE plpgsql VOLATILE").append(opConfig.getLineSplit());
        sb.append("  COST 100");
        return sb.toString();
    }
    public  String create_delete(OpConfig opConfig){
        deleteCommonFields =getDeleteCommonFields();
        deleteCommonValues =getDeleteCommonValues();
        if(mapList==null)
            mapList = PgTableInfoService.getTableColNames(opConfig.getTableName());
        StringBuilder sb = getProHead("_u");
        sb.append("-- 常用字段\r\n");
        sb.append(deleteCommonFields).append(opConfig.getFieldSplit());
        sb.append("BEGIN").append("\r\n");
        sb.append(deleteCommonValues).append(opConfig.getFieldSplit());
        sb.append("delete from ").append(opConfig.getTableName());
        String conditions=produceConditions(opConfig);
        if(!conditions.equals(""))
            sb.append(" where 1=1").append(conditions).append(opConfig.getLineSplit());
        else
            sb.append(";").append(opConfig.getLineSplit());
        sb.append("msg_=\"更新成功\";").append("\r\n\r\n");
        sb.append("RETURN rbc.pub_builaryytjsb(0,msg_);").append("\r\n");
        sb.append("END\r\n$BODY$").append(opConfig.getLineSplit());
        sb.append("LANGUAGE plpgsql VOLATILE").append(opConfig.getLineSplit());
        sb.append("  COST 100");
        return sb.toString();
    }

    private  StringBuilder getProHead(String suffix ){
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE OR REPLACE FUNCTION \"dbo\".\"" + opConfig.getTableName() +suffix+"\"(\"datajson_\" varchar)");
        sb.append("\r\n");
        sb.append("  RETURNS \"pg_catalog\".\"jsonb\" AS $BODY$\r\n");
        sb.append("DECLARE\r\n\r\n");
        return  sb;
    }
}
