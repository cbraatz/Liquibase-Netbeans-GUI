package org.tesis.db;

import java.util.ArrayList;
import java.util.List;
import org.tesis.exception.InvalidParameterException;

public class ForeignKeyList {
    private List<ForeignKey> foreignKeys=new ArrayList<>();
    
    public ForeignKeyList() { }
    
    public ForeignKeyList(List<ForeignKey> fKeys) throws InvalidParameterException {
        this.setForeignKeys(foreignKeys);
    }

    public List<ForeignKey> getForeignKeys() {
        return foreignKeys;
    }

    public final void setForeignKeys(List<ForeignKey> foreignKeys) throws InvalidParameterException {
        for(ForeignKey fk:foreignKeys){
            this.addForeignKey(fk);
        }
    }
    
    public void removeForeignKeyByColumnName(String columnName){
        for(ForeignKey fk:this.foreignKeys){
            if(fk.getColumn().getName().equals(columnName)){
                this.foreignKeys.remove(fk);
                //System.out.println("Foreign key ="+fk.toString()+" ha sido borrada correctamente.");
                break;
            }
        }
    }
    
    public void setForeignKeyByColumnName(String columnName, ForeignKey fk){
        for(ForeignKey f:this.foreignKeys){
            if(f.getColumn().getName().equals(columnName)){
                f.setColumn(fk.getColumn());
                f.setForeignColumn(fk.getForeignColumn());
                f.setForeignTable(fk.getForeignTable());
                f.setTable(fk.getTable());
                f.setFKName(fk.getFKName());
                break;
            }
        }
    }
    public void addForeignKey(ForeignKey fk) throws InvalidParameterException{
        for(ForeignKey f:foreignKeys){
            if(f.getColumn().getName().equals(fk.getColumn().getName()) && f.getTable().getName().equals(fk.getTable().getName())){
                throw new InvalidParameterException("La tabla y columna de origen ya existen en la lista de ForeignKeys, favor especificar otros valores.");
            }
        }
        this.foreignKeys.add(fk);
    }
    public List<ForeignKey> getSubForeignKeyListByTableName(String tableName){
        List<ForeignKey> fKeys=new ArrayList<>();
        for(ForeignKey fk:foreignKeys){
            if(fk.getTable().getName().equals(tableName)){
                fKeys.add(fk);
            }
        }
        return fKeys;
    }
    public int getSize(){
        return this.foreignKeys.size();
    }
    public boolean isEmpty(){
        return this.foreignKeys.isEmpty();
    }
}
