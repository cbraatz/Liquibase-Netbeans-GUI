package org.tesis.mybase;

import org.tesis.changelog.tag.TagList;
import org.tesis.db.MyConnection;
import org.tesis.db.Database;
import org.tesis.db.dbms.Dbms;

/**
 *Clase principal de la libreria que contiene la base de datos y se encarga de crearla y actualizarla.
 */
public class MyBase {
    private Database database=null;
    private Dbms dbms=null;
    public MyBase(MyConnection connection, String changeLog, Dbms dbms) throws Exception{
        this.dbms=dbms;
        ChangeLog ch=new ChangeLog(changeLog, dbms);
        this.database=new Database(connection, ch, dbms);
    }

    public Database getDatabase() {
        return database;
    }
    
    public boolean update() throws Exception{
        if(null != database){
            database.updateDatabase(dbms);
            return true;    
        }else{
            return false;
        }
    }
    public boolean updateDatabaseObject() throws Exception{
        if(null != database){
            database.generateDatabaseObject();
            return true;    
        }else{
            return false;
        }
    }
    /**
     * Agrega un nuevo TagList al changelog interno de la base de datos
     * y sin actualizar la base de datos.
     * @param tags
     * @throws Exception 
     */
    public void addNewTagListAndUpdateAll(TagList tags) throws Exception{
        this.database.addNewTagListAndUpdateAll(tags);
    }
    /**
     * Genera el archivo xml.
     * @throws Exception 
     */
    public void generateXMLFile() throws Exception{
        this.database.generateXMLFile();
    }
    /*public static void main(String[] args){
        try {
            //MyBase mb=new MyBase(new MyConnection("src\\org\\tesis\\db\\database.properties"), "src\\org\\tesis\\interpreter\\changeLog.xml");
            MyBase mb=new MyBase(new MyConnection("org.postgresql.Driver","jdbc:postgresql://localhost:5432/test","root","123456","test"), "D:\\changeLog5.xml",null);
            //mb.update();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }*/
}
