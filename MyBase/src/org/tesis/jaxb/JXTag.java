package org.tesis.jaxb;



import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.dbms.Dbms;

/*
 * Es la clase que utilizará el JAXB para mapear un Tag en el change-log.
 */
//@XmlRootElement(namespace = "org.tesis.jaxb.JXChangeLog")
@XmlType(propOrder = {"id", "author","date"})
public abstract class JXTag {
    private String id;
    private String author;
    private String date;
    public JXTag() {//solo lo creo poque sino el JAXB se queja de que no hay constructor por defecto
    }

    public JXTag(String id, String author, String date) {
        this.id = id;
        this.author = author;
        this.date=date;
    }

    public JXTag(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
    @XmlElement(name="id")
    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }
    
    @XmlElement(name="autor")
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }
    
    @XmlElement(name="date")
    public void setDate(String date) {
        this.date = date;
    }
    
    public Tag exportTagObject(Dbms dbms)throws Exception{
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
