/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.mybase;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.tag.DataColumnTag;
import org.tesis.db.Constants;
import org.tesis.db.DataColumn;
import org.tesis.exception.NotUniqueNameException;
import org.tesis.jaxb.JXDataColumnTag;

public class DataColumnList {
    
    private List<DataColumnTag> dataColumnTags;
    public DataColumnList(){
        dataColumnTags= new ArrayList<>();
    }
    
    public DataColumnList(List<DataColumnTag> columnDataTags) throws Exception{
        for(DataColumnTag c:columnDataTags){
            this.addDataColumn(c);
        }
    }
    /**
     * agrega una tag de columna a la lista de tag de columnas
     * @param columnDataTag
     * @throws java.lang.Exception 
     */
    public void addDataColumn(DataColumnTag columnDataTag)throws Exception{
        for(DataColumnTag c:dataColumnTags){
            if(c.getPropertyValueByKey(Constants.PROPERTY_NAME).equals(columnDataTag.getPropertyValueByKey(Constants.PROPERTY_NAME))){
                throw new NotUniqueNameException("Nombre no válido para la columna con ID = "+columnDataTag.getPropertyValueByKey(Constants.PROPERTY_ID)+". Name = "+c.getPropertyValueByKey(Constants.PROPERTY_NAME)+" ya está en uso por otra columna en la misma tabla.");
            }
        }
        this.dataColumnTags.add(columnDataTag);
    }
    
    public List<DataColumn> getDataColumnList() throws Exception{
        List<DataColumn> columns=new ArrayList<>();
        for(DataColumnTag ct: dataColumnTags){
            DataColumn c=(DataColumn) ct.exportDBObject();
            columns.add(c);
        }
        return columns;
    }
    public boolean isEmpty(){
        return this.dataColumnTags.isEmpty();
    }
    public List<DataColumnTag> getDataColumnTags() {
        return dataColumnTags;
    }
    public List<JXDataColumnTag> exportJXDataColumnTags(){
        List<JXDataColumnTag> res=new ArrayList<>();
        for(DataColumnTag c:this.dataColumnTags){
            res.add((JXDataColumnTag)c.exportJXTag());
        }
        return res;
    }
    public List<String> getAllColumnIDs(){
        List<String> res=new ArrayList<>();
        for(DataColumnTag c:this.dataColumnTags){
            res.add(c.getPropertyValueByKey(Constants.PROPERTY_ID));
        }
        return res;
    }
}
