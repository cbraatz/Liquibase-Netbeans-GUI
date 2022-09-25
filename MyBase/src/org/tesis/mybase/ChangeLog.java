package org.tesis.mybase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.NotUniqueIDException;
import org.tesis.jaxb.JXChangeLog;
import org.tesis.jaxb.JXHeader;
import org.tesis.jaxb.JXTag;
import org.tesis.util.MyBaseLogger;

/**
 * Esta clase es el equivalente a un archivo changeLog, es decir que ambos están constituídos por una lista de tags.
 * @author Claus
 */
public class ChangeLog {
    private List<Tag> tags;
    private ChangeLogHeader header;
    private List<String> usedIds=new ArrayList();//lista de ids usados hasta el momento
    private String changeLogPath;
    public ChangeLog(String path, Dbms dbms) throws Exception {
        this.tags=new ArrayList<>();
        this.changeLogPath=path;
        JXChangeLog jxChangeLog=this.getJXChangeLogFromXMLFile();
        this.validateAndSetTagsList(jxChangeLog,dbms);//transforma un jxChangeLog a ChangeLog y valida que no se repitan los ids y nombres en caso de tablas
        this.header=new ChangeLogHeader(jxChangeLog.getHeader().getProject(),jxChangeLog.getHeader().getAuthor(),jxChangeLog.getHeader().getDate());
    }
    /**
     * Retorna si se pudo agregar el tag a la lista de tags
     * @param tag es el tag a agregar   
     * @return si se pudo agregar
     * @throws NotUniqueIDException 
     */
    public void addTag(Tag tag) throws Exception{
        for(String s : this.usedIds) {
            for(String s2 : tag.getIDsList()){
                if(s.equals(s2)){
                    NotUniqueIDException ex= new NotUniqueIDException("Error al agregar tag. El ID = "+s2+" ya ha sido agregado por un tag previo.");
                    MyBaseLogger.LogWarningMessage(ex.getMessage(), "ChangeLog");
                    throw ex;
                }
            }
        }
        /*if(tag.hasUniqueName()){ //ver como implementar esto sin tanto hack
            for(String n : this.usedNames) {
                if(n.equals(tag.getPropertyValueByKey(Constants.PROPERTY_NAME))){
                    NotUniqueNameException ex= new NotUniqueNameException("Error al agregar tag con ID = "+tag.getPropertyValueByKey(Constants.PROPERTY_ID)+". El NAME = "+n+" ya ha sido por un tag previo.");
                    MyBaseLogger.LogWarningMessage(ex.getMessage(), "ChangeLog");
                    throw ex;
                }
            }
            this.usedNames.add(tag.getPropertyValueByKey(Constants.PROPERTY_NAME));
        }*/
        this.usedIds.addAll(tag.getIDsList());
        this.tags.add(tag);
    }
    /**
     * Borra un tag de la lista y libera los ids y el nombre que ha utilizado para que puedan volver a usarse
     * @param tag tag a quitar de la lista
     */
    /*public void removeTag(Tag tag){
        for(String s : tag.getIDsList()) {
            for(String s2 : this.usedIds){
                if(s.equals(s2)){
                    this.usedIds.remove(s);
                }
            }
        }
        if(tag.hasUniqueName()&&tag.addsNewDbObject()){//solo si tiene nombre unico y si a traves de este tag se agrega nuevo objeto hay que borrarlo.
            this.usedNames.remove(tag.getPropertyValueByKey(Constants.PROPERTY_NAME));
        }
    }*/
    /**
     * Retorna la lista de tags.
     * @return la lista de tags.
     */
    public List<Tag> getTags() {
        return tags;
    }
    
    public boolean isEmpty(){
        return this.tags.isEmpty();
    }
    /**
     * retorna el tag por id
     * @param id el id del tag buscado
     * @return el tag identificado por id
     */
    public Tag getTagById(String id){
        for(Tag t : tags) {
            if(t.getId().equals(id)){
                return t;
            }
        }
        return null;
    }

    public String getChangeLogPath() {
        return changeLogPath;
    }
    
    public List<SqlQuery> getSqlQueries() throws Exception{
        List<SqlQuery> li=new ArrayList<>();
        for(Tag t: tags){
            li.addAll(t.exportSQLQuery());
        }
        return li;
    }
    /**
     * este metodo lo que hace es leer la lista de JXTags del JXChangeLog y los valida y convierte a una lista de Tags
     * que es atributo de esta clase.
     */
    private void validateAndSetTagsList(JXChangeLog jxChangeLog,Dbms dbms) throws Exception{
        this.tags=new ArrayList<>();
        for(JXTag t:jxChangeLog.getTags()){
            Tag tag=t.exportTagObject(dbms);
            this.addTag(tag);
        }
    }
    /**
     * Genera el archivo xml.
     * @throws Exception 
     */
    public void generateXMLFile() throws Exception{
        List<JXTag> jxTags=new ArrayList<>();
        for(Tag t:this.tags){
            jxTags.add(t.exportJXTag());
        }
        new JXChangeLog(new JXHeader(this.header.getProjectName(),this.header.getAuthor(),this.header.getCreationDate()), jxTags).generateXmlFile(this.changeLogPath);
    }
    
    /**
     * Obtiene este objeto JXChangeLog a partir del xml
     * @return JXChangeLog
     * @throws javax.xml.bind.JAXBException
     */
    public JXChangeLog getJXChangeLogFromXMLFile()throws JAXBException{
         // crea el contexto de JAXB e inicializa el Unmarshaller
        JAXBContext jaxbContext = JAXBContext.newInstance(JXChangeLog.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        //especifica la dirección del archivo xml de entrada
        File XMLfile = new File(this.changeLogPath);

        // crea un objeto Java del tipo JXChangeLog a partir del xml
        try{
            return (JXChangeLog) jaxbUnmarshaller.unmarshal(XMLfile);
        }catch(JAXBException e){
            throw new JAXBException("Error al leer el Change-Log; posiblemente se deba a una estructura incorrecta del archivo o el mismo esté vacío.",e);
        }
    }
}