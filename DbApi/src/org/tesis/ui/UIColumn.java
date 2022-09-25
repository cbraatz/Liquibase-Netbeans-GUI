package org.tesis.ui;

import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import org.tesis.db.Column;

public class UIColumn extends javax.swing.JPanel{//son los paneles que se agregarn a la lista de columnas
    private javax.swing.JLabel labelLenght;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelType;
    private javax.swing.JButton buttonRemove;
    private javax.swing.JLabel labelPK;
    private javax.swing.JLabel labelAllowNull;
    private final long internalId;
    private boolean columnIsPK;
    private boolean columnIsNullable;
    private final String YES_LABEL="SI";
    private final String NO_LABEL="NO";
    private boolean deleted=false;
    public UIColumn(Column column){
        internalId=column.getInternalId();
        columnIsPK=column.isPk();
        columnIsNullable=column.isAllowNull();
        labelPK = new javax.swing.JLabel();
        labelAllowNull = new javax.swing.JLabel();
        buttonRemove = new javax.swing.JButton();
        labelName = new javax.swing.JLabel();
        labelType = new javax.swing.JLabel();
        labelLenght = new javax.swing.JLabel();
        this.setBackground(UIConstants.LIST_ELEMENTS_ORIGINAL_COLOR);
        buttonRemove.setText("-");
        buttonRemove.addActionListener(new java.awt.event.ActionListener() {@Override public void actionPerformed(java.awt.event.ActionEvent evt) {buttonRemoveActionPerformed(evt);} });
        labelName.setFont(new java.awt.Font("Tahoma", 0, 14));
        labelName.setText(column.getName());
        labelType.setFont(new java.awt.Font("Tahoma", 0, 14));
        labelType.setText((null==column.getType()?" ":column.getType().getPublicName()));
        labelLenght.setFont(new java.awt.Font("Tahoma", 0, 14));
        labelLenght.setText((column.getType().getLenght()<0?"":column.getType().getLenght().toString()));//si es menor a cero carga vacio.
        labelPK.setText((column.isPk()?YES_LABEL:NO_LABEL));
        labelAllowNull.setText((column.isAllowNull()?YES_LABEL:NO_LABEL));
        javax.swing.GroupLayout panelLinea1Layout = new javax.swing.GroupLayout(this);
        this.setLayout(panelLinea1Layout);
        
        //columnas
        SequentialGroup sg=panelLinea1Layout.createSequentialGroup();
            sg.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE);
            sg.addComponent(labelName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE);
            sg.addGap(10, 10, 10);
            sg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            sg.addComponent(labelType, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE);
            sg.addGap(30, 30, 30);
            sg.addComponent(labelLenght, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE);
            sg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED);
            sg.addGap(22, 22, 22);
            sg.addComponent(labelPK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE);
            sg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            sg.addComponent(labelAllowNull, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE);
            sg.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED);
            sg.addComponent(buttonRemove);
        ParallelGroup pg1=panelLinea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            pg1.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sg);
        panelLinea1Layout.setHorizontalGroup(pg1);
        
        //filas
        ParallelGroup pg3=panelLinea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE);
            pg3.addComponent(buttonRemove);
            pg3.addComponent(labelAllowNull);
            pg3.addComponent(labelPK);
            pg3.addComponent(labelName);
            pg3.addComponent(labelType);
            pg3.addComponent(labelLenght);
        ParallelGroup pg2=panelLinea1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
            pg2.addGroup(pg3);
            
        panelLinea1Layout.setVerticalGroup(pg2);
        
    }
    private void buttonRemoveActionPerformed(java.awt.event.ActionEvent evt){
        this.setVisible(false);
        this.setDeleted(true);//se setea como deleted xq al hacer setVisible hay un listener que captura en TableEditor y ahi es necesario saber si se borró la columna o si se ocultó nomas para limpiar la lista
    }
    /**
     * Retorna el valor del label si no está vacio, en ese caso retorna null
     * @return el valor del label si no está vacio, en ese caso retorna null
     */
    public String getColumnLenght() {
        return (this.labelLenght.getText().isEmpty()?null:this.labelLenght.getText());//retorna null si está vacio
    }

    public String getColumnName() {
        return this.labelName.getText();
    }

    public long getInternalId() {
        return internalId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getColumnType() {
        return labelType.getText(); 
    }
    
    public boolean isColumnPK() {
        return columnIsPK;
    }

    public boolean isColumnNullable() {
        return columnIsNullable;
    }

    public void setColLenght(String lenght) {
        this.labelLenght.setText(lenght);
    }

    public void setColName(String name) {
        this.labelName.setText(name);
    }

    public void setColType(String type) {
        this.labelType.setText(type);
    }

    public void setColPK(boolean columnIsPK) {
        this.columnIsPK = columnIsPK;
        labelPK.setText((columnIsPK?YES_LABEL:NO_LABEL));
        labelPK.validate();
    }

    public void setColNullable(boolean columnIsNullable) {
        this.columnIsNullable = columnIsNullable;
        labelAllowNull.setText((columnIsNullable?YES_LABEL:NO_LABEL));
        labelAllowNull.validate();
    }
}
