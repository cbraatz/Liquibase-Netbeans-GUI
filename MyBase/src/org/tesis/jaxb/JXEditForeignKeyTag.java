package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.EditForeignKeyTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag EditFK.
 */
@XmlType(name="editFK", propOrder = {"table", "column", "foreignTable", "foreignColumn", "FKName", "newFKName", "oldColumn", "oldForeignTable", "oldForeignColumn"})
public class JXEditForeignKeyTag extends JXTag{
    private String table;
    private String column;
    private String foreignTable;
    private String foreignColumn;
    private String FKName;
    private String oldColumn;
    private String oldForeignTable;
    private String oldForeignColumn;
    private String newFKName;
    public JXEditForeignKeyTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXEditForeignKeyTag(String id, String author, String date, String tableName, String columnName, String foreignTableName, String foreignColumnName, String FKName, String newFKName, String oldColumnName, String oldForeignTableName, String oldForeignColumnName) {
        super(id, author, date);
        this.table=tableName;
        this.column=columnName;
        this.foreignTable=foreignTableName;
        this.foreignColumn=foreignColumnName;
        this.FKName=FKName;
        this.oldColumn=oldColumnName;
        this.oldForeignTable=oldForeignTableName;
        this.oldForeignColumn=oldForeignColumnName;
        this.newFKName=newFKName;
    }

    public String getTable() {
        return table;
    }
    @XmlElement
    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }
    @XmlElement
    public void setColumn(String column) {
        this.column = column;
    }

    public String getFKName() {
        return FKName;
    }
    @XmlElement
    public void setFKName(String FKName) {
        this.FKName = FKName;
    }

    public String getForeignTable() {
        return foreignTable;
    }
    @XmlElement
    public void setForeignTable(String foreignTable) {
        this.foreignTable = foreignTable;
    }

    public String getForeignColumn() {
        return foreignColumn;
    }
    @XmlElement
    public void setForeignColumn(String foreignColumn) {
        this.foreignColumn = foreignColumn;
    }

    public String getOldColumn() {
        return oldColumn;
    }
    @XmlElement
    public void setOldColumn(String oldColumn) {
        this.oldColumn = oldColumn;
    }

    public String getOldForeignTable() {
        return oldForeignTable;
    }
    @XmlElement
    public void setOldForeignTable(String oldForeignTable) {
        this.oldForeignTable = oldForeignTable;
    }

    public String getOldForeignColumn() {
        return oldForeignColumn;
    }
    @XmlElement
    public void setOldForeignColumn(String oldForeignColumn) {
        this.oldForeignColumn = oldForeignColumn;
    }

    public String getNewFKName() {
        return newFKName;
    }
    @XmlElement
    public void setNewFKName(String newFKName) {
        this.newFKName = newFKName;
    }
    
    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_TABLE_NAME, this.getTable());
        pl.addProperty(Constants.PROPERTY_COLUMN_NAME, this.getColumn());
        pl.addProperty(Constants.PROPERTY_FK_NAME, this.getFKName());
        pl.addProperty(Constants.PROPERTY_FOREIGN_TABLE_NAME, this.getForeignTable());
        pl.addProperty(Constants.PROPERTY_FOREIGN_COLUMN_NAME, this.getForeignColumn());
        pl.addProperty(Constants.PROPERTY_OLD_COLUMN_NAME, this.getOldColumn());
        pl.addProperty(Constants.PROPERTY_NEW_FK_NAME, this.getNewFKName());
        pl.addProperty(Constants.PROPERTY_OLD_FOREIGN_TABLE_NAME, this.getOldForeignTable());
        pl.addProperty(Constants.PROPERTY_OLD_FOREIGN_COLUMN_NAME, this.getOldForeignColumn());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        return new EditForeignKeyTag(pl, dbms);
    }
}
