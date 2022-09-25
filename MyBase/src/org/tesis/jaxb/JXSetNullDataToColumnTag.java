package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.EditColumnNullableTag;
import org.tesis.changelog.tag.SetNullDataToColumnTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag EditColumnNullable.
 */
@XmlType(name="SetDataNullValue", propOrder = {"tableName","columnName","type","lenght"})
public class JXSetNullDataToColumnTag extends JXTag{
    private String tableName;
    private String columnName;
    private String type;
    private String lenght;
    
    public JXSetNullDataToColumnTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXSetNullDataToColumnTag(String id, String author, String date, String tableName, String columnName, String type, String lenght) {
        super(id, author, date);
        this.columnName = columnName;
        this.tableName=tableName;
        this.type=type;
        this.lenght=lenght;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    @XmlElement
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }
    @XmlElement
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getType() {
        return type;
    }
    @XmlElement
    public void setType(String type) {
        this.type = type;
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
        pl.addProperty(Constants.PROPERTY_COLUMN_NAME, this.getColumnName());
        pl.addProperty(Constants.PROPERTY_LENGHT, this.getLenght());
        pl.addProperty(Constants.PROPERTY_TYPE, this.getType());
        return new SetNullDataToColumnTag(pl, dbms);
    }
}
