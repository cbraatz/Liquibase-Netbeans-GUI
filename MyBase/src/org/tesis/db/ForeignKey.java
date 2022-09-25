package org.tesis.db;

import org.tesis.exception.InvalidParameterException;
import org.tesis.util.Utils;

public class ForeignKey {
    private long internalId;
    private String FKName;
    private Table table;
    private Column column;
    private Table foreignTable;
    private Column foreignColumn;

    public ForeignKey(Long id, String fkName, Table table, Column column, Table foreignTable, Column foreignColumn) throws InvalidParameterException {
        if(null != column){
            if(null != table){
                if(null != foreignColumn){
                    if(null != foreignTable){
                        if(null != fkName){
                            if(column.getType().toString().equals(foreignColumn.getType().toString())){
                                this.foreignTable = foreignTable;
                                this.foreignColumn = foreignColumn;
                                this.table = table;
                                this.column = column;
                                if(null==id){
                                    this.internalId=Utils.generateUniqueID();
                                }else{
                                    this.internalId=id;
                                }
                                this.FKName=fkName;
                            }else{
                                throw new InvalidParameterException("Los parámetros column y foreignTable deben tener el mismo tipo de dato. Tipos de datos encontrados; Column = '"+column.getType().toString()+"' y ForeighColumn = '"+foreignColumn.getType().toString()+"'.");
                            }
                        }else{
                            throw new InvalidParameterException("El parámetros fkName no puede ser nulo al crear una instancia de ForeignKey.");
                        }
                    }else{
                        throw new InvalidParameterException("El parámetro foreignTable no debe ser nulo al crear una instancia de ForeignKey.");
                    }
                }else{
                    throw new InvalidParameterException("El parámetro foreignColumn no debe ser nulo al crear una instancia de ForeignKey.");
                }
            }else{
                throw new InvalidParameterException("El parámetro table no debe ser nulo al crear una instancia de ForeignKey.");
            }
        }else{
            throw new InvalidParameterException("El parámetro Column no debe ser nulo al crear una instancia de ForeignKey.");
        }
        
        
    }
    
    public Table getForeignTable() {
        return foreignTable;
    }

    public Column getForeignColumn() {
        return foreignColumn;
    }

    public Table getTable() {
        return table;
    }

    public Column getColumn() {
        return column;
    }

    public long getInternalId() {
        return internalId;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public void setForeignTable(Table foreignTable) {
        this.foreignTable = foreignTable;
    }

    public void setForeignColumn(Column foreignColumn) {
        this.foreignColumn = foreignColumn;
    }

    public String getFKName() {
        return FKName;
    }

    public void setFKName(String FKName) {
        this.FKName = FKName;
    }

    @Override
    public boolean equals(Object fk){
       ForeignKey ff=(ForeignKey)fk;
       if(this.getTable().getName().equals(ff.getTable().getName()) && this.getColumn().getName().equals(ff.getColumn().getName()) && this.getForeignTable().getName().equals(ff.getForeignTable().getName()) && this.getForeignColumn().getName().equals(ff.getForeignColumn().getName())&& this.getFKName().equals(ff.getFKName())){
           return true;
       }else{
           return false;
       }
    }
    @Override
    public String toString(){
        return "FK "+this.getFKName()+"[ tabla='"+this.table.getName()+"', columna='"+this.column.getName()+"', tabla_foranea='"+this.foreignTable.getName()+"', columna_foranea='"+this.foreignColumn.getName()+"' ]";        
    }
}
