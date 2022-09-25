package org.tesis.changelog.type;

import org.tesis.db.DataFilterCriteria;
import org.tesis.exception.InvalidConversionException;

/**
 * Clase abstracta que representa a un tipo de dato
 * @author Claus
 */
public abstract class Type {
    private Integer lenght=Integer.parseInt("-1");
    private String dbName;
    private String publicName;
    private boolean insertRequestWithQuotes;
    public Type(String dbName, String publicName, boolean quotes){//length null = no tiene
        this.dbName=dbName;
        this.publicName=publicName;
        insertRequestWithQuotes=quotes;
    }
    public boolean hasLenght(){
        throw new UnsupportedOperationException("Este método aun no fue implementado.");
    }
    public String getDbName() {
        return dbName;
    }

    public String getDefaultValue() {
        throw new UnsupportedOperationException("Este método aun no fue implementado.");
    }
    
    public boolean validateValue(String value, boolean nullable){
        throw new UnsupportedOperationException("Este método aun no fue implementado. This.class = "+this.getClass().getName());
    }
    
    public String getPublicName() {
        return publicName;
    }

    public Integer getLenght() {
        return lenght;
    }
    public void setLenght(String lenght){
       if(null!=lenght){
            this.lenght=Integer.parseInt(lenght);
        }
    }

    public boolean isInsertRequestWithQuotes() {
        return insertRequestWithQuotes;
    }
    
    public String convertTo(Type newDataType, String value)throws InvalidConversionException{
        throw new UnsupportedOperationException("Este método aun no fue implementado.");
    }
    
    @Override
    public String toString(){
        return this.getDbName();
    }
    public DataFilterCriteria[] getAvailableDataFilterCriteriaList(){
        throw new UnsupportedOperationException("Este método aun no fue implementado.");
    }
    public boolean passFilter(DataFilterCriteria filterCriteria, String columnValue, String value1, String value2)throws Exception{
        throw new UnsupportedOperationException("Este método aun no fue implementado.");
    }
}
