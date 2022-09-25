package org.tesis.db.dbms;

import java.text.SimpleDateFormat;
import org.tesis.db.Constants;
import org.tesis.db.EditColumnNullable;
import org.tesis.db.EditColumnType;
import org.tesis.db.EditForeignKey;
import org.tesis.db.EditTablePK;
import org.tesis.db.RemoveForeignKey;
import org.tesis.db.RenameColumn;
import org.tesis.db.RenameTable;
import org.tesis.db.SqlQuery;
import org.tesis.util.Utils;

public class MySQLv5_6 extends DbmsCommon implements DbmsManagement{
    @Override
    public SqlQuery getRenameTableStatement(RenameTable renameTable) throws Exception {
        return new SqlQuery("RENAME TABLE "+renameTable.getOldName()+" TO "+renameTable.getName()+";");
    }
    
    @Override
    public SqlQuery getRenameColumnStatement(RenameColumn renameColumn) throws Exception {
        return new SqlQuery(" ALTER TABLE "+renameColumn.getTableName()+" CHANGE "+renameColumn.getOldName()+" "+renameColumn.getNewName()+" "+renameColumn.getOldType().toString()+";");
    }
     @Override
    public SqlQuery getEditColumnTypeStatement(EditColumnType editColumnType) throws Exception {
        return new SqlQuery("ALTER TABLE "+editColumnType.getTableName()+" MODIFY "+editColumnType.getColumnName()+" "+editColumnType.getNewType().toString()+";");
    }

    @Override
    public SqlQuery getEditColumnNullableStatement(EditColumnNullable editColumnNullable) throws Exception {
         return new SqlQuery("ALTER TABLE "+editColumnNullable.getTableName()+" MODIFY "+editColumnNullable.getColumnName()+" "+editColumnNullable.getOldType().toString()+" "+(editColumnNullable.getNewNullable()?"NULL":"NOT NULL")+";");
    }

    @Override
    public SqlQuery getEditTablePKStatement(EditTablePK editTablePK) throws Exception {
        String pks=editTablePK.getPKs();
        return new SqlQuery("ALTER TABLE "+editTablePK.getTableName()+" DROP PRIMARY KEY "+(pks.isEmpty()?";":" , ADD PRIMARY KEY ("+editTablePK.getPKs()+");"));
    }
    @Override
    public SqlQuery getRemoveForeignKeyStatement(RemoveForeignKey removeFK) throws Exception {
        return new SqlQuery("ALTER TABLE "+removeFK.getTableName()+" drop foreign key "+removeFK.getForeignKeyName()+";");
    }
    @Override
    public SqlQuery getEditForeignKeyStatement(EditForeignKey editFK) throws Exception {
        return new SqlQuery("ALTER TABLE "+editFK.getTableName()+" DROP FOREIGN KEY "+editFK.getForeignKeyName()+", ADD CONSTRAINT "+editFK.getForeignKeyName()+" FOREIGN KEY ("+editFK.getColumnName()+") REFERENCES "+editFK.getForeignTableName()+"("+editFK.getForeignColumnName()+");");
    }
}