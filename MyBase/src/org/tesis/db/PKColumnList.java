package org.tesis.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Es una lista de nombres de columnas que son pk
 */
public class PKColumnList {
    private List<String> columns;

    public PKColumnList() {
        columns=new ArrayList();
    }
    public void addPkColumn(String columnName){
        columns.add(columnName);
    }
    public String getPkColumnNames(){
        //Collections.sort(columns);//ordena la lista de columnas
        StringBuilder res=new StringBuilder();
        for(String c:columns){
            if(!res.toString().isEmpty()){
                res.append(",");
            }
            res.append(c);
        }
        return res.toString();
    }
    public void renameColumn(String name, String newName){
        for(int i=0;i<columns.size();i++){
            if(columns.get(i).equals(name)){
                columns.set(i, newName);
                break;
            }
        }
    }
    public List<String> getColumns() {
        return columns;
    }
    public boolean isEmpty(){
        return this.columns.isEmpty();
    }
}
