package org.tesis.db;

import org.tesis.changelog.type.Type;
import org.tesis.db.dbms.Dbms;

public abstract class EditColumnObj {
    String tableName;
    Type oldType;

    public String getTableName() {
        return tableName;
    }

    public Type getOldType() {
        return oldType;
    }
    
    public SqlQuery getSQLStatement(Dbms dbms) throws Exception{
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
