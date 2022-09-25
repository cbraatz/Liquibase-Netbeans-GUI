package org.tesis.db.dbms;

import java.util.ArrayList;
import java.util.List;
import org.tesis.db.AddColumn;
import org.tesis.db.AddForeignKey;
import org.tesis.db.Column;
import org.tesis.db.Constants;
import org.tesis.db.DataColumn;
import org.tesis.db.DataRow;
import org.tesis.db.Database;
import org.tesis.db.EditColumnNullable;
import org.tesis.db.EditColumnType;
import org.tesis.db.EditData;
import org.tesis.db.EditForeignKey;
import org.tesis.db.EditTablePK;
import org.tesis.db.ForeignKey;
import org.tesis.db.RemoveColumn;
import org.tesis.db.RemoveData;
import org.tesis.db.RemoveForeignKey;
import org.tesis.db.RemoveTable;
import org.tesis.db.RenameColumn;
import org.tesis.db.RenameTable;
import org.tesis.db.SetNullDataToColumn;
import org.tesis.db.SqlQuery;
import org.tesis.db.Table;

public class DbmsCommon implements DbmsManagement{

    @Override
    public SqlQuery getCreateTableStatement(Table table, String iidType) throws Exception{
        StringBuilder res = new StringBuilder();
        res.append("CREATE TABLE ");
        res.append(table.getName());
        res.append(" (");
        res.append(Constants.DEFAULT_TABLE_ID_NAME);
        res.append(" ");
        res.append(iidType);
        res.append(" NOT NULL");
        List<String> pks=new ArrayList<>();
        for(Column c:table.getColumns()){
            res.append(", ");
            res.append(c.getName());
            res.append(" ");
            res.append(c.getType().toString());
            if(c.isPk()){
                res.append(" NOT NULL");
                pks.add(c.getName());
            }else{//si no es clave primaria
                res.append((c.isAllowNull()?" NULL":" NOT NULL"));
            }
        }
        if(!pks.isEmpty()){//si tiene pks
            res.append(", CONSTRAINT ");
            res.append(table.getPrimaryKeyName());
            res.append(" PRIMARY KEY ");
            res.append("(");
            for(int j=0;j<pks.size();j++){
                if(j > 0){
                    res.append(", ");
                }
                res.append(pks.get(j));
            }
            res.append(")");
        }
        res.append(");");
        return new SqlQuery(res.toString());
    }

    @Override
    public String getStringTypeName() {
        return "varchar";
    }

    @Override
    public String getIntegerTypeName() {
        return "int";
    }

    @Override
    public String getFloatTypeName() {
        return "float";
    }

    @Override
    public String getBooleanTypeName() {
        return "boolean";
    }

    @Override
    public String getCharacterTypeName() {
        return "char";
    }
    @Override
    public String getInetTypeName() {
        return "inet";
    }
    @Override
    public String getDateTypeName() {
        return "date";
    }
    @Override
    public List<String> getAllTypeNames(){
        List<String> res=new ArrayList<>();
        res.add(Constants.TYPE_INT);
        res.add(Constants.TYPE_FLOAT);
        res.add(Constants.TYPE_BOOLEAN);
        res.add(Constants.TYPE_CHAR);
        res.add(Constants.TYPE_STRING);
        res.add(Constants.TYPE_DATE);
        return res;
    }

    @Override
    public SqlQuery getRenameTableStatement(RenameTable renameTable) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SqlQuery getRemoveTableStatement(RemoveTable removeTable) throws Exception {
        return new SqlQuery("DROP TABLE "+removeTable.getName()+";");
    }

