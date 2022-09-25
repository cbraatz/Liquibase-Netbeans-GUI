package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.EditTablePKTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag RenameTable.
 */
@XmlType(name="EditTablePK", propOrder = {"tableName", "PKName", "oldPKs", "PKs"})
public class JXEditTablePKTag extends JXTag{
    private String tableName;
    private String PKName;
    private String oldPKs;
    private String PKs;
    public JXEditTablePKTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXEditTablePKTag(String id, String author, String date, String tableName, String PKName, String oldPKs, String PKs) {
        super(id, author, date);
        this.tableName=tableName;
        this.PKName=PKName;
        this.PKs=PKs;
        this.oldPKs=oldPKs;
    }

    public String getTableName() {
        return tableName;
    }
    
    @XmlElement
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getOldPKs() {
        return oldPKs;
    }

    @XmlElement
    public void setOldPKs(String oldPKs) {
        this.oldPKs = oldPKs;
    }

    public String getPKs() {
        return PKs;
    }

    @XmlElement
    public void setPKs(String PKs) {
        this.PKs = PKs;
    }

    public String getPKName() {
        return PKName;
    }
    @XmlElement
    public void setPKName(String PKName) {
        this.PKName = PKName;
    }

    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        pl.addProperty(Constants.PROPERTY_TABLE_NAME, this.getTableName());
        pl.addProperty(Constants.PROPERTY_PK_NAME, this.getPKName());
        pl.addProperty(Constants.PROPERTY_OLD_PKS, this.getOldPKs());
        pl.addProperty(Constants.PROPERTY_PKS, this.getPKs());
        return new EditTablePKTag(pl, dbms);
    }
}
