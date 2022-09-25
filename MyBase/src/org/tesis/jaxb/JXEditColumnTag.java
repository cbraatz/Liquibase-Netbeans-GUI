package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.EditColumnTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag EditColumn.
 */
@XmlType(name="EditColumn", propOrder = {"tableName","columnName","newName","oldType","type","oldLenght","lenght"})
public class JXEditColumnTag extends JXTag{
    private String tableName;
    private String columnName;
    private String newName;
    private String oldType;
    private String type;
    private String oldLenght;
    private String lenght;
    
    public JXEditColumnTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXEditColumnTag(String id, String author, String date, String tableName, String columnName, String newName, String oldType, String type, String oldLenght, String lenght) {
        super(id, author, date);
        this.columnName = columnName;
        this.newName = newName;
        this.tableName=tableName;
        this.oldType=oldType;
        this.type=type;
        this.oldLenght=oldLenght;
        this.lenght=lenght;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    @XmlElement
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getNewName() {
        return newName;
    }
    @XmlElement
    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getTableName() {
        return tableName;
    }
    @XmlElement
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOldType() {
        return oldType;
    }
    @XmlElement
    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public String getType() {
        return type;
    }
    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    public String getOldLenght() {
        return oldLenght;
    }
    @XmlElement
    public void setOldLenght(String oldLenght) {
        this.oldLenght = oldLenght;
    }

    public String getLenght() {
        return lenght;
    }
    @XmlElement
    public void setLenght(String lenght) {
        this.lenght = lenght;
    }

    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        pl.addProperty(Constants.PROPERTY_TABLE_NAME, this.getTableName());
        pl.addProperty(Constants.PROPERTY_NEW_NAME, this.getNewName());
        pl.addProperty(Constants.PROPERTY_COLUMN_NAME, this.getColumnName());
        pl.addProperty(Constants.PROPERTY_LENGHT, this.getLenght());
        pl.addProperty(Constants.PROPERTY_OLD_LENGHT, this.getOldLenght());
        pl.addProperty(Constants.PROPERTY_TYPE, this.getType());
        pl.addProperty(Constants.PROPERTY_OLD_TYPE, this.getOldType());
        return new EditColumnTag(pl, dbms);
    }
}
