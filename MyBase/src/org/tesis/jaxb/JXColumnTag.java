package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.ColumnTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag Column.
 */
//@XmlRootElement(name="Column")
@XmlType(name="Column",propOrder = {"name", "type", "lenght", "pk","nullable"})
public class JXColumnTag extends JXTag{
    private String type;
    private String lenght;
    private String name;
    private String nullable;
    private String pk;
    public JXColumnTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
        super();
    }

    public JXColumnTag(String id) {//column no es obligatorio que tenga autor porque pertenece a un tag que si debe tener autor
        super(id);
    }
    
    public JXColumnTag(String id, String name, String type, String lenght, String nullable, String pk) {
        super(id);
        this.type = type;
        this.lenght = lenght;
        this.name = name;
        this.nullable=nullable;
        this.pk=pk;
    }
    
    public String getType() {
        return type;
    }
    
    @XmlElement(name="type")
    public void setType(String type) {
        this.type = type;
    }

    public String getLenght() {
        return lenght;
    }
    @XmlElement(name="lenght")
    public void setLenght(String lenght) {
        this.lenght = lenght;
    }

    public String getName() {
        return name;
    }
    @XmlElement(name="name")
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNullable() {
        return nullable;
    }
    
    public String getPk() {
        return pk;
    }
    @XmlElement(name="nullable")
    public void setNullable(String nullable) {
        this.nullable = nullable;
    }
    @XmlElement(name="pk")
    public void setPk(String pk) {
        this.pk = pk;
    }
    
    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        if(null!=this.getId()){
            pl.addProperty(Constants.PROPERTY_ID, this.getId());
        }
        if(null!=this.getName()){
            pl.addProperty(Constants.PROPERTY_NAME, this.getName());
        }
        if(null!=this.getType()){
            pl.addProperty(Constants.PROPERTY_TYPE, this.getType());
        }
        if(null!=this.getLenght()){
            pl.addProperty(Constants.PROPERTY_LENGHT, this.getLenght());
        }
        if(null!=this.getNullable()){
            pl.addProperty(Constants.PROPERTY_NULLABLE, this.getNullable());
        }
        if(null!=this.getPk()){
            pl.addProperty(Constants.PROPERTY_PRIMARY_KEY, this.getPk());
        }
        return new ColumnTag(pl, dbms);
    }
    
}
