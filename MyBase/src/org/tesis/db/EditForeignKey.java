package org.tesis.db;


public final class EditForeignKey {
    private String tableName;
    private String foreignTableName;
    private String foreignKeyName;
    private String columnName;
    private String foreignColumnName;
    private String oldForeignTableName;
    private String newForeignKeyName;
    private String oldColumnName;
    private String oldForeignColumnName;
    
    public EditForeignKey(String tableName, String columnName, String foreignTableName, String foreignColumnName, String foreignKeyName, String newForeignKeyName, String oldColumnName, String oldForeignTableName, String oldForeignColumnName) {
        this.tableName=tableName;
        this.foreignTableName=foreignTableName;
        this.columnName=columnName;
        this.foreignColumnName=foreignColumnName;
        this.foreignKeyName=foreignKeyName;
        this.oldForeignTableName=oldForeignTableName;
        this.oldColumnName=oldColumnName;
        this.oldForeignColumnName=oldForeignColumnName;
        this.newForeignKeyName=newForeignKeyName;
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

    public String getOldForeignTableName() {
        return oldForeignTableName;
    }

    public void setOldForeignTableName(String oldForeignTableName) {
        this.oldForeignTableName = oldForeignTableName;
    }

    public String getNewForeignKeyName() {
        return newForeignKeyName;
    }

    public void setNewForeignKeyName(String newForeignKeyName) {
        this.newForeignKeyName = newForeignKeyName;
    }

    public String getOldColumnName() {
        return oldColumnName;
    }

    public void setOldColumnName(String oldColumnName) {
        this.oldColumnName = oldColumnName;
    }

    public String getOldForeignColumnName() {
        return oldForeignColumnName;
    }

    public void setOldForeignColumnName(String oldForeignColumnName) {
        this.oldForeignColumnName = oldForeignColumnName;
    }
    
}
