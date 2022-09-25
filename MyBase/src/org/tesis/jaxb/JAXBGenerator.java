package org.tesis.jaxb;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.tesis.changelog.tag.Tag;

/**
 * Gestiona la conversiones de JAXB.
 */
public class JAXBGenerator {
    /**
     * Genera un archivo xml en base al objeto tipo JXChangeLog que se le pasa como parámetro
     * @param obj objeto tipo JXChangeLog que será convertido a xml
     * @param path dirección del archivo xml de salida
     */
   public static void generateXmlFromObject(JXChangeLog obj, String path) throws Exception{
        // crea el contexto de JAXB e inicializa el Marshaller
        JAXBContext jaxbContext = JAXBContext.newInstance(JXChangeLog.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        
        // para mejorar el aspecto de la salida
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        
        //especifica la dirección del archivo xml de salida
        File XMLfile = new File(path);
        
        // escribe el archivo xml
        jaxbMarshaller.marshal(obj, XMLfile); 
        
        // escribe a consola
        jaxbMarshaller.marshal(obj, System.out); 
    }
    
    public static JXChangeLog generateObjectFromXml(String path) throws Exception{
         // crea el contexto de JAXB e inicializa el Unmarshaller
        JAXBContext jaxbContext = JAXBContext.newInstance(JXChangeLog.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        //especifica la dirección del archivo xml de entrada
        File XMLfile = new File(path);

        // crea un objeto Java del tipo JXChangeLog a partir del xml
        JXChangeLog chl = (JXChangeLog) jaxbUnmarshaller.unmarshal(XMLfile);
        //System.out.println(chl.getTags().toString());
        
        //retorna el archivo Java
        return chl;
    }
    public static void main(String[] args){
       
           //objeto a xml
         /*     List<JXColumnTag> cols1=new ArrayList<>();
              cols1.add(new JXColumnTag("1","Column11","string",15,false,false));
              cols1.add(new JXColumnTag("2","Column12","string",57,false,false));
              List<JXColumnTag> cols2=new ArrayList<>();
              cols2.add(new JXColumnTag("3","Column21","string",11,false,false));
              cols2.add(new JXColumnTag("4","Column22","string",50,false,false));
              JXNewTableTag tt1=new JXNewTableTag("01","Claus","1/1/2009 1:1","Table1", cols1);
              JXNewTableTag tt2=new JXNewTableTag("02","Claus","1/1/2009 1:1","Table2", cols2);
              
              List<JXTag> tags=new ArrayList<>();
              tags.add(tt1);
              tags.add(tt2);
              JXChangeLog chl=new JXChangeLog(new JXHeader("Algun Proyecto","Pepito",Utils.getCurrentDate()),tags);
             try {
                 JAXBGenerator.generateXmlFromObject(chl, "D://file.xml");
             } catch (Exception ex) {
                 Logger.getLogger(JAXBGenerator.class.getName()).log(Level.SEVERE, null, ex);
             }
             */
             //xml a objeto
          /*try {
              ChangeLog chl2=new ChangeLog("D://file.xml",new MySQLv5_6());
              for(Tag t:chl2.getTags()){
                  System.out.print(t.exportSQLQuery().getQuery());
                   System.out.print(t.exportDBObject());
                 // NewTableTag tt=(NewTableTag)t;
                //  for(int i=0;i<tt.getColumnTags().size();i){
                 //     System.out.print(t2.exportSQLQuery());
                 // }
              }
          } catch (Exception ex) {
              Logger.getLogger(JAXBGenerator.class.getName()).log(Level.SEVERE, null, ex);
          }*/
        try {
          JXChangeLog aa=JAXBGenerator.generateObjectFromXml("D://file.xml");
          for(JXTag t:aa.getTags()){
              Tag tt=t.exportTagObject(null);
          }
        } catch (Exception ex) {
            Logger.getLogger(JAXBGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
