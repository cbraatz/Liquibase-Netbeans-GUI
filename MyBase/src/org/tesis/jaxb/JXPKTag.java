package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.PKTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag PK.
 */
//@XmlRootElement(name="Column")
@XmlType(name="PK",propOrder = {"columnName", "value", "quoted"})
public class JXPKTag extends JXTag{
    private String columnName;
    private String value;
    private String quoted;

    public JXPKTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
        super();
    }

    public JXPKTag(String id) {//column no es obligatorio que tenga autor porque pertenece a un tag que si debe tener autor
        super(id);
    }
    
    public JXPKTag(String id, String columnName, String value, String quoted) {
        super(id);
        this.columnName = columnName;
        this.value = value;
        this.quoted = quoted;
    }

    public String getColumnName() {
        return columnName;
    }
    @XmlElement
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getValue() {
        return value;
    }
    @XmlElement
    public void setValue(String value) {
        this.value = value;
    }

    public String getQuoted() {
        return quoted;
    }
    @XmlElement
    public void setQuoted(String quoted) {
        this.quoted = quoted;
    }
    
    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        if(null!=this.getId()){
            pl.addProperty(Constants.PROPERTY_ID, this.getId());
        }
        if(null!=this.getColumnName()){
            pl.addProperty(Constants.PROPERTY_COLUMN_NAME, this.getColumnName());
        }
        if(null!=this.getValue()){
            pl.addProperty(Constants.PROPERTY_VALUE, this.getValue());
        }
        if(null!=this.getQuoted()){
            pl.addProperty(Constants.PROPERTY_QUOTED, this.getQuoted());
        }
        return new PKTag(pl, dbms);
    }
    
}
