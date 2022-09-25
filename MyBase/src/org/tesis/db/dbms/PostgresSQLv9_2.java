package org.tesis.db.dbms;

import java.util.ArrayList;
import java.util.List;
import org.tesis.db.Constants;
import org.tesis.db.EditColumnNullable;
import org.tesis.db.EditColumnType;
import org.tesis.db.EditForeignKey;
import org.tesis.db.EditTablePK;
import org.tesis.db.RemoveForeignKey;
import org.tesis.db.RemoveTable;
import org.tesis.db.RenameColumn;
import org.tesis.db.RenameTable;
import org.tesis.db.SqlQuery;

public class PostgresSQLv9_2 extends DbmsCommon implements DbmsManagement{
    @Override
    public SqlQuery getRenameTableStatement(RenameTable renameTable) throws Exception {
        return new SqlQuery("ALTER TABLE "+renameTable.getOldName()+" RENAME TO "+renameTable.getName()+";");
    }
    @Override
    public SqlQuery getRenameColumnStatement(RenameColumn renameColumn) throws Exception {
        return new SqlQuery("ALTER TABLE "+renameColumn.getTableName()+" RENAME COLUMN "+renameColumn.getOldName()+" TO "+renameColumn.getNewName()+";");
    }
    @Override
    public SqlQuery getEditColumnTypeStatement(EditColumnType editColumnType) throws Exception {
        return new SqlQuery("ALTER TABLE "+editColumnType.getTableName()+" ALTER COLUMN "+editColumnType.getColumnName()+" TYPE "+editColumnType.getNewType().toString()+" USING "+(editColumnType.getNewType().isInsertRequestWithQuotes()?"'":"")+editColumnType.getNewType().getDefaultValue()+(editColumnType.getNewType().isInsertRequestWithQuotes()?"'":"")+";");
    }
    @Override
    public SqlQuery getRemoveTableStatement(RemoveTable removeTable) throws Exception {
        return new SqlQuery("DROP TABLE \""+removeTable.getName()+"\";");//en postgres es obligatorio poner el nombre de la tabla entre comillas si tiene nombres en mayusculas
    }
    @Override
    public SqlQuery getRemoveTableCascadeStatement(RemoveTable removeTable) throws Exception {
        return new SqlQuery("DROP TABLE \""+removeTable.getName()+"\" CASCADE;");//en postgres es obligatorio poner el nombre de la tabla entre comillas si tiene nombres en mayusculas
    }
    @Override
    public SqlQuery getEditColumnNullableStatement(EditColumnNullable editColumnNullable) throws Exception {
        
        if(editColumnNullable.getNewNullable()){
            return new SqlQuery("ALTER TABLE "+editColumnNullable.getTableName()+" ALTER COLUMN "+editColumnNullable.getColumnName()+" DROP NOT NULL;");
         }else{
            return new SqlQuery("ALTER TABLE "+editColumnNullable.getTableName()+" ALTER COLUMN "+editColumnNullable.getColumnName()+" SET NOT NULL;");
         }
    }

    @Override
    public SqlQuery getEditTablePKStatement(EditTablePK editTablePK) throws Exception {
        String pks=editTablePK.getPKs();
        return new SqlQuery("ALTER TABLE "+editTablePK.getTableName()+" DROP CONSTRAINT "+editTablePK.getPrimaryKeyName()+(pks.isEmpty()?";":", ADD CONSTRAINT "+editTablePK.getPrimaryKeyName()+" PRIMARY KEY("+editTablePK.getPKs()+");"));
    }

    @Override
    public SqlQuery getRemoveForeignKeyStatement(RemoveForeignKey removeFK) throws Exception {
        return new SqlQuery("ALTER TABLE "+removeFK.getTableName()+" DROP CONSTRAINT "+removeFK.getForeignKeyName()+";");
    }
    
    @Override
    public SqlQuery getEditForeignKeyStatement(EditForeignKey editFK) throws Exception {
        return new SqlQuery("ALTER TABLE "+editFK.getTableName()+" DROP CONSTRAINT "+editFK.getForeignKeyName()+", ADD CONSTRAINT "+editFK.getForeignKeyName()+" FOREIGN KEY ("+editFK.getColumnName()+") REFERENCES "+editFK.getForeignTableName()+"("+editFK.getForeignColumnName()+");");
    }
    
    @Override
    public List<String> getAllTypeNames(){
        List<String> res=new ArrayList<>();
        res.add(Constants.TYPE_INT);
        res.add(Constants.TYPE_FLOAT);
        res.add(Constants.TYPE_BOOLEAN);
        res.add(Constants.TYPE_CHAR);
        res.add(Constants.TYPE_STRING);
        //res.add(Constants.TYPE_INET);
        res.add(Constants.TYPE_DATE);
        return res;
    }
}
