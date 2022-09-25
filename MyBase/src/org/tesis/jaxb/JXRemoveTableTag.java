package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.RemoveTableTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag RemoveTable.
 */
@XmlType(name="RemoveTable")
public class JXRemoveTableTag extends JXTag{
    private String name;

    public JXRemoveTableTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXRemoveTableTag(String id, String author, String date, String name) {
        super(id, author, date);
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_NAME, this.getName());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        return new RemoveTableTag(pl, dbms);
    }
}
