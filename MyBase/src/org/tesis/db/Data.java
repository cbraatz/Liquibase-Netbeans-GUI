package org.tesis.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Data {
    private List<DataRow> dataRows;
    public Data() {   
        this.dataRows=new ArrayList<>();
    }
    public Data(List<DataRow> dataRows) {
        this.dataRows = dataRows;
    }
    public void addDataRow(DataRow dataRow){
        this.dataRows.add(dataRow);
    }
    public void addAllDataRows(List<DataRow> dataRows) {
        this.dataRows.addAll(dataRows);
    }
    public DataRow getByInternalId(String internalID){
        if(null != internalID){
            for(DataRow dr:this.dataRows){
                if(dr.doesInternalIdMatches(internalID)){
                    return dr;
                }
            }
           // System.out.println("No se encontró la columna con InternalId ="+internalID);
            return null;
        }else{
            //System.err.println("internalID es nulo en el método getByInternalId.");
            return null;
        }    
    }
    public boolean isEmpty(){
        return this.dataRows.isEmpty();
    }
    public int getDataRowCount(){
        return this.dataRows.size();
    }

    public List<DataRow> getDataRows() {
        return dataRows;
    }

    public void setDataRows(List<DataRow> dataRows) {
        this.dataRows = dataRows;
    }
    /*public boolean replaceColumnValue(Long rowInternalId, String columnName, String columnValue){
        DataRow dr=this.getByInternalId(rowInternalId);
        if(null != dr){
            return dr.replaceValue(columnName,columnValue);
        }else{
            return false;
        }
    }*/
    public boolean replaceValueOrAddDataColumnByInternalId(String rowInternalId, DataColumn dataColumn){
        DataRow dr=this.getByInternalId(rowInternalId);
        if(null != dr){
            return dr.replaceValueOrAddDataColumn(dataColumn);
        }else{
            return false;
        }
    }
    public void removeRowFromListIfExists(String internalId){
        Iterator<DataRow> i = this.dataRows.iterator();//uso iterator para poder borrar directamente el elemento de la lista donde estoy iterando
        while (i.hasNext()) {
            DataRow d=i.next();
            if(d.doesInternalIdMatches(internalId)){
                i.remove();
            }
        }
    }
}
