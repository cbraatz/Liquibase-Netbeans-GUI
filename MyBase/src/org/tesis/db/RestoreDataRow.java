package org.tesis.db;

import java.util.List;
import org.tesis.exception.InvalidParameterException;

public class RestoreDataRow extends DataRow{
    private String oldInternalId;

    public RestoreDataRow(String internalId, String tableName,String oldInternalId, List<DataColumn> dataColumns) throws InvalidParameterException {
        super(internalId, tableName, dataColumns);
        this.oldInternalId=oldInternalId;
    }

    public String getOldInternalId() {
        return oldInternalId;
    }

    public void setOldInternalId(String oldInternalId) {
        this.oldInternalId = oldInternalId;
    }
    public DataRow getDataRow(){
        return (DataRow)this;
    }
}
