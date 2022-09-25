package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.RenameTableTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag RenameTable.
 */
@XmlType(name="RenameTable", propOrder = {"name", "oldName"})
public class JXRenameTableTag extends JXTag{
    private String name;
    private String oldName;

    public JXRenameTableTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXRenameTableTag(String id, String author, String date, String newName, String oldName) {
        super(id, author, date);
        this.name = newName;
        this.oldName = oldName;
    }
    
    public String getName() {
        return name;
    }
    
    @XmlElement
    public void setName(String newName) {
        this.name = newName;
    }

    public String getOldName() {
        return oldName;
    }
    @XmlElement
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_NAME, this.getName());
        pl.addProperty(Constants.PROPERTY_OLD_NAME, this.getOldName());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        return new RenameTableTag(pl, dbms);
    }
}
