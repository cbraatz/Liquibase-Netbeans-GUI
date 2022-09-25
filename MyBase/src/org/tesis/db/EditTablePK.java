package org.tesis.db;

public final class EditTablePK {
    private String tableName;
    private String primaryKeyName;
    private String oldPKs;
    private String PKs;
    public EditTablePK(String tableName, String primaryKeyName, String oldPKs, String PKs) {
        this.tableName=tableName;
        this.oldPKs=oldPKs;
        this.PKs=PKs;
        this.primaryKeyName=primaryKeyName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getOldPKs() {
        return oldPKs;
    }

    public String getPKs() {
        return PKs;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }
}