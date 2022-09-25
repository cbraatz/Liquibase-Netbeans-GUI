package org.tesis.db;

import org.tesis.exception.InvalidParameterException;

public class DataColumn {
    private String columnName;
    private String value;
    private boolean quoted;//PUEDE SER String columnType tambien en logar de esto
    public DataColumn(String columnName, String value, Boolean insertWithQuotes) throws InvalidParameterException{
        if(null != insertWithQuotes){
            if(null != columnName){
                if(null != value){
                    this.columnName = columnName;
                    this.value = value;
                    this.quoted=insertWithQuotes;
                }else{
                    throw new InvalidParameterException("El parámetro value no debe ser nulo al crear una instancia de DataColumn.");
                }
            }else{
                throw new InvalidParameterException("El parámetro columnName no debe ser nulo al crear una instancia de DataColumn.");
            }
        }else{
            throw new InvalidParameterException("El insertWithQuotes Column no debe ser nulo al crear una instancia de ForeignKey.");
        }
    }
    public DataColumn(String columnName, String value, String insertWithQuotes) throws InvalidParameterException{
        if(null != insertWithQuotes){
            if(null != columnName){
                if(null != value){
                    this.columnName = columnName;
                    this.value = value;
                    this.quoted=Boolean.parseBoolean(insertWithQuotes);
                }else{
                    throw new InvalidParameterException("El parámetro value no debe ser nulo al crear una instancia de DataColumn.");
                }
            }else{
                throw new InvalidParameterException("El parámetro columnName no debe ser nulo al crear una instancia de DataColumn.");
            }
        }else{
            throw new InvalidParameterException("El insertWithQuotes Column no debe ser nulo al crear una instancia de ForeignKey.");
        }
    }
    @Override
    public String toString(){
        return this.columnName;
    }

    public boolean isQuoted() {
        return quoted;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
