package org.tesis.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.ColumnTag;
import org.tesis.changelog.tag.NewTableTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;
import org.tesis.mybase.ColumnList;

/*
 * Es la clase que utilizará el JAXB para mapear el tag NewTable.
 */
//@XmlRootElement(namespace = "org.tesis.jaxb.JXChangeLog")
//@XmlRootElement(name="NewTable")//requerido para que muestre este nombre al exportar solo este tag
@XmlType(name="NewTable", propOrder = {"name", "PKName","columns"})
public class JXNewTableTag extends JXTag{
    private String name;
    private String PKName;
    private List<JXColumnTag> jxcolumns;

    public JXNewTableTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXNewTableTag(String id, String author, String date) {
        super(id, author, date);
        this.jxcolumns = new ArrayList<>();
    }

    public JXNewTableTag(String id, String author, String date, String name, String pkName, List<JXColumnTag> jxcolumns) {
        super(id, author, date);
        this.name = name;
        this.jxcolumns = jxcolumns;
        this.PKName=pkName;
    }
    
    public JXNewTableTag(String id, String author, String date, String name, String pkName) {
        super(id, author, date);
        this.name = name;
        this.PKName=pkName;
    }
    
    public String getName() {
        return name;
    }
    
    public void addColumn(JXColumnTag column){
        this.jxcolumns.add(column);
    }
    
    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
    }

    public String getPKName() {
        return PKName;
    }
    
    @XmlElement
    public void setPKName(String PKName) {
        this.PKName = PKName;
    }
    
    public List<JXColumnTag> getColumns() {
        return jxcolumns;
    }
    
    @XmlElementWrapper//(name = "columns") name no es necesario, toma el nombre del metodo sin el set
    @XmlElement(name="Column")//si es necesario el nombre
    public void setColumns(List<JXColumnTag> jxcolumns) {
        this.jxcolumns = jxcolumns;
    }
    
    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_NAME, this.getName());
        pl.addProperty(Constants.PROPERTY_PK_NAME, this.getPKName());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        ColumnList cl=new ColumnList();
        for(JXColumnTag c:this.jxcolumns){
            cl.addColumn((ColumnTag)c.exportTagObject(dbms));
        }
        return new NewTableTag(pl, cl, dbms);
    }
}
