package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.RemoveForeignKeyTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag RemoveForeignKey.
 */
@XmlType(name="RemoveFK", propOrder = {"table", "column", "FKName"})
public class JXRemoveForeignKeyTag extends JXTag{
    private String column;
    private String table;
    private String FKName;
    public JXRemoveForeignKeyTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXRemoveForeignKeyTag(String id, String author, String date, String tableName, String columnName, String FKName) {
        super(id, author, date);
        this.column = columnName;
        this.table=tableName;
        this.FKName=FKName;
    }
    
    public String getColumn() {
        return column;
    }
    
    @XmlElement
    public void setColumn(String columnName) {
        this.column = columnName;
    }

    public String getTable() {
        return table;
    }
    @XmlElement
    public void setTable(String tableName) {
        this.table = tableName;
    }

    public String getFKName() {
        return FKName;
    }
    @XmlElement
    public void setFKName(String FKName) {
        this.FKName = FKName;
    }

    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_COLUMN_NAME, this.getColumn());
        pl.addProperty(Constants.PROPERTY_TABLE_NAME, this.getTable());
        pl.addProperty(Constants.PROPERTY_FK_NAME, this.getFKName());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        return new RemoveForeignKeyTag(pl, dbms);
    }
}
