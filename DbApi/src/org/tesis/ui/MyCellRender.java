package org.tesis.ui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import org.tesis.db.Column;
import org.tesis.db.Table;

public class MyCellRender extends DefaultTableCellRenderer{
    Table dbTable;
    public MyCellRender(Table dbTable) {
        this.dbTable=dbTable;
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        JLabel cell=(JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String colName=table.getModel().getColumnName(column);
        Column col=dbTable.getColumnByName(colName);
        //System.out.println(isSelected+" "+hasFocus+" "+row+" "+column);
        if(!col.getType().validateValue((value==null?null:value.toString()), col.isAllowNull()) && row < (table.getModel().getRowCount()-1)){
            cell.setBackground(Color.orange);
            //System.out.println("Pintando celda "+row+","+column);
        }else{
            cell.setBackground(Color.white);
        }
        if(isSelected){
            cell.setForeground(Color.blue);
        }else{
            cell.setForeground(Color.black);
        }
        return cell;
    }
}
