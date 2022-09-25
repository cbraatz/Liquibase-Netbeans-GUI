package org.tesis.db.dbms;

import java.util.List;
import org.tesis.db.*;

public interface DbmsManagement {
    public SqlQuery getCreateTableStatement(Table table, String iidType) throws Exception;
    public SqlQuery getRenameTableStatement(RenameTable renameTable) throws Exception;
    public SqlQuery getRenameColumnStatement(RenameColumn renameColumn) throws Exception;
    public SqlQuery getEditColumnTypeStatement(EditColumnType editColumnType) throws Exception;
    public SqlQuery getEditColumnNullableStatement(EditColumnNullable editColumnNullable) throws Exception;
    public SqlQuery getEditTablePKStatement(EditTablePK editTablePK) throws Exception;
    public SqlQuery getRemoveTableStatement(RemoveTable removeTable) throws Exception;
    public SqlQuery getRemoveTableCascadeStatement(RemoveTable removeTable) throws Exception;
    public SqlQuery getRemoveAllDataFromTableStatement(RemoveData removeData) throws Exception;
    public SqlQuery getAddForeignKeyStatement(AddForeignKey addFK) throws Exception;
    public SqlQuery getEditForeignKeyStatement(EditForeignKey editFK) throws Exception;
    public SqlQuery getRemoveForeignKeyStatement(RemoveForeignKey removeFK) throws Exception;
    public SqlQuery getAddColumnStatement(AddColumn addColumn) throws Exception;
    public SqlQuery getRemoveColumnStatement(RemoveColumn removeColumn) throws Exception;
    public SqlQuery getAddDataStatement(DataRow dataRow) throws Exception;
    public SqlQuery getEditDataStatement(EditData editData) throws Exception;
    public SqlQuery getRemoveDataStatement(RemoveData removeData) throws Exception;
    public SqlQuery getSetNullDataToColumnStatement(SetNullDataToColumn setNullDataToColumn) throws Exception;
    public String getStringTypeName();
    public String getIntegerTypeName();
    public String getFloatTypeName();
    public String getBooleanTypeName();
    public String getCharacterTypeName();
    public List<String> getAllTypeNames();
    public String getInetTypeName();
    public String getDateTypeName();
    public String getTableDDL(Table table); 
    public String getDatabaseCreationDDL(String database); 
    public String getDatabaseQueries(Database database) throws Exception;
}
