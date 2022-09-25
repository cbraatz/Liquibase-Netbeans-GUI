package org.tesis.db;

import org.tesis.changelog.type.Type;
import org.tesis.db.dbms.Dbms;

public final class EditColumnNullable extends EditColumnObj{
    boolean  oldNullable, newNullable;
    String columnName;
    
    public EditColumnNullable(String tableName, String colName, Type oldType, boolean  oldNullable, boolean  newNullable){
        this.tableName=tableName;
        this.columnName=colName;
        this.oldType=oldType;
        this.oldNullable=oldNullable;
        this.newNullable=newNullable;
    }
    
    public boolean getNewNullable() {
        return newNullable;
    }
    public String getColumnName() {
        return columnName;
    }
    public boolean getOldNullable() {
        return oldNullable;
    }

    @Override
    public SqlQuery getSQLStatement(Dbms dbms) throws Exception{
        return dbms.getDbmsInstance().getEditColumnNullableStatement(this);
    }
}
