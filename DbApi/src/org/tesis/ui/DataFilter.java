package org.tesis.ui;

import org.tesis.changelog.type.Type;
import org.tesis.db.DataFilterCriteria;

public class DataFilter {
   String columnName;
   Type columnType;
   DataFilterCriteria filterCriteria;
   String value1;
   String value2;

    public DataFilter(String columnName, Type columnType, DataFilterCriteria filterCriteria, String value1, String value2) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.filterCriteria = filterCriteria;
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Type getColumnType() {
        return columnType;
    }

    public void setColumnType(Type columnType) {
        this.columnType = columnType;
    }

    public DataFilterCriteria getFilterCriteria() {
        return filterCriteria;
    }

    public void setFilterCriteria(DataFilterCriteria filterCriteria) {
        this.filterCriteria = filterCriteria;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }
    public boolean passFilter(String val) throws Exception{
        return this.getColumnType().passFilter(filterCriteria, val, value1, value2);
    }
}
