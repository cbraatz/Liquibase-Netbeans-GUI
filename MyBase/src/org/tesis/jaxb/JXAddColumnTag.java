package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.AddColumnTag;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear el tag AddColumn.
 */
@XmlType(name="AddColumn",propOrder = {"table","name", "type", "lenght", "pk","nullable"})
public class JXAddColumnTag extends JXTag{
    private String type;
    private String lenght;
    private String name;
    private String nullable;
    private String pk;
    private String table;
    public JXAddColumnTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
        super();
    }

    public JXAddColumnTag(String id) {//column no es obligatorio que tenga autor porque pertenece a un tag que si debe tener autor
        super(id);
    }
    
    public JXAddColumnTag(String id, String author, String date, String tableName, String name, String type, String lenght, String nullable, String pk) {
        super(id, author, date);
        this.type = type;
        this.lenght = lenght;
        this.name = name;
        this.nullable=nullable;
        this.pk=pk;
        this.table=tableName;
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

    public String getTable() {
        return table;
    }
    @XmlElement(name="table")
    public void setTable(String table) {
        this.table = table;
    }
    
    @Override
    public Tag exportTagObject(Dbms dbms)throws Exception{
        PropertyList pl=new PropertyList();
        pl.addProperty(Constants.PROPERTY_ID, this.getId());
        pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor());
        pl.addProperty(Constants.PROPERTY_DATE, this.getDate());
        pl.addProperty(Constants.PROPERTY_NAME, this.getName());
        pl.addProperty(Constants.PROPERTY_TABLE_NAME, this.getTable());
        pl.addProperty(Constants.PROPERTY_TYPE, this.getType());
        if(null!=this.getLenght()){
            pl.addProperty(Constants.PROPERTY_LENGHT, this.getLenght());
        }
        if(null!=this.getNullable()){
            pl.addProperty(Constants.PROPERTY_NULLABLE, this.getNullable());
        }
        if(null!=this.getPk()){
            pl.addProperty(Constants.PROPERTY_PRIMARY_KEY, this.getPk());
        }
        return new AddColumnTag(pl, dbms);
    }
}
