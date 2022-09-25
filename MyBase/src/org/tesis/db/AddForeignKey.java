package org.tesis.db;


public final class AddForeignKey {
    private String tableName;
    private String foreignTableName;
    private String foreignKeyName;
    private String columnName;
    private String foreignColumnName;
    
    public AddForeignKey(String tableName, String columnName, String foreignTableName, String foreignColumnName, String foreignKeyName) {
        this.tableName=tableName;
        this.foreignTableName=foreignTableName;
        this.columnName=columnName;
        this.foreignColumnName=foreignColumnName;
        this.foreignKeyName=foreignKeyName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getForeignTableName() {
        return foreignTableName;
    }

    public void setForeignTableName(String foreignTableName) {
        this.foreignTableName = foreignTableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getForeignColumnName() {
        return foreignColumnName;
    }

    public void setForeignColumnName(String foreignColumnName) {
        this.foreignColumnName = foreignColumnName;
    }

    public String getForeignKeyName() {
        return foreignKeyName;
    }

    public void setForeignKeyName(String foreignKeyName) {
        this.foreignKeyName = foreignKeyName;
    }
}
