/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.mybase;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.tag.ColumnTag;
import org.tesis.db.Column;
import org.tesis.db.Constants;
import org.tesis.exception.NotUniqueIDException;
import org.tesis.exception.NotUniqueNameException;
import org.tesis.jaxb.JXColumnTag;

/**
 *
 * @author Claus
 */
public class ColumnList {
    
    private List<ColumnTag> columnTags;
    public ColumnList(){
        columnTags= new ArrayList<>();
    }
    public ColumnList(List<ColumnTag> columnTags) throws Exception{
        for(ColumnTag c:columnTags){
            this.addColumn(c);
        }
    }
    /**
     * agrega una tag de columna a la lista de tag de columnas
     * @param columnTag 
     * @throws java.lang.Exception 
     */
    public void addColumn(ColumnTag columnTag)throws Exception{
        for(ColumnTag c:columnTags){
            if(c.getPropertyValueByKey(Constants.PROPERTY_ID).equals(columnTag.getPropertyValueByKey(Constants.PROPERTY_ID))){
                throw new NotUniqueIDException("ID no válido para la columna. ID = "+c.getPropertyValueByKey(Constants.PROPERTY_ID)+" ya está en uso por otra columna en la misma tabla.");
            }
            if(c.getPropertyValueByKey(Constants.PROPERTY_NAME).equals(columnTag.getPropertyValueByKey(Constants.PROPERTY_NAME))){
                throw new NotUniqueNameException("Nombre no válido para la columna con ID = "+columnTag.getPropertyValueByKey(Constants.PROPERTY_ID)+". Name = "+c.getPropertyValueByKey(Constants.PROPERTY_NAME)+" ya está en uso por otra columna en la misma tabla.");
            }
        }
        this.columnTags.add(columnTag);
    }
    /**
     * Retorna el sql referente a esta lista de columnas.
     * @return sentencia sql
     */
    /*@Override
    public String toString(){
        try {
            List<Column> cols=this.getColumnList();
            StringBuilder res = new StringBuilder();
            res.append("(");
            int i=0;
            List<String> pks=new ArrayList<>();
            for(Column c:cols){
                res.append((i>0?" , ":""));
                res.append(c.getName());
                res.append(" ");
                res.append(c.getType().getName());
                if(c.getType().getLenght() >= 0){
                    res.append("(");
                    res.append(c.getType().getLenght());
                    res.append(")");
                }
                if(c.isPk()){
                    res.append(" NOT NULL");
                    pks.add(c.getName());
                }else{//si no es clave primaria
                    res.append((c.isAllowNull()?" NULL":" NOT NULL"));
                }
                i++;
            }
            if(!pks.isEmpty()){//si tiene pks
                res.append(", PRIMARY KEY (");
                for(int j=0;j<pks.size();j++){
                    if(j > 0){
                        res.append(", ");
                    }
                    res.append(pks.get(j));
                }
                res.append(")");
            }
            res.append(")");
            return res.toString();
        } catch (Exception ex) {
            MyBaseLogger.LogWarningMessage("Error al obtener el String de ColumnList."+ex.getMessage(),"ColumnList");
        }
        return null;
    }*/
    public List<Column> getColumnList() throws Exception{
        List<Column> columns=new ArrayList<>();
        for(ColumnTag ct: columnTags){
            Column c=(Column) ct.exportDBObject();
            columns.add(c);
        }
        return columns;
    }
    public boolean isEmpty(){
        return this.columnTags.isEmpty();
    }
    public List<ColumnTag> getColumnTags() {
        return columnTags;
    }
    public List<JXColumnTag> exportJXColumnTags(){
        List<JXColumnTag> res=new ArrayList<>();
        for(ColumnTag c:this.columnTags){
            res.add((JXColumnTag)c.exportJXTag());
        }
        return res;
    }
    public List<String> getAllColumnIDs(){
        List<String> res=new ArrayList<>();
        for(ColumnTag c:this.columnTags){
            res.add(c.getPropertyValueByKey(Constants.PROPERTY_ID));
        }
        return res;
    }
}
