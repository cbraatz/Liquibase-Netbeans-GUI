package org.tesis.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.tesis.exception.ColumnNotFoundException;
import org.tesis.exception.InvalidParameterException;
import org.tesis.util.Utils;

public class DataRow{
    private List<DataColumn> dataColumns=new ArrayList<>();//tiene que ser DataColumnList
    private String tableName;
    private String internalId;
    private Date date;//last updated or deleted date.
    public DataRow() {
        this.setDate();
    }
    
    public DataRow(String internalId, String tableName, List<DataColumn> dataColumns) throws InvalidParameterException {
        this.tableName=tableName;
        this.internalId=internalId;
        this.setDataColumns(dataColumns);
        this.setDate();
    }
    public DataRow(String tableName, List<DataColumn> dataColumns) throws InvalidParameterException {
        this.tableName=tableName;
        this.setDataColumns(dataColumns);
        this.internalId=Long.toString(Utils.generateUniqueID());
        this.setDate();
    }
    public DataRow(String internalId, String tableName){
        this.tableName=tableName;
        this.internalId=internalId;
        this.setDate();
    }

    public String getInternalId() {
        return internalId;
    }
    
    public void setInternalId(String internalId){
        this.internalId=internalId;
    }
    
    public List<DataColumn> getDataColumns() {
        return dataColumns;
    }

    public Date getDate() {
        return date;
    }

    public void setDate() {
        this.date=new Date();
    }

    public final void setDataColumns(List<DataColumn> dataColumns) throws InvalidParameterException {
        for(DataColumn dc:dataColumns){
            this.addDataColumn(dc);
        }
        this.setDate();
    }
    public void addDataColumn(DataColumn data) throws InvalidParameterException{
        for(DataColumn d:dataColumns){
            if(d.getColumnName().equals(data.getColumnName())){
                throw new InvalidParameterException("Columna ya existe en la lista de ColumnData, favor no agregar columnas repetidas.");
            }
        }
        this.dataColumns.add(data);
        this.setDate();
    }
   
    public int getSize(){
        return this.dataColumns.size();
    }
    
    public boolean isEmpty(){
        return this.dataColumns.isEmpty();
    }

    public String getTableName() {
        return tableName;
    }
    
    public boolean doesInternalIdMatches(String internalID){
        return (this.internalId.equals(internalID));
    }
    public void replaceValues(List<DataColumn> dcl) throws ColumnNotFoundException{
        boolean found,hasEmptyValue;
        for(DataColumn mdc:dcl){
            found=false;
            hasEmptyValue=false;
            for(DataColumn dc:dataColumns){
                if(mdc.getColumnName().equals(dc.getColumnName())){
                    if(!mdc.getValue().equals(dc.getValue())){
                        if(!mdc.getValue().isEmpty()){
                            dc.setValue(mdc.getValue());
                        }else{
                           hasEmptyValue=true;
                        }
                    }
                    found=true;
                    break;
                }
            }
            if(!found){
                //throw new ColumnNotFoundException("Columna "+mdc.getColumnName()+" no fue encontrada al intentar remplazar su valor por: '"+mdc.getValue()+"'");
                this.dataColumns.add(mdc);
            }else{
                if(hasEmptyValue){
                    System.err.println("Borrando DataColumn = "+mdc.getColumnName());
                   this.removeDataColumnIfExists(mdc.getColumnName());//podria haber hecho esto directamente 10 lineas mas arriba
                }
            }
        }
        this.setDate();
    }
    public void removeDataColumnIfExists(String columnName){
        Iterator<DataColumn> i = this.dataColumns.iterator();//uso iterator para poder borrar directamente el elemento de la lista donde estoy iterando
        while (i.hasNext()) {
            DataColumn d=i.next();
            if(d.getColumnName().equals(columnName)){
                i.remove();
            }
        }
    }
    public void replaceDataToNull(String columnName) throws ColumnNotFoundException{
        boolean found;
        found=false;
        for(DataColumn dc:dataColumns){
            if(columnName.equals(dc.getColumnName())){
                dc.setValue(null);
                found=true;
                break;
            }
        }
        //if(!found){
            //ver si setear agregar la columna en vez de tirar la excepción
            //throw new ColumnNotFoundException("Columna "+columnName+" no fue encontrada al intentar remplazar su valor por: null"); no tiene sentido setear null cuando no se encuentra, que quiere decier que ya es null :D
        //}
        this.setDate();
    }

    public boolean replaceValueOrAddDataColumn(DataColumn dataColumn){
        boolean exists=false;
        for(DataColumn dc:dataColumns){
            if(dataColumn.getColumnName().equals(dc.getColumnName())){
                dc.setValue(dataColumn.getValue());
                exists= true;
            }
        }
        if(!exists){
            this.dataColumns.add(dataColumn);
        }
        this.setDate();
        return true;
    }

    public DataColumn getDataColumnByName(String name) throws InvalidParameterException{
        for(DataColumn dc:this.dataColumns){
            if(dc.getColumnName().equals(name)){
                return dc;
            }
        }
        throw new InvalidParameterException("No se encontró DataColumn con nombre: "+name);
    }
}
