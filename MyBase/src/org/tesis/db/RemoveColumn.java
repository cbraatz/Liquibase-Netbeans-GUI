package org.tesis.db;

public class RemoveColumn {
    private String tableName;
    private String columnName;
    
    public RemoveColumn(String tableName, String columnName) {
        this.columnName=columnName;
        this.tableName = tableName;
    }
    
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
}
