package com.it.code.component;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class JAutoCompleteComboBox extends JComboBox {

    private AutoCompleter completer;

    public JAutoCompleteComboBox() {
        super();
        addCompleter();
    }

    public JAutoCompleteComboBox(ComboBoxModel cm) {
        super(cm);
        addCompleter();
    }

    public JAutoCompleteComboBox(Object[] items) {
        super(items);
        addCompleter();
    }

    public JAutoCompleteComboBox(List v) {
        super((Vector) v);
        addCompleter();
    }

    private void addCompleter() {
        setEditable(true);
        completer = new AutoCompleter(this);
    }
    public void initModel(DefaultComboBoxModel model){
        super.setModel(model);
        completer.setModel(model);
    }
    public void autoComplete(String str) {
        this.completer.autoComplete(str, str.length());
    }

    public String getText() {
        return ((JTextField) getEditor().getEditorComponent()).getText();
    }

    public void setText(String text) {
        ((JTextField) getEditor().getEditorComponent()).setText(text);
    }

    public boolean containsItem(String itemString) {
        for (int i = 0; i < this.getModel().getSize(); i++) {
            String _item = " " + this.getModel().getElementAt(i);
            if (_item.equals(itemString))
                return true;
        }
        return false;
    }

   /* public static void main(String[] args) {
        JFrame frame = new JFrame();
        Object[] items = new Object[] { "", "abc ", "aab ", "aba ", "hpp ",
                "pp ", "hlp " };
        DefaultComboBoxModel model = new DefaultComboBoxModel(items);
        JComboBox cmb = new JAutoCompleteComboBox(model);
*//*        model.addElement("abc");
        model.addElement("aab");
        model.addElement("aba");
        model.addElement("hpp");
        model.addElement("pp");
        model.addElement("hlp");*//*
        frame.getContentPane().add(cmb);
        frame.setSize(400, 60);
        frame.setVisible(true);
    }*/
}
