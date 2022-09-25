package org.tesis.db;
    
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tesis.changelog.property.PKListProperty;
import org.tesis.changelog.property.TableNameProperty;
import org.tesis.changelog.tag.AddColumnTag;
import org.tesis.changelog.tag.AddForeignKeyTag;
import org.tesis.changelog.tag.EditColumnNullableTag;
import org.tesis.changelog.tag.EditColumnTag;
import org.tesis.changelog.tag.EditDataTag;
import org.tesis.changelog.tag.EditForeignKeyTag;
import org.tesis.changelog.tag.EditTablePKTag;
import org.tesis.changelog.tag.InsertDataTag;
import org.tesis.changelog.tag.NewTableTag;
import org.tesis.changelog.tag.RemoveColumnTag;
import org.tesis.changelog.tag.RemoveDataTag;
import org.tesis.changelog.tag.RemoveForeignKeyTag;
import org.tesis.changelog.tag.RemoveTableTag;
import org.tesis.changelog.tag.RenameTableTag;
import org.tesis.changelog.tag.RestoreDataTag;
import org.tesis.changelog.tag.SetNullDataToColumnTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.changelog.tag.TagList;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.ColumnNotFoundException;
import org.tesis.exception.InvalidForeignKeyException;
import org.tesis.exception.InvalidParameterException;
import org.tesis.exception.InvalidValueException;
import org.tesis.exception.NotUniqueNameException;
import org.tesis.exception.TableNotFoundException;
import org.tesis.exception.UnknownClassException;
import org.tesis.mybase.ChangeLog;
import org.tesis.util.MyBaseLogger;

