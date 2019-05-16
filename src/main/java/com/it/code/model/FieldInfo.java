package com.it.code.model;

import java.util.List;

/**
 * @CopyRight : 华润河南医药有限公司
 * @Version:1.0
 * @Author wenjianwu
 * @Datetime 2019-05-11 09:42
 * @Modor wenjianwu
 * @ModDesc
 */
public class FieldInfo {
    private  String filename;
    private  String typename;
    private  int typelen;
    private  String comment;
    private  boolean isnotnull;
    private List<String> indexList;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getTypelen() {
        return typelen;
    }

    public void setTypelen(int typelen) {
        this.typelen = typelen;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isIsnotnull() {
        return isnotnull;
    }

    public void setIsnotnull(boolean isnotnull) {
        this.isnotnull = isnotnull;
    }

    public List<String> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<String> indexList) {
        this.indexList = indexList;
    }
}
