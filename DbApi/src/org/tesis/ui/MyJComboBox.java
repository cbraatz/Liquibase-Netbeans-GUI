package org.tesis.ui;

import javax.swing.JComboBox;

public class MyJComboBox extends JComboBox{
    public void setSelectedItem(String text){
        for(int i=0;i<this.getModel().getSize();i++){
            Object obj=this.getModel().getElementAt(i);
            if(obj.toString().equals(text)){
                this.getModel().setSelectedItem(obj);
            }
        }
    }
    
}
