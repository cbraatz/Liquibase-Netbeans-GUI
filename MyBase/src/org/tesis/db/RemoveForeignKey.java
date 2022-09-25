package org.tesis.db;

public class RemoveForeignKey {
    private String columnName;
    private String tableName;
    private String foreignKeyName;
    
    public RemoveForeignKey(String tableName, String columnName, String foreignKeyName) {
        this.columnName=columnName;
        this.tableName=tableName;
        this.foreignKeyName=foreignKeyName;
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

    public String getForeignKeyName() {
        return foreignKeyName;
    }

    public void setForeignKeyName(String foreignKeyName) {
        this.foreignKeyName = foreignKeyName;
    }
    
}
