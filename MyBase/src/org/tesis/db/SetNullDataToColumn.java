package org.tesis.db;

import org.tesis.changelog.type.Type;
import org.tesis.db.dbms.Dbms;

public final class SetNullDataToColumn extends EditColumnObj{
    String columnName;
    
    public SetNullDataToColumn(String tableName, String colName, Type oldType){
        this.tableName=tableName;
        this.columnName=colName;
        this.oldType=oldType;
    }
    
    public String getColumnName() {
        return columnName;
    }

    @Override
    public SqlQuery getSQLStatement(Dbms dbms) throws Exception{
        return dbms.getDbmsInstance().getSetNullDataToColumnStatement(this);
    }
}
