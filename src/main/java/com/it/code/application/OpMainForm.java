package com.it.code.application;

import com.it.code.Service.PgTableInfoService;
import com.it.code.Service.SqlSessionManager;
import com.it.code.component.JAutoCompleteComboBox;
import com.it.code.model.JdbcConfig;
import com.it.code.model.OpConfig;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author wenjianwu
 * @date 2019/3/24 0024 上午 11:48
 */

public class OpMainForm {
    private JPanel mainForm;
    private JRadioButton rbUpdate;
    private JRadioButton rbDelete;
    private JTextField tbUrl;
    private JButton btnConn;
    private JTextField tbSchema;
    private JRadioButton rbSelect;
    private JRadioButton rbInsert;
    private JButton btnProduce;
    private JTextArea taSql;
    private JButton btnExport;
    private JScrollPane jsPane;
    private JTextField tbCondition;
    private JLabel lbCondition;
    private JButton btnConField;
    private JToolBar jtoolbar;
    private JLabel lbConfig;
    private JLabel lfFile;
    private JAutoCompleteComboBox cbTables;
    private String opType = "select";
    private OpConfig opConfig = new OpConfig();
    private  CreateProcedureAction createProcedureAction;
    public OpMainForm() {

         this.init();
    }

    /**
     * 控件添加事件
     */
    private void init(){
        JdbcConfig jdbcConfig = initDataSource();
        SqlSessionManager.jdbcConfig = jdbcConfig;
        tbUrl.setText(jdbcConfig.getUrl());
        tbSchema.setText(jdbcConfig.getSchemaname());
        opConfig.setOpType("select");
        btnConn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnConn_click(e);
            }
        });
        rbSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                opType = "select";
            }
        });
        rbInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                opType = "insert";
            }
        });
        rbUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                opType = "update";
            }
        });
        rbDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                opType = "delete";
            }
        });
        btnProduce.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnProduce_click(e);
            }
        });
        cbTables.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                opConfig.setTableName(e.getItem().toString());
                createProcedureAction=null;
            }
        });
        btnExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnExport_click(e);
            }
        });
        btnConField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnConfigFields_click(e);
            }
        });
        lbConfig.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lbConfig_click();
            }
        });
    }
    public  void resetUiDateSoure(){
        tbUrl.setText(SqlSessionManager.jdbcConfig.getUrl());
        btnConn_click(null);
    }

    public void btnConfigFields_click(ActionEvent e){
        if(opConfig==null||opConfig.getTableName()==null){
            JOptionPane.showMessageDialog(null, "请选择表", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(createProcedureAction==null)
            createProcedureAction=new CreateProcedureAction(opConfig);
        opConfig.setTableInfo(createProcedureAction.tableInfo);
        JFrame frame = new JFrame("配置字段");
        frame.setContentPane(new ConfigFieldForm(opConfig).mainForm);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * 连接数据库
     * @param e
     */
    public void btnConn_click(ActionEvent e) {
        System.out.println(tbSchema.getText());
        List<Map> mapList = PgTableInfoService.getAllTableNames(tbSchema.getText());
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (Map map : mapList) {
            model.addElement(map.get("tablename"));
        }
       cbTables.initModel(model);
       opConfig.setTableName(cbTables.getText());
    }

    /**
     * 生成存储过程
     * @param e
     */
    public void btnProduce_click(ActionEvent e) {
        if (cbTables.getItemCount() == 0) {
            JOptionPane.showMessageDialog(null, "没有表", "消息", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else if(cbTables.getText()==null||cbTables.getText().equals("")){
            JOptionPane.showMessageDialog(null, "请选择表", "消息", JOptionPane.ERROR_MESSAGE);
            return;
        }
        opConfig.setTableName(cbTables.getText());
        opConfig.setOpType(opType);
        opConfig.setConditions(tbCondition.getText());
        if(createProcedureAction==null)
             createProcedureAction = new CreateProcedureAction(opConfig);
        String proSql = createProcedureAction.create();
        taSql.setText(proSql);
    }

    /**
     * 主函数启动窗体
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("pg存储过程生成器");
        frame.setContentPane(new OpMainForm().mainForm);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);


    }

    /**
     * 获取jdbc信息
     * @return
     */
    public static JdbcConfig initDataSource() {
        Properties properties = new Properties();
        try {
            String filePath=System.getProperty("user.dir") + "\\config\\jdbc.properties";
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
                    //Resources.getResourceAsStream(filepath);
            properties.load(in);
            JdbcConfig jdbcConfig = new JdbcConfig();
            jdbcConfig.setDriver(String.valueOf(properties.get("jdbc.driver")));
            jdbcConfig.setUrl(String.valueOf(properties.get("jdbc.url")));
            jdbcConfig.setUsername(String.valueOf(properties.get("jdbc.username")));
            jdbcConfig.setPassword(String.valueOf(properties.get("jdbc.password")));
            jdbcConfig.setSchemaname(String.valueOf(properties.get("schemaname")));
            return jdbcConfig;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void btnExport_click(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.showDialog(new JLabel(), "确定");
        File fileDire = jfc.getSelectedFile();
        if (fileDire == null) return;
        if (fileDire.isDirectory()) {
            String path=fileDire.getPath();
            File file = new File(path+ "\\"+opConfig.getTableName()+".txt");
            try {
                FileWriter fw = new FileWriter(file);
                String str = taSql.getText();
                fw.write(str);
                fw.close();
                JOptionPane.showMessageDialog(null, "保存成功", "消息", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, "保存成功", "消息", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
    }
    private void lbConfig_click(){
        JPopupMenu popupMenu = new JPopupMenu();
//        JMenu menu = new JMenu("menu1");
//
//        JMenuItem item1 = new JMenuItem("item1");

        JMenuItem config = new JMenuItem("配置数据源");
//        menu.add(item1);
//
//        popupMenu.add(menu);
        config.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfigDataSource(e);
            }
        });
        popupMenu.add(config);
        popupMenu.show(lbConfig, 0, lbConfig.getY() + lbConfig.getHeight());
    }

    /**
     * 配置数据源
     * @param e
     */
    private void ConfigDataSource(ActionEvent e){
        ConfigDataSourceForm dataSourceForm=new ConfigDataSourceForm(this);
        dataSourceForm.pack();
        dataSourceForm.setVisible(true);
        dataSourceForm.setLocationRelativeTo(null);
        dataSourceForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
}