    @Override
    public SqlQuery getRemoveTableCascadeStatement(RemoveTable removeTable) throws Exception {
        return new SqlQuery("DROP TABLE "+removeTable.getName()+" CASCADE;");
    }
    @Override
    public SqlQuery getRemoveAllDataFromTableStatement(RemoveData removeData) throws Exception {
        return new SqlQuery("DELETE FROM "+removeData.getTableName());
    }
    @Override
    public SqlQuery getRenameColumnStatement(RenameColumn renameColumn) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SqlQuery getEditColumnTypeStatement(EditColumnType editColumnType) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SqlQuery getEditColumnNullableStatement(EditColumnNullable editColumnNullable) throws Exception {
         throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SqlQuery getEditTablePKStatement(EditTablePK editTablePK) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SqlQuery getAddForeignKeyStatement(AddForeignKey addFK) throws Exception {
        return new SqlQuery("ALTER TABLE "+addFK.getTableName()+" ADD CONSTRAINT "+addFK.getForeignKeyName()+" FOREIGN KEY ("+addFK.getColumnName()+") REFERENCES "+addFK.getForeignTableName()+"("+addFK.getForeignColumnName()+");");
    }
    
    @Override
    public SqlQuery getEditForeignKeyStatement(EditForeignKey editFK) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SqlQuery getRemoveForeignKeyStatement(RemoveForeignKey removeFK) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SqlQuery getAddColumnStatement(AddColumn addColumn) throws Exception {
        return new SqlQuery("ALTER TABLE "+addColumn.getTableName()+" ADD COLUMN "+addColumn.getColumn().getName()+" "+addColumn.getColumn().getType().toString()+";");
    }
    
    @Override
    public SqlQuery getRemoveColumnStatement(RemoveColumn remColumn) throws Exception {
        return new SqlQuery("ALTER TABLE "+remColumn.getTableName()+" DROP COLUMN "+remColumn.getColumnName()+";");
    }
    @Override
    public String getTableDDL(Table table) {
        StringBuilder res = new StringBuilder();
        res.append("CREATE TABLE ");
        res.append(table.getName());
        res.append(" (\n\t");
        int i=0;
        List<String> pks=new ArrayList<>();
        for(Column c:table.getColumns()){
            res.append((i>0?" ,\n\t":""));
            res.append(c.getName());
            res.append(" ");
            res.append(c.getType().toString());//esto fue agregado en vez de lo comentado mas arriba
            if(c.isPk()){
                res.append(" NOT NULL");
                pks.add(c.getName());
            }else{//si no es clave primaria
                res.append((c.isAllowNull()?" NULL":" NOT NULL"));
            }
            i++;
        }
        if(!pks.isEmpty()){//si tiene pks
            res.append(",\n\tCONSTRAINT ");
            res.append(table.getPrimaryKeyName());
            res.append(" PRIMARY KEY ");
            res.append("(");
            for(int j=0;j<pks.size();j++){
                if(j > 0){
                    res.append(", ");
                }
                res.append(pks.get(j));
            }
            res.append(")");
        }
        if(!table.getForeignKeys().isEmpty()){//si tiene pks
            for(ForeignKey fk: table.getForeignKeys().getForeignKeys()){
                res.append(",\n\tCONSTRAINT ");
                res.append(fk.getFKName());
                res.append(" FOREIGN KEY ");
                res.append("(");
                res.append(fk.getColumn().getName());
                res.append(") REFERENCES ");
                res.append(fk.getForeignTable().getName());
                res.append("(");
                res.append(fk.getForeignColumn().getName());
                res.append(")");
            }
        }
        res.append("\n);");
        //System.out.println(res.toString());
        return res.toString();
    }

    @Override
    public String getDatabaseCreationDDL(String database) {
        return "CREATE DATABASE "+database+";";
    }

    @Override
    public String getDatabaseQueries(Database database) throws Exception{
        StringBuilder res = new StringBuilder();
        for(SqlQuery sql:database.getChangeLog().getSqlQueries()){
            res.append(sql.getQuery());
            res.append("\n");
        }
        return res.toString();
    }

    @Override
    public SqlQuery getAddDataStatement(DataRow dataRow) throws Exception {
        StringBuilder res = new StringBuilder();
        res.append("INSERT INTO ");
        //INSERT INTO tbl_name (iid, id, col_str) VALUES(796487941549, 15, 'gdgf');
        res.append(dataRow.getTableName());
        res.append(" (");
        res.append(Constants.DEFAULT_TABLE_ID_NAME);//iid
        for(DataColumn c:dataRow.getDataColumns()){
            res.append(", ");
            res.append(c.getColumnName());
        }
        res.append(") VALUES ('");
        res.append(dataRow.getInternalId());//iid=internalId
        res.append("'");
        for(DataColumn c:dataRow.getDataColumns()){
            res.append(", ");
            if(c.isQuoted()){
                res.append("'");
                res.append(c.getValue());
                res.append("'");
            }else{
                res.append(c.getValue());
            }
        }
        res.append(");");
        return new SqlQuery(res.toString());
    }

    @Override
    public SqlQuery getEditDataStatement(EditData editData) throws Exception {
        StringBuilder res = new StringBuilder();
        res.append("UPDATE ");
        res.append(editData.getTableName());
        res.append(" SET ");
        boolean fir=true;
        for(DataColumn c:editData.getDataColumns()){
            res.append((!fir?", ":""));
            res.append(c.getColumnName());
            res.append(" = ");
            if(c.getValue().isEmpty()){
                res.append("null");
            }else{
                res.append(c.isQuoted()?"'"+c.getValue()+"'":c.getValue());
            }
            fir=false;
        }
        res.append(" WHERE ");
        res.append(Constants.DEFAULT_TABLE_ID_NAME);
        res.append(" = ");
        res.append("'");
        res.append(editData.getInternalId());
        res.append("';");
        return new SqlQuery(res.toString());
    }

    @Override
    public SqlQuery getRemoveDataStatement(RemoveData removeData) throws Exception {
        StringBuilder res = new StringBuilder();
        res.append("DELETE FROM ");
        res.append(removeData.getTableName());
        res.append(" WHERE ");
        res.append(Constants.DEFAULT_TABLE_ID_NAME);
        res.append(" = ");
        res.append("'");
        res.append(removeData.getInternalId());
        res.append("';");
        return new SqlQuery(res.toString());
    }

    @Override
    public SqlQuery getSetNullDataToColumnStatement(SetNullDataToColumn setNullDataToColumn) throws Exception {
        return new SqlQuery("UPDATE "+setNullDataToColumn.getTableName()+" SET "+setNullDataToColumn.getColumnName()+" = NULL");
    }    
}
