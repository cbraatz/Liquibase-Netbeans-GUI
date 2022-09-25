package org.tesis.jaxb;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.DataColumnTag;
import org.tesis.changelog.tag.EditDataTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;
import org.tesis.mybase.DataColumnList;
import org.tesis.mybase.DataPKList;

/*
 * Es la clase que utilizará el JAXB para mapear el tag EditData.
 */
@XmlType(name="EditData", propOrder = {"internalId","tableName","dataColumns"})
public class JXEditDataTag extends JXTag{
    private String tableName;
    private String internalId;
    private List<JXDataColumnTag> dataColumns;
    
    public JXEditDataTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    /*public JXEditDataTag(String id, String author, String date) {
        super(id, author, date);
        //this.PKs = new ArrayList<>();
    }*/

    public JXEditDataTag(String id, String author, String date, String tableName, String internalId, List<JXDataColumnTag> jxDataColumns) {
        super(id, author, date);
        this.tableName = tableName;
        this.internalId = internalId;
        this.dataColumns=jxDataColumns;
    }
    
    public JXEditDataTag(String id, String author, String date, String tableName) {
        super(id, author, date);
        this.tableName = tableName;
    }
    @XmlElement(name="internalId")
    public void setInternalId(String internalId){
        this.internalId = internalId;
    }
    public void addDataColumnTag(JXDataColumnTag col){
        this.dataColumns.add(col);
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

    public List<JXDataColumnTag> getDataColumns() {
        return dataColumns;
    }
    @XmlElementWrapper
    @XmlElement(name="DataColumn")//si es necesario el nombre
    public void setDataColumns(List<JXDataColumnTag> jxDataColumns) {
        this.dataColumns = jxDataColumns;
    }
    
    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_INTERNAL_ID, this.getInternalId());
        pl.addProperty(Constants.PROPERTY_TABLE_NAME, this.getTableName());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        DataColumnList dcl=new DataColumnList();
        for(JXDataColumnTag dc:this.dataColumns){
            dcl.addDataColumn((DataColumnTag)dc.exportTagObject(dbms));
        }
        return new EditDataTag(pl, dcl, dbms);
    }
}
