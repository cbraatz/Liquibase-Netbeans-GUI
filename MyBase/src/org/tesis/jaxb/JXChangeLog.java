package org.tesis.jaxb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/*
 * Es la clase que utilizará el JAXB para mapear todo el change-log.
 */
@XmlRootElement(name="MyBaseChangeLog")//define el nombre del tag principal
@XmlType(propOrder = {"header", "tags"})//Opcional
public class JXChangeLog {
    private JXHeader header=new JXHeader();
    private List<JXTag> jxtags=new ArrayList<>();
    
    public JXChangeLog() {
    }

    public JXChangeLog(JXHeader header) {
        if(null!=header){//no tengo idea de xq pero a veces de solo setea null
            this.header = header;
        }else{
            System.err.println("Se intentó setear un header nulo en el constructor");
        }
    }
    public JXChangeLog(JXHeader header, List<JXTag> jxtags) {
        this.jxtags=jxtags;
        if(null!=header){//no tengo idea de xq pero a veces de solo setea null
            this.header = header;
        }else{
            System.err.println("Se intentó setear un header nulo  en el constructor 2");
        }
    }
    
    public List<JXTag> getTags() {
        return jxtags;
    }
     
    @XmlElementWrapper//(name = "tags") name no es necesario, toma el nombre del metodo sin el set
    @XmlElements({  // XmlElement setea el nombre de los entities en la colection.
        @XmlElement(name="Tag",type=JXTag.class),//nunca se usa este
        @XmlElement(name="NewTable",type=JXNewTableTag.class),
        @XmlElement(name="RenameTable",type=JXRenameTableTag.class),
        @XmlElement(name="RemoveTable",type=JXRemoveTableTag.class),
        @XmlElement(name="EditColumn",type=JXEditColumnTag.class),
        @XmlElement(name="EditColumnNullable",type=JXEditColumnNullableTag.class),
        @XmlElement(name="EditTablePK",type=JXEditTablePKTag.class),
        @XmlElement(name="AddForeignKey",type=JXAddForeignKeyTag.class),
        @XmlElement(name="RemoveForeignKey",type=JXRemoveForeignKeyTag.class),
        @XmlElement(name="EditForeignKey",type=JXEditForeignKeyTag.class),
        @XmlElement(name="AddColumn",type=JXAddColumnTag.class),
        @XmlElement(name="RemoveColumn",type=JXRemoveColumnTag.class),
        @XmlElement(name="AddData",type=JXDataInsertTag.class),
        @XmlElement(name="EditData",type=JXEditDataTag.class),
        @XmlElement(name="RemoveData",type=JXRemoveDataTag.class),
        @XmlElement(name="RestoreData",type=JXRestoreDataTag.class),
        @XmlElement(name="SetDataNullValue",type=JXSetNullDataToColumnTag.class)
    })
    public void setTags(List<JXTag> jxtags) {
        this.jxtags = jxtags;
    }
    public JXHeader getHeader() {
        return header;
    }
    
    public void setHeader(JXHeader header) {
        if(null!=header){//no tengo idea de xq pero a veces de solo setea null
            this.header = header;
        }else{
            System.err.println("Se intentó setear un header nulo");
        }
    }
    
    public void addNewTag(JXTag jxtag){
        this.jxtags.add(jxtag);
    }
    public void removeTag(JXTag jxtag){
        this.jxtags.remove(jxtag);
    }
    /**
     * Genera un archivo xml en base al objeto tipo JXChangeLog que se le pasa como parámetro
     * @param path dirección del archivo xml de salida
     */
   public void generateXmlFile(String path) throws Exception{
        // crea el contexto de JAXB e inicializa el Marshaller
        JAXBContext jaxbContext = JAXBContext.newInstance(JXChangeLog.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        
        // para mejorar el aspecto de la salida
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        //especifica la dirección del archivo xml de salida
        File XMLfile = new File(path);
        
        // escribe el archivo xml
        jaxbMarshaller.marshal(this, XMLfile); 
        
        // escribe a consola
        jaxbMarshaller.marshal(this, System.out); 
    }
}
