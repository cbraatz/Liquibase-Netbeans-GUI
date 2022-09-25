package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.RemoveColumnTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag RemoveColumn.
 */
@XmlType(name="RemoveColumn", propOrder = {"table", "name"})
public class JXRemoveColumnTag extends JXTag{
    private String name;
    private String table;
    public JXRemoveColumnTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXRemoveColumnTag(String id, String author, String date, String table, String name) {
        super(id, author, date);
        this.name = name;
        this.table=table;
    }
    
    public String getName() {
        return name;
    }
    
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }
    @XmlElement
    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_NAME, this.getName());
        pl.addProperty(Constants.PROPERTY_TABLE_NAME, this.getTable());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        return new RemoveColumnTag(pl, dbms);
    }
}
