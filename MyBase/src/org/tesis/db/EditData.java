package org.tesis.db;

import java.util.ArrayList;
import java.util.List;

public class EditData {
    private List<DataColumn> dataColumns=new ArrayList<>();
    private String internalId;
    private String tableName;

    public EditData(String tableName, List<DataColumn> dataColumns, String internalId) {
        this.tableName = tableName;
        this.internalId=internalId;
        this.dataColumns=dataColumns;
    }

    public List<DataColumn> getDataColumns() {
        return dataColumns;
    }

    public void setDataColumns(List<DataColumn> dataColumns) {
        this.dataColumns = dataColumns;
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
