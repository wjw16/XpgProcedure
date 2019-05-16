package com.it.code.application;

import com.it.code.model.OpConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wenjianwu
 * @date 2019/4/3 0003 下午 4:57
 */

public class ConfigFieldForm {
    public JPanel mainForm;
    private JLabel lbTableName;
    private JPanel JPanel;
    private JPanel jpanelFields;
    private JCheckBox opSelect;
    private JButton btnSave;
    private List<String>fieldList;
    private List<String>selFieldList;
    private OpConfig opConfig;
    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }
    public ConfigFieldForm(){
        opSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opSelect_Click(e);
            }
        });
    }
    public ConfigFieldForm(OpConfig opc){
        this();
        List<String> fdList=opc.getTableInfo().getFieldList();
        lbTableName.setText(opc.getTableName());
        this.jpanelFields.setLayout(new GridLayout(fdList.size(),0));
        this.fieldList=fdList;
         this.jpanelFields.setBackground(Color.white);
         this.jpanelFields.setVisible(true);
        if(fieldList!=null)
        {
            for (String field:fieldList){
                JCheckBox jc=new JCheckBox();
                jc.setText(field);
                jc.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cb_click(e);
                    }
                });
                jc.setSelected(true);
               jpanelFields.add(jc);
            }
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    private  void cb_click(ActionEvent e){

    }
    private void opSelect_Click(ActionEvent e){
          int count=jpanelFields.getComponentCount();
          selFieldList=new ArrayList<String>();
          for(int i=0;i<count;i++){
              JCheckBox jcb=(JCheckBox)jpanelFields.getComponent(i);
             if(opSelect.isSelected()){
                jcb.setSelected(true);
                selFieldList.add(jcb.getText());
             }
             else{
                 jcb.setSelected(false);
                 selFieldList.remove(jcb.getText());
             }
          }

    }

    public OpConfig getOpConfig() {
        return opConfig;
    }

    public void setOpConfig(OpConfig opConfig) {
        this.opConfig = opConfig;
    }
}
