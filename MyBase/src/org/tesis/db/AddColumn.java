package org.tesis.db;

import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.type.Type;
import org.tesis.changelog.type.TypeFactory;
import org.tesis.db.dbms.Dbms;

public class AddColumn {
    private String tableName;
    private Column column;
    public AddColumn(Column column, String tableName) {
        this.column=column;
        this.tableName = tableName;
        
    }
   
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}
