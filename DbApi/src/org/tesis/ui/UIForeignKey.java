package org.tesis.ui;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.tesis.db.ForeignKey;

public class UIForeignKey extends JPanel{
    private String tableName;
    private String columnName;
    private String foreignColumnName;
    private String foreignTableName;
    private String FKName;
    private long internalID;
    Dimension dimension=new Dimension(253, 50);
    JLabel labelTop=new JLabel();
    JLabel labelBottom=new JLabel();
    public UIForeignKey(ForeignKey fKey) {
        this.FKName=fKey.getFKName();
        this.tableName=fKey.getTable().getName();
        this.columnName=fKey.getColumn().getName();
        this.foreignColumnName=fKey.getForeignColumn().getName();
        this.foreignTableName=fKey.getForeignTable().getName();
        this.internalID=fKey.getInternalId();
        this.setMaximumSize(dimension);
        this.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLabelsText();
        this.setOpaque(true);
        this.setBackground(UIConstants.LIST_ELEMENTS_ORIGINAL_COLOR);
        labelTop.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelTop.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBottom.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelBottom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        /*labelTop.setMaximumSize(new java.awt.Dimension(180, 48));
        labelTop.setMinimumSize(new java.awt.Dimension(180, 48));
        labelTop.setPreferredSize(new java.awt.Dimension(200, 48));*/

        javax.swing.GroupLayout yoLayout = new javax.swing.GroupLayout(this);
        this.setLayout(yoLayout);
        yoLayout.setHorizontalGroup(
            yoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelTop, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
            .addComponent(labelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );
        yoLayout.setVerticalGroup(
            yoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, yoLayout.createSequentialGroup()
                .addGap(10)
                .addComponent(labelTop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(labelBottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }
    private void setLabelsText(){
        labelTop.setText(this.columnName);
        labelBottom.setText(foreignTableName+" - "+foreignColumnName);
        labelTop.validate();
        labelBottom.validate();
    }
    public Dimension getDimension() {
        return dimension;
    }

    /*public void setForeignKeyValues(ForeignKey fk) {
        this.tableName=fk.getTable().getName();
        this.columnName=fk.getColumn().getName();
        this.foreignColumnName=fk.getForeignColumn().getName();
        this.foreignTableName=fk.getForeignTable().getName();
        this.internalID=fk.getInternalId();
        setLabelsText();
    }*/

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getForeignColumnName() {
        return foreignColumnName;
    }

    public String getForeignTableName() {
        return foreignTableName;
    }

    public long getInternalID() {
        return internalID;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
        //setLabelsText(); no hace falta xq el nombre de la tabla no figura en el UIForeignKey
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
        setLabelsText();
    }

    public void setForeignColumnName(String foreignColumnName) {
        this.foreignColumnName = foreignColumnName;
        setLabelsText();
    }

    public void setForeignTableName(String foreignTableName) {
        this.foreignTableName = foreignTableName;
        setLabelsText();
    }

    public String getFKName() {
        return FKName;
    }
    
    public boolean compareWithForeignKey(ForeignKey fk){
       if(this.getTableName().equals(fk.getTable().getName()) && this.getColumnName().equals(fk.getColumn().getName()) && this.getForeignTableName().equals(fk.getForeignTable().getName()) && this.getForeignColumnName().equals(fk.getForeignColumn().getName())&& this.getFKName().equals(fk.getFKName()) && this.getInternalID()==fk.getInternalId()){
           return true;
       }else{
           return false;
       }
    }
}
