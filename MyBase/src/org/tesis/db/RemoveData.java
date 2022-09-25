package org.tesis.db;

public class RemoveData {
    private String internalId;
    private String tableName;

    public RemoveData(String tableName, String internalId) {//internalId puede ser nulo en el caso de que se quiera borrar todos los datos de la table usando getRemoveAllDataFromTableStatement en dbms
        this.tableName = tableName;
        this.internalId=internalId;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
