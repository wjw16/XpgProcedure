package com.it.code.model;

import java.util.Properties;

/**
 * @author wenjianwu
 * @date 2019/3/23 0023 上午 8:48
 */

public class JdbcConfig {
    private String driver;
    private String url;
    private String address;
    private int  port;
    private String dbname;
    private String username;
    private String password;
    private String schemaname;
    private String preUrl;
    private String schema;
    private Properties properties;
    public  static  String PG_PREURL="jdbc:postgresql";
    public  static  String PG_DRIVER="org.postgresql.Driver";
    public  static  String JDBC_FILEPATH;
    static {
        JDBC_FILEPATH=System.getProperty("user.dir") + "\\config\\jdbc.properties";
    }
    public String getSchemaname() {
        return schemaname;
    }

    public void setSchemaname(String schemaname) {
        this.schemaname = schemaname;
    }

    public  JdbcConfig(){properties=new Properties();};
    public  JdbcConfig(String driver,String url,String username,String pasword,String schema){
        this();
        this.driver=driver;
        this.url=url;
        this.username=username;
        this.password=pasword;
        if(schema ==null ||schema.equals(""))
            this.schema="dbo";
        else
            this.schema=schema;
    }
    public  JdbcConfig(String driver,String address,int port,String dbname,String username,String pasword,String schema){
         this();
        this.driver=driver;
        this.address=address;
        this.port=port;
        this.dbname=dbname;
        this.username=username;
        this.password=pasword;
        if(schema ==null ||schema.equals(""))
            this.schema="dbo";
        else
            this.schema=schema;
        this.url=getPreUrl()+"://"+address+":"+port+"/"+dbname;
         properties.put("jdbc.driver",driver);
         properties.put("jdbc.url",url);
         properties.put("jdbc.username",username);
         properties.put("jdbc.password",pasword);
         properties.put("schemaname",schema);
    }
    public  JdbcConfig(String address,int port,String dbname,String username,String pasword,String schema){
        this(PG_DRIVER,address,port,dbname,username,pasword,schema);
    }

    public String getPreUrl() {
        if(preUrl==null)
            return  PG_PREURL;
        return preUrl;
    }

    public void setPreUrl(String preUrl) {
        this.preUrl = preUrl;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
