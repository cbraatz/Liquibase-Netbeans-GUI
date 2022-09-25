/**
 * Esta es una clase que representa a una tabla de base de datos.
 */
package org.tesis.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.tesis.exception.InvalidParameterException;
import org.tesis.exception.PrimaryKeyNotFoundException;

public final class Table {
    private String name;
    private String primaryKeyName;
    List<Column> columns=new ArrayList<>();
    List<DataRow> rows=new ArrayList<>();
    List<DataRow> removedRows=new ArrayList<>();
    private ForeignKeyList foreignKeys=new ForeignKeyList();
    
    public Table(String name, String primaryKeyName, List<Column>columns) throws PrimaryKeyNotFoundException {
        boolean hasPK=false;
        for(Column c:columns){
            if(c.isPk()){
                hasPK=true;
                break;
            }
        }
        if(!hasPK){
            throw new PrimaryKeyNotFoundException("No se encontró clave primaria en la tabla "+name);
        }
        this.columns.addAll(columns);
        this.name=name;
        this.primaryKeyName=primaryKeyName;
    }

    @Override
    public String toString() {
        return name;
    }
    
    public void removeForeignKeyByColumnName(String columnName){
        this.foreignKeys.removeForeignKeyByColumnName(columnName);
    }
    public void setForeignKeyByColumnName(String columnName, ForeignKey fk){
        this.foreignKeys.setForeignKeyByColumnName(columnName, fk);
    }
    public ForeignKeyList getForeignKeys() {
        return foreignKeys;
    }
    
    public void addForeignKey(ForeignKey fk) throws InvalidParameterException{
        fk.setTable(this);//se asegura de que esta sea la tabla de origen
        this.foreignKeys.addForeignKey(fk);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    public List<Column> getColumns() {
        return columns;
    }
    public Column getColumnByName(String columnName){
        for(Column col:this.columns){
            if(col.getName().equals(columnName)){
                return col;
            }
        }
        return null;
    }
    
    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public void setForeignKeys(ForeignKeyList foreignKeys) {
        this.foreignKeys = foreignKeys;
    }
    
    public PKColumnList getPKColumnList(){
        PKColumnList pks=new PKColumnList();
        for(Column c:this.columns){
            if(c.isPk()){
                pks.addPkColumn(c.getName());
            }
        }
        return pks;
    }
    public List<DataRow> getRows() {
        return rows;
    }
    
    public boolean addRows(List<DataRow> rows) {
        return this.rows.addAll(rows);
    }
    
    public boolean addRow(DataRow row) {
        return this.rows.add(row);
    }
    
    public boolean addRemovedRow(DataRow row) {
        row.setDate();
        return this.removedRows.add(row);
    }
    
    public void setRows(List<DataRow> rows) {
        this.rows = rows;
    }
    
    public List<DataRow> getRemovedRows() {
        return removedRows;
    }
    
    public boolean addRemovedRows(List<DataRow> rows) {
        return this.removedRows.addAll(rows);
    }
    
    public DataRow getDataRowByInternalId(String internalId){
        for(DataRow r:this.rows){
            if(r.getInternalId().equals(internalId)){
                return r;
            }
        }
        return null;
    }
    public DataRow getRemovedDataRowByInternalId(String internalId){
        for(DataRow r:this.removedRows){
            if(r.getInternalId().equals(internalId)){
                return r;
            }
        }
        return null;
    }
    public void removeRow(String internalId){
        Iterator<DataRow> i = this.getRows().iterator();
        while (i.hasNext()) {
            DataRow d=i.next();
            if(d.doesInternalIdMatches(internalId)){
                i.remove();
            }
        }
    }
    public void removeRemovedRow(String internalId){
        Iterator<DataRow> i = this.getRemovedRows().iterator();
        while (i.hasNext()) {
            DataRow d=i.next();
            if(d.doesInternalIdMatches(internalId)){
                i.remove();
            }
        }
    }
}
