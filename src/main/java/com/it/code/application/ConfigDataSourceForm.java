package com.it.code.application;

import com.it.code.Service.SqlSessionManager;
import com.it.code.model.JdbcConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ConfigDataSourceForm extends  JFrame{
    public JPanel JPcfDataSource;
    private JPanel contentPane;
    private JLabel jb_url;
    private JTextField tb_address;
    private JLabel jb_port;
    private JTextField tb_port;
    private JLabel jb_dbname;
    private JTextField tb_dbname;
    private JLabel jb_username;
    private JTextField tb_username;
    private JLabel jb_password;
    private JTextField tb_password;
    private JButton btnOk;
    private JButton btnConnect;
    private JLabel jb_schema;
    private JTextField tb_schema;
    private  OpMainForm opMainForm;
    public  ConfigDataSourceForm (){
       initUI();
    }
    public  ConfigDataSourceForm(OpMainForm opMainForm){
        this();
        this.opMainForm=opMainForm;
    }

    /**
     * 初始化窗体控件，布局
     */
    private void initUI(){
        setResizable(false);

        this.setLayout(null);
        setTitle("设置窗体大小");// 设置窗体标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 默认关闭方式
        this.setPreferredSize(new Dimension(400, 350));// 设置窗体大小
        this.setLocationRelativeTo(null);
        contentPane = new JPanel();// 创建内容面板
        contentPane.setLayout(null);
        setContentPane(contentPane);// 设置内容面板
        jb_url=new JLabel("地址：");
        tb_address=new JTextField();
        tb_address.setSize(300,20);
        jb_url.setLocation(20,20);
        jb_url.setSize(40,20);
        tb_address.setLocation(75,20);
        contentPane.add(jb_url);// 添加标签控件到窗体
        contentPane.add(tb_address);
        //端口号
        jb_port =new JLabel("端口号:");
        tb_port=new JTextField();
        jb_port.setSize(50,20);
        jb_port.setLocation(20,50);
        tb_port.setSize(100,20);
        tb_port.setLocation(75,50);
        contentPane.add(jb_port);
        contentPane.add(tb_port);

        //数据库名称
        jb_dbname =new JLabel("数据库:");
        tb_dbname=new JTextField();
        jb_dbname.setSize(50,20);
        jb_dbname.setLocation(20,80);
        tb_dbname.setSize(100,20);
        tb_dbname.setLocation(75,80);
        contentPane.add(jb_dbname);
        contentPane.add(tb_dbname);
        //模式
        jb_schema =new JLabel("模式:");
        tb_schema=new JTextField();
        jb_schema.setSize(50,20);
        jb_schema.setLocation(20,110);
        tb_schema.setSize(100,20);
        tb_schema.setLocation(75,110);
        contentPane.add(jb_schema);
        contentPane.add(tb_schema);
        //用户名
        jb_username =new JLabel("用户名:");
        tb_username=new JTextField();
        jb_username.setSize(50,20);
        jb_username.setLocation(20,140);
        tb_username.setSize(150,20);
        tb_username.setLocation(75,140);
        contentPane.add(jb_username);
        contentPane.add(tb_username);
        //密码
        jb_password =new JLabel("用户名:");
        tb_password=new JTextField();
        jb_password.setSize(50,20);
        jb_password.setLocation(20,170);
        tb_password.setSize(150,20);
        tb_password.setLocation(75,170);
        contentPane.add(jb_password);
        contentPane.add(tb_password);



        //测试连接
        btnConnect =new JButton("测试连接");
        btnConnect.setSize(90,30);
        btnConnect.setLocation(75,230);
        contentPane.add(btnConnect);
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               btnConnect_click(e);
            }
        });

        //确定按钮
        btnOk =new JButton("确定");
        btnOk.setSize(90,30);
        btnOk.setLocation(170,230);
        contentPane.add(btnOk);
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResetDataSource(e);
            }
        });
    }

    private  void ResetDataSource(ActionEvent e){
        if(!checkInput()){
            JOptionPane.showMessageDialog(null, "输入框不能为空", "消息", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JdbcConfig jc=new JdbcConfig(tb_address.getText(),Integer.parseInt(tb_port.getText()),
                tb_dbname.getText(),tb_username.getText(),tb_password.getText(),tb_schema.getText());
        SqlSessionManager.jdbcConfig=jc;
        OutputStream out=null;
        try{
        out =new FileOutputStream(JdbcConfig.JDBC_FILEPATH);
        jc.getProperties().store(out,"");
        out.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "未找到配四置文件异常", "消息", JOptionPane.INFORMATION_MESSAGE);
        }
        opMainForm.resetUiDateSoure();
        this.dispose();
    }
    private  Boolean checkInput(){
        if(tb_address.getText().trim().equals("")||tb_port.getText().trim().equals("")||
                tb_dbname.getText().trim().equals("")||tb_username.getText().trim().equals("")||
        tb_password.getText().trim().equals("")){
             return false;
        }
        return  true;
    }
    private void btnConnect_click(ActionEvent e){
        if(!checkInput()){
            JOptionPane.showMessageDialog(null, "输入框不能为空", "消息", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        SqlSessionManager.jdbcConfig=new JdbcConfig(tb_address.getText(),Integer.parseInt(tb_port.getText()),
                tb_dbname.getText(),tb_username.getText(),tb_password.getText(),tb_schema.getText());
        if(SqlSessionManager.testConnect()){
            JOptionPane.showMessageDialog(null, "连接成功", "消息", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, "连接失败", "消息", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
