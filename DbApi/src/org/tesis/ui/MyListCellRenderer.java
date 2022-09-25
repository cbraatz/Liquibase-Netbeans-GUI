package org.tesis.ui;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class MyListCellRenderer extends JPanel implements ListCellRenderer{
    public MyListCellRenderer() {
        
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        UIForeignKey f=(UIForeignKey) value;
        this.add(f);
        //this.setPreferredSize(f.getDimension()); 
        //this.setMaximumSize(f.getDimension());
        //f.getLayout().preferredLayoutSize(this);
      /*  System.out.println("dim1 "+f.getSize().toString());
        System.out.println("dim2 "+this.getSize().toString());
        System.out.println("X "+this.getX());
        System.out.println("Y "+this.getY());
        System.out.println("X2 "+f.getX());
        System.out.println("Y2 "+f.getY());
        System.out.println("bouds1 "+this.getBounds().toString());
        System.out.println("bouds2 "+this.getBounds().toString());
        System.out.println("CompCont1 "+this.getComponentCount());
        System.out.println("CompCont1 "+f.getComponentCount());
        System.out.println("Hei1 "+this.getHeight());
        System.out.println("Wid1 "+this.getWidth());
        System.out.println("Hei2 "+f.getHeight());
        System.out.println("Wid2 "+f.getWidth());*/
        return this;
    }    
}
