package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.RemoveDataTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag RemoveData.
 */
@XmlType(name="RemoveData", propOrder = {"internalId","tableName"})
public class JXRemoveDataTag extends JXTag{
    private String tableName;
    private String internalId;
    
    public JXRemoveDataTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXRemoveDataTag(String id, String author, String date) {
        super(id, author, date);
    }

    public JXRemoveDataTag(String id, String author, String date, String tableName, String internalId) {
        super(id, author, date);
        this.tableName = tableName;
        this.internalId = internalId;
    }
    
    public JXRemoveDataTag(String id, String author, String date, String tableName) {
        super(id, author, date);
        this.tableName = tableName;
    }
  
    public String getTableName() {
        return tableName;
    }
    
    @XmlElement
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getInternalId() {
        return this.internalId;
    }
    @XmlElement(name="internalId")
    public void setInternalId(String internalId){
        this.internalId = internalId;
    }
    
    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_TABLE_NAME, this.getTableName());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        pl.addProperty(Constants.PROPERTY_INTERNAL_ID, this.getInternalId());
        return new RemoveDataTag(pl, dbms);
    }
}
