package org.tesis.db;

import org.tesis.changelog.property.PropertyList;
import org.tesis.db.dbms.Dbms;

public class PK {
    private String columnName;
    private String value;
    private boolean quoted;
    
    public PK(PropertyList properties, Dbms dbms) throws Exception{
        this.columnName=properties.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME);
        this.value=properties.getPropertyValueByKey(Constants.PROPERTY_VALUE);
        this.quoted=Boolean.parseBoolean(properties.getPropertyValueByKey(Constants.PROPERTY_QUOTED));
    }

    public PK(String columnName, String value, boolean quoted) {
        this.columnName = columnName;
        this.value = value;
        this.quoted = quoted;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isQuoted() {
        return quoted;
    }

    public void setQuoted(boolean quoted) {
        this.quoted = quoted;
    }
    
    @Override
    public String toString(){
        return this.columnName+"="+(this.quoted?"'"+this.value+"'":this.value);
    }
}