public final class Database {
    private String name;
    private MyConnection connection;
    private List<Table> tables=new ArrayList<>();
    private ChangeLog changeLog;/**
     * Constructor de Database
     * @param connection conexión de base de datos
     * @param changeLog objeto ChangeLog obtenido del Interprete
     * @throws Exception 
     */
    public Database(MyConnection connection, ChangeLog changeLog, Dbms dbms) throws Exception{
       this.connection=connection;
       this.changeLog=changeLog;
       this.name=connection.getDatabaseName();
       this.updateDatabase(dbms);
    }
    /**
     * Genera el Objeto Database a partir del ChangeLog
     * @throws Exception 
     */
    public void generateDatabaseObject() throws Exception{
        this.tables=new ArrayList<>();
        for(Tag t: this.getChangeLog().getTags()){
            switch(t.getClass().getSimpleName()){
                case "NewTableTag": this.addTable((NewTableTag) t); break;
                case "RenameTableTag": this.renameTable((RenameTableTag) t); break;
                case "RemoveTableTag": this.removeTable((RemoveTableTag) t); break;
                case "EditColumnTag": this.editColumn((EditColumnTag) t); break;
                case "EditColumnNullableTag": this.editColumnNullable((EditColumnNullableTag) t); break;
                case "EditTablePKTag": this.editPKs((EditTablePKTag) t); break;
                case "AddForeignKeyTag": this.addForeignKey((AddForeignKeyTag) t); break;
                case "RemoveForeignKeyTag": this.removeForeignKey((RemoveForeignKeyTag) t); break;
                case "AddColumnTag": this.addColumn((AddColumnTag) t); break;
                case "RemoveColumnTag": this.removeColumn((RemoveColumnTag) t); break;
                case "EditForeignKeyTag": this.editForeignKey((EditForeignKeyTag) t); break;
                case "InsertDataTag": this.insertData((InsertDataTag) t); break;
                case "EditDataTag": this.editData((EditDataTag) t); break;
                case "RemoveDataTag": this.removeData((RemoveDataTag) t); break;
                case "SetNullDataToColumnTag": this.setNullDataToColumn((SetNullDataToColumnTag) t); break;
                case "RestoreDataTag": this.restoreData((RestoreDataTag) t); break;
                default: throw new UnknownClassException("Clase no admitida: "+t.getClass().getSimpleName());
            }
        }
    }
    /**
     * Agrega un nuevo tag al changeLog de la base de datos y actualiza el objeto de bd, actualiza la bd y el xml.
     * @param tag Tag a agregar.
     */
    /*public void addNewTagAndUpdateAll(Tag tag) throws Exception{
            this.changeLog.addTag(tag);
            List<SqlQuery> sqls=new ArrayList<>();
            sqls.addAll(tag.exportSQLQuery());
            this.connection.executeMultipleSqlQuery(sqls);
            this.generateDatabaseObject();
            this.generateXMLFile();
    }*/
     /**
     * Agrega una nueva lista de tags al changeLog de la base de datos y actualiza el objeto de bd, actualiza la bd y el xml.
     * @param tags  lsota de Tag a agregar.
     */
    public void addNewTagListAndUpdateAll(TagList tags) throws Exception{
            for(Tag t:tags.getTagList()){
               this.changeLog.addTag(t);
            }
            List<SqlQuery> sqls=new ArrayList<>();
            for(Tag t:tags.getTagList()){
               sqls.addAll(t.exportSQLQuery());
            }
            this.connection.executeMultipleSqlQuery(sqls);
            this.generateDatabaseObject();
            this.generateXMLFile();
    }
    /**
     * Borra todas las tablas de la base de datos y luego genera la base de datos ejecutando los SQLs obtenidos del objeto ChangeLog
     * @throws Exception 
     */
    public void updateDatabase(Dbms dbms) throws Exception{
        this.connection.dropAllTables(dbms);
        this.connection.executeMultipleSqlQuery(changeLog.getSqlQueries());
        this.generateDatabaseObject();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Table> getTables() {
        return this.tables;
    }

    public MyConnection getConnection() {
        return connection;
    }

    public ChangeLog getChangeLog() {
        return changeLog;
    }
    /**
     * Agrega una tabla a la lista de tablas
     * @param newTableTag
     * @throws NotUniqueNameException
     * @throws Exception 
     */
    private void addTable(NewTableTag newTableTag) throws NotUniqueNameException, Exception{
        String tableName=newTableTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_NAME);
        for(Table t:tables){
            if(t.getName().equals(tableName)){
                throw new NotUniqueNameException("Nombre no válido para la tabla: "+t.getName()+". Nombre ya está en uso por otra tabla");
            }
        }
        this.tables.add((Table) newTableTag.exportDBObject());
    }  
    /**
     * Busca una tabla de la lista y la cambia de nombre
     * @param renameTableTag
     * @throws NotUniqueNameException
     * @throws TableNotFoundException 
     */
    private void renameTable(RenameTableTag renameTableTag) throws NotUniqueNameException, TableNotFoundException{
        boolean exists=false;
        String tableName=renameTableTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_NAME);
        for(Table t:tables){//verifica que no exista el nuevo nombre
            if(t.getName().equals(tableName)){
                throw new NotUniqueNameException("Tabla: "+t.getName()+" no puede ser renombrado a: "+tableName+". Nombre ya está en uso por otra tabla");
            }
        }
        String oldTableName=renameTableTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_OLD_NAME);
        for(Table t:tables){//busca la tabla y si existe remplaza su nombre y setea la bandera exists
            if(t.getName().equals(oldTableName)){
                t.setName(tableName);
                exists=true;
                //System.err.println("Verificar los FK, si hay una tabla que es FK, ver si se modifica ahí tambien el nombre de la tabla");
                break;
            }
        }
        if(!exists){//si no existe tira error
            throw new TableNotFoundException("Tabla: "+oldTableName+" no puede ser renombrado a: "+tableName+". La tabla no existe.");
        }
    } 
    /**
     * Busca una tabla de la lista y la borra
     * @param removeTableTag
     * @throws NotUniqueNameException
     * @throws TableNotFoundException 
     */
    private void removeTable(RemoveTableTag removeTableTag) throws NotUniqueNameException, TableNotFoundException{
        boolean exists=false;
        String tableName=removeTableTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_NAME);
        for(Table t:tables){//busca la tabla y si existe la borra y setea la bandera exists
            if(t.getName().equals(tableName)){
                tables.remove(t);
                exists=true;
                break;
            }
        }
        if(!exists){//si no existe tira error
            throw new TableNotFoundException("Tabla: "+tableName+" no puede ser borrada porque no existe.");
        }
    } 
    private void editPKs(EditTablePKTag editTablePKTag) throws Exception{
        
        
        //solo para impresion//////////////////////////////////////////////////
        /*for(Table t:tables){
            System.out.println("TABLA ANTES DE MODIFICAR PK"+t.getName());
                 for(Column c: t.getColumns()){
                     System.out.println("columna= "+c.getName()+c.isPk());
                 }
        }*/
        /////////////////////////////////////////////////////////////////////

        
        
        PKListProperty namesProp=(PKListProperty) editTablePKTag.getProperties().getPropertyByKey(Constants.PROPERTY_PKS);
        List <String> names=namesProp.getColumnNames();
        TableNameProperty tableNameprop=(TableNameProperty) editTablePKTag.getProperties().getPropertyByKey(Constants.PROPERTY_TABLE_NAME);
        for(Table t:tables){
            if(t.getName().equals(tableNameprop.getValue())){//si es la tabla
                 for(Column c: t.getColumns()){
                     boolean x=false;
                     for(String n: names){
                        if(c.getName().equals(n)){
                            x=true;
                            break;
                        }
                     }
                     c.setPk(x);
                 }
                 break;
            }
        }
        
        //solo para impresion//////////////////////////////////////////////////
        for(Table t:tables){
            MyBaseLogger.LogInformationMessage("TABLA DESPUES DE MODIFICAR PK"+t.getName(),this.getClass().getName());
             /*for(Column c: t.getColumns()){
                 System.out.println("columna= "+c.getName()+c.isPk());
             }*/
        }
        /////////////////////////////////////////////////////////////////////

    }
    /**
     * Busca una Columna de una tabla y la edita.
     * @param editColumnTag 
     */
    private void editColumn(EditColumnTag editColumnTag) throws Exception{
        Table table=null;
        Column column=null;
        String tableName=editColumnTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME);
        for(Table t:tables){//busca la tabla
            if(t.getName().equals(tableName)){
                table=t;
                break;
            }
        }
        String columnName=editColumnTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME);
        if(null != table){
            for(Column c:table.getColumns()){
                if(c.getName().equals(columnName)){
                    column=c;
                    break;
                }
            }
        }else{
            throw new TableNotFoundException("Tabla: "+tableName+" no fue econtrada, cuya columna: '"+columnName+"' se intentó editar en el tag EditColumn. La tabla no existe.");
        }
        if(null != column){
            this.renameColumn((RenameColumn) editColumnTag.getEditColumnObjectByClassName(RenameColumn.class), column);
           // this.editColumnNullable((EditColumnNullable) editColumnTag.getEditColumnObjectByClassName(EditColumnNullable.class), column);
            this.editColumnType((EditColumnType) editColumnTag.getEditColumnObjectByClassName(EditColumnType.class), column);
        }else{
            throw new ColumnNotFoundException("Columna '"+editColumnTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME)+"' de la Tabla: "+tableName+" se intentó editar en el tag EditColumn. La Columna no existe");
        }
    }
    /**
     * Busca una Columna de una tabla y edita su propiedad nullable.
     * @param editColumnNullableTag 
     */
    private void editColumnNullable(EditColumnNullableTag editColumnNullableTag) throws Exception{
        Table table=null;
        Column column=null;
        String tableName=editColumnNullableTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME);
        for(Table t:tables){//busca la tabla
            if(t.getName().equals(tableName)){
                table=t;
                break;
            }
        }
        String columnName=editColumnNullableTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME);
        String nullable=editColumnNullableTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_NULLABLE);
        if(null != nullable){
            if(null != table){
                for(Column c:table.getColumns()){
                    if(c.getName().equals(columnName)){
                        c.setAllowNull(Boolean.parseBoolean(nullable));
                        column=c;
                        break;
                    }
                }
            }else{
                throw new TableNotFoundException("Tabla: "+tableName+" no fue econtrada, cuya columna: '"+columnName+"' se intentó cambiar su propiedad nullable. La tabla no existe.");
            }
        }else{
            throw new TableNotFoundException("La propiedad '"+Constants.PROPERTY_NULLABLE+"' no fue encontrada al intentar editar dicha propiedad en la Tabla: "+tableName+", columna: '"+columnName+"'.");
        }
        if(null == column){
            throw new ColumnNotFoundException("Columna '"+editColumnNullableTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME)+"' de la Tabla: "+tableName+" se intentó se intentó cambiar su propiedad nullable. La Columna no existe");
        }
    }
    private void renameColumn(RenameColumn edit, Column column){
        if(null!=edit){
            column.setName(edit.getNewName());
            System.err.println("Verificar los FK, si hay una columna que es FK, ver si se modifica ahí tambien el nombre de la columna");
        }
    }
    /**
     * Agrega una columna a una tabla
     * @param addColumnTag
     * @throws NotUniqueNameException
     * @throws Exception 
     */
    private void addColumn(AddColumnTag addColumnTag) throws Exception{
        AddColumn addColumn=(AddColumn)addColumnTag.exportDBObject();
        String tableName=addColumn.getTableName();
        String columnName=addColumn.getColumn().getName();
        for(Table t:tables){
            if(t.getName().equals(tableName)){
                for(Column c: t.getColumns()){
                    if(c.getName().equals(columnName)){
                        throw new NotUniqueNameException("La columna "+columnName+" no pudo ser agregada a la tabla "+tableName+". Una columna con el mismo nombre ya existe en la tabla.");
                    }
                }
                t.getColumns().add(addColumn.getColumn());
            }
        }
    }
    /**
     * borra una columna de una tabla
     * @param removeColumnTag
     * @throws Exception 
     */
    private void removeColumn(RemoveColumnTag removeColumnTag) throws Exception{
        RemoveColumn removeColumn=(RemoveColumn)removeColumnTag.exportDBObject();
        String tableName=removeColumn.getTableName();
        String columnName=removeColumn.getColumnName();
        for(Table t:tables){
            if(t.getName().equals(tableName)){
                boolean exists=false;
                for(Column c: t.getColumns()){
                    if(c.getName().equals(columnName)){
                        this.removeForeignKey(tableName, columnName, false);
                        t.getColumns().remove(c);
                        exists=true;
                        break;
                    }
                }
                if(!exists){
                    throw new ColumnNotFoundException("La columna "+columnName+" no existe en la tabla "+tableName+".");
                }
                for(DataRow dr:t.getRemovedRows()){
                    dr.removeDataColumnIfExists(columnName);
                }
                for(DataRow dr:t.getRows()){
                    dr.removeDataColumnIfExists(columnName);
                }
            }
        }
    }
    private void editColumnType(EditColumnType edit, Column column){
        if(null!=edit){
            column.setType(edit.getNewType());
        }
        try {
            for(Table t:tables){
                if(t.getName().equals(edit.getTableName())){
                    for(DataRow d:t.getRemovedRows()){
                        DataColumn dc=d.getDataColumnByName(edit.getColumnName());
                        String val=edit.getOldType().convertTo(edit.getNewType(), dc.getValue());
                        d.replaceValueOrAddDataColumn(new DataColumn(column.getName(), val, edit.getNewType().isInsertRequestWithQuotes()));
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addForeignKey(AddForeignKeyTag addFKTag) throws Exception{
        String fkName=addFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_FK_NAME);
        String tableName=addFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME);
        String columnName=addFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME);
        String foreignTableName=addFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_FOREIGN_TABLE_NAME);
        String foreignColumnName=addFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_FOREIGN_COLUMN_NAME);
        long id=Long.parseLong(addFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_ID));
        if(!tableName.equals(foreignTableName)){//valida que el nombre de la tabla de origen y destino no sean iguales           
            Table foreignTable=null;
            Table table=null;
            Column foreignColumn=null;
            Column column=null;
            for(Table t :tables){//carga los valores de los objetos
                if(t.getName().equals(tableName)){
                    table=t;
                    for(Column c: t.getColumns()){
                        if(c.getName().equals(columnName)){
                            column=c;
                            break;
                        }
                    }
                }else{
                    if(t.getName().equals(foreignTableName)){
                        foreignTable=t;
                        for(Column c: t.getColumns()){
                            if(c.getName().equals(foreignColumnName)){
                                foreignColumn=c;
                                break;
                            }
                        }
                    }
                }
                if(null != foreignColumn && null != column){
                    break;
                }
            }
            if(!(null == foreignColumn || null == column || null == foreignTable || null == table || null == fkName)){
                ForeignKey fkey=new ForeignKey(id, fkName, table, column, foreignTable, foreignColumn);
                table.addForeignKey(fkey); 
               // System.out.println("FK con id = "+fkey.getInternalId()+" fue agregado correctamente al objeto database.");
            }else{
                throw new InvalidForeignKeyException("Alguno de estos valores no han sido encontrados. Favor verificar los nombres. FKName : '"+fkName+"'. Columnas : '"+columnName+"' , '"+foreignColumnName+"'. Tablas : '"+tableName+"' , '"+foreignTableName+"'.");
            }
        }else{
            throw new InvalidValueException("El nombre de la tabla origen y la de destino son iguales. Tabla = "+tableName);
        }
    } 
    private void removeForeignKey(RemoveForeignKeyTag removeFKTag) throws Exception{
        String tableName=removeFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME);
        String columnName=removeFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME);
        this.removeForeignKey(tableName, columnName, true);
    } 
    
    /**
     * Borra un FK
     * @param tableName nombre de la tabla
     * @param columnName nombre de la columna
     * @param exceptionIfFKNotFound si es true, tira una Exception si no se encuentra el FK
     * @throws Exception
     */
    private void removeForeignKey(String tableName, String columnName, boolean exceptionIfFKNotFound) throws Exception{
        Table table=null;
        Column column=null;
        for(Table t :tables){//carga los valores de los objetos
            if(t.getName().equals(tableName)){
                table=t;
                for(Column c: t.getColumns()){
                    if(c.getName().equals(columnName)){
                        column=c;
                        break;
                    }
                }
            }
            if(null != column){
                break;
            }
        }
        if(!(null == column || null == table)){
            table.removeForeignKeyByColumnName(column.getName());
        }else{
            if(exceptionIfFKNotFound){
                throw new InvalidForeignKeyException("Alguno de estos valores no ha sido encontrado. Favor verificar los nombres. Columna : '"+columnName+"' de la tabla : '"+tableName+"'.");
            }
        }
    } 
    private void editForeignKey(EditForeignKeyTag editFKTag) throws Exception{
        String fkName=editFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_NEW_FK_NAME);
        if(null==fkName){//si no se edito el nombre del fk
            fkName=editFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_FK_NAME);
        }
        String tableName=editFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME);
        String columnName=editFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME);
        String foreignTableName=editFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_FOREIGN_TABLE_NAME);
        String foreignColumnName=editFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_FOREIGN_COLUMN_NAME);
        long id=Long.parseLong(editFKTag.getProperties().getPropertyValueByKey(Constants.PROPERTY_ID));
        if(!tableName.equals(foreignTableName)){//valida que el nombre de la tabla de origen y destino no sean iguales           
            Table foreignTable=null;
            Table table=null;
            Column foreignColumn=null;
            Column column=null;
            for(Table t :tables){//carga los valores de los objetos
                if(t.getName().equals(tableName)){
                    table=t;
                    for(Column c: t.getColumns()){
                        if(c.getName().equals(columnName)){
                            column=c;
                            break;
                        }
                    }
                }else{
                    if(t.getName().equals(foreignTableName)){
                        foreignTable=t;
                        for(Column c: t.getColumns()){
                            if(c.getName().equals(foreignColumnName)){
                                foreignColumn=c;
                                break;
                            }
                        }
                    }
                }
                if(null != foreignColumn && null != column){
                    break;
                }
            }
            if(!(null == foreignColumn || null == column || null == foreignTable || null == table || null == fkName)){
                ForeignKey fkey=new ForeignKey(id, fkName, table, column, foreignTable, foreignColumn);
                table.setForeignKeyByColumnName(columnName, fkey); 
                //System.out.println("FK con id = "+fkey.getInternalId()+" fue editado correctamenteen el objeto database.");
            }else{
                throw new InvalidForeignKeyException("Alguno de estos valores no han sido encontrados. Favor verificar los nombres. FKName : '"+fkName+"'. Columnas : '"+columnName+"' , '"+foreignColumnName+"'. Tablas : '"+tableName+"' , '"+foreignTableName+"'.");
            }
        }else{
            throw new InvalidValueException("El nombre de la tabla origen y la de destino son iguales. Tabla = "+tableName);
        }
    } 
    /**
     * Genera el archivo xml.
     * @throws Exception 
     */
    public void generateXMLFile() throws Exception{
        this.changeLog.generateXMLFile();
    }
    /**
     * Inserta datos a la tabla
     * @param insertDataTag
     * @throws Exception 
     */
    private void insertData(InsertDataTag insertDataTag) throws Exception{
        DataRow dataRow=(DataRow)insertDataTag.exportDBObject();
        String tableName=dataRow.getTableName();
        for(Table t:tables){
            if(t.getName().equals(tableName)){
                t.addRow(dataRow);
            }
        }
    }
    /**
     * Edita datos a la tabla
     * @param editDataTag
     * @throws Exception 
     */
    private void editData(EditDataTag editDataTag) throws Exception{
        EditData edit=(EditData)editDataTag.exportDBObject();
        String tableName=edit.getTableName();
        for(Table t:tables){
            if(t.getName().equals(tableName)){
                for(DataRow d:t.getRows()){
                    if(d.doesInternalIdMatches(edit.getInternalId())){
                        d.replaceValues(edit.getDataColumns());
                    }
                }
            }
        }
    }
    /**
     * Elimina datos a la tabla
     * @param removeDataTag
     * @throws Exception 
     */
    private void removeData(RemoveDataTag removeDataTag) throws Exception{
        RemoveData remove=(RemoveData)removeDataTag.exportDBObject();
        String tableName=remove.getTableName();
        for(Table t:tables){
           // System.out.println("Cantidad de rows antes de borrar "+t.getRows().size());
            if(t.getName().equals(tableName)){
                /*Iterator<DataRow> i = t.getRows().iterator();//uso iterator para poder borrar directamente el elemento de la lista donde estoy iterando
                while (i.hasNext()) {
                    DataRow d=i.next();
                    if(d.doesInternalIdMatches(remove.getInternalId())){
                        i.remove();
                    }
                }*/
                t.addRemovedRow(t.getDataRowByInternalId(remove.getInternalId()));
                t.removeRow(remove.getInternalId());
                
            }
            //System.out.println("Cantidad de rows despues de borrar "+t.getRows().size());
        }
    }
    /**
     * Hace que toda la columna tenga valores nulos
     * @param setNullDataToColumn
     * @throws Exception 
     */
    private void setNullDataToColumn(SetNullDataToColumnTag setNullDataToColumnTag) throws Exception{
        SetNullDataToColumn setNull=(SetNullDataToColumn)setNullDataToColumnTag.exportDBObject();
        String tableName=setNull.getTableName();
        String col=setNull.getColumnName();
        for(Table t:tables){
            if(t.getName().equals(tableName)){
                Iterator<DataRow> i = t.getRows().iterator();//uso iterator para poder borrar directamente el elemento de la lista donde estoy iterando
                while (i.hasNext()) {
                    i.next().replaceDataToNull(col);//DataRow.replaceDataToNull(col);
                }
            }
           // System.out.println("Terminó de setear a null todos los datos de la columna: "+col+" de la tabla: "+tableName);
        }
    }
    /**
     * Restaura datos de la tabla
     * @param restoreDataTag
     * @throws Exception 
     */
    private void restoreData(RestoreDataTag restoreDataTag) throws Exception{
        RestoreDataRow restore=(RestoreDataRow)restoreDataTag.exportDBObject();
        String tableName=restore.getTableName();
        
        for(Table t:tables){
            //System.out.println("Cantidad de rows antes de restaurar "+t.getRows().size());
            if(t.getName().equals(tableName)){
                //primero borra el registro de removedRows
                t.removeRemovedRow(restore.getOldInternalId());
                //luego vuelvo a insertar el registro
                t.addRow(restore.getDataRow());
            }
            //System.out.println("Cantidad de rows despues de restaurar "+t.getRows().size());
        }
    }
}
