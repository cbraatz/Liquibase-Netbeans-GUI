package org.tesis.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
    private final String[] columnNames;// = {"First Name","Last Name","Sport","# of Years","Vegetarian"};
    private List <String[]> data=new ArrayList<>();

    public MyTableModel(String[] columnNames, String[][] data) {
        this.columnNames = columnNames;
        this.data.addAll(Arrays.asList(data));
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public int getRowCount() {
        return data.size();
    }
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        //if (col < 2) {
        //    return false;
        //} else {
            return true;
       // }
    }

    /*public void setValueAt(String value, int row, int col) {
        data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }*/
    
    public void addEmptyRow() {
       data.add(new String[columnNames.length]);
       // ver que hacer aca       
//this.data.addAll(Arrays.asList(data));
       //fireTableDataChanged();
    }
}
