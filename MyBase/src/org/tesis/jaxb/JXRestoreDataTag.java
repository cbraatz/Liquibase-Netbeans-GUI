package org.tesis.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.DataColumnTag;
import org.tesis.changelog.tag.RestoreDataTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;
import org.tesis.mybase.DataColumnList;

/*
 * Es la clase que utilizará el JAXB para mapear el tag RestoreData.
 */
//@XmlRootElement(namespace = "org.tesis.jaxb.JXChangeLog")
//@XmlRootElement(name="NewTable")//requerido para que muestre este nombre al exportar solo este tag
@XmlType(name="RestoreData", propOrder = {"oldInternalId", "tableName", "columns"})
public class JXRestoreDataTag extends JXTag{
    private String tableName;
    private String oldInternalId;
    private List<JXDataColumnTag> jxDataColumns;

    public JXRestoreDataTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXRestoreDataTag(String id, String author, String date, String oldId) {
        super(id, author, date);
        oldInternalId=oldId;
        this.jxDataColumns = new ArrayList<>();
    }

    public JXRestoreDataTag(String id, String author, String date, String tableName, String oldId, List<JXDataColumnTag> jxDataColumns) {
        super(id, author, date);
        this.tableName = tableName;
        oldInternalId=oldId;
        this.jxDataColumns = jxDataColumns;
    }
    
    public JXRestoreDataTag(String id, String author, String date, String tableName, String oldId) {
        super(id, author, date);
        oldInternalId=oldId;
        this.tableName = tableName;
    }
    
    public String getTableName() {
        return tableName;
    }
    public String getInternalId(){
        return this.getId();
    }
    public void addColumn(JXDataColumnTag dataColumn){
        this.jxDataColumns.add(dataColumn);
    }

    public String getOldInternalId() {
        return oldInternalId;
    }
    
    @XmlElement
    public void setOldInternalId(String oldInternalId) {
        this.oldInternalId = oldInternalId;
    }
    
    @XmlElement
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<JXDataColumnTag> getColumns() {
        return jxDataColumns;
    }
    
    @XmlElementWrapper//(name = "columns") name no es necesario, toma el nombre del metodo sin el set
    @XmlElement(name="data-col")//si es necesario el nombre
    public void setColumns(List<JXDataColumnTag> jxDataColumns) {
        this.jxDataColumns = jxDataColumns;
    }
    
    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_OLD_INTERNAL_ID, this.getOldInternalId());
        pl.addProperty(Constants.PROPERTY_TABLE_NAME, this.getTableName());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        DataColumnList cl=new DataColumnList();
        for(JXDataColumnTag c:this.jxDataColumns){
            cl.addDataColumn((DataColumnTag)c.exportTagObject(dbms));
        }
        return new RestoreDataTag(pl, cl, dbms);
    }
}
