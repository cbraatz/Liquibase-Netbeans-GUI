/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.db;

import org.tesis.changelog.type.Type;
import org.tesis.db.dbms.Dbms;

/**
 *
 * @author Claus
 */
public final class RenameColumn extends EditColumnObj{
    String oldName, newName;
    
    public RenameColumn(String tableName, String oldName, String newName, Type  oldType){
        this.tableName=tableName;
        this.oldName=oldName;
        this.newName=newName;
        this.oldType=oldType;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }
    
    @Override
    public SqlQuery getSQLStatement(Dbms dbms) throws Exception{
        return dbms.getDbmsInstance().getRenameColumnStatement(this);
    }
}
