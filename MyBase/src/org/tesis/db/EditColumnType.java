package org.tesis.db;

import org.tesis.changelog.type.Type;
import org.tesis.db.dbms.Dbms;

public final class EditColumnType extends EditColumnObj{
    Type  newType;//oldType ya está en la clase padre
    String columnName;
    public EditColumnType(String tableName, String colName, Type  oldType, Type  newType){
        this.tableName=tableName;
        this.columnName=colName;
        this.oldType=oldType;
        this.newType=newType;
    }

    public Type getNewType() {
        return newType;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    @Override
    public SqlQuery getSQLStatement(Dbms dbms) throws Exception{
        return dbms.getDbmsInstance().getEditColumnTypeStatement(this);
    }
}
