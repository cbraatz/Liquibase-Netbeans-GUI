package org.tesis.dbapi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tesis.db.MyConnection;
import org.tesis.db.dbms.Dbms;
import org.tesis.util.Utils;

/**
 * La clase config es la encargada de brindar los métodos necesarios para interactuar con el archivo de configuración en donde se almacenan datos como<br>
 *          - La URL completa  para acceder a la base de datos, donde ya están incluidos  datos como usuario y contraseña<br>
 *          - El nombre del archivo ChangeLog<br>
 *          - El nombre del autor del proyecto<br>
 *          - El nombre del motor de base de datos<br>
 * Esta clase cuenta con métodos como writeConfigFile, getConnectionFromConfigFile, getChangeLogNameFromConfigFile, getAuthorFromConfigFile y getDbmsFromConfigFile
 * los cuales son métodos utilizados para tanto introducir como obtener datos del archivo de configuración.
*/
public class Config {
   /**
     * Método Utilizado para escribir dentro del archivo de configuración, se escriben datos como 
     * <br>Driver
     * <br> Url
     * <br> Username
     * <br> Password
     * <br> Nombre del changelog
     * <br> Autor del proyecto
     * <br> Nombre del DBMS <br>
     * @param mc Objeto del tipo MyConnection, cuyos atributos son URL, USER, PASS y DBNAME  
     * @param path  Url del archivo de configuración
     * @param changeLogName Nombre del archivo changeLog
     * @param author Autor del proyecto
     * @param dbms Objeto del tipo DBMS, el cual contiene el nombre del motor de base de datos, la URL de la base de datos y  el driver de conexión a la base de datos
     * @return 
     * @throws Exception 
     */

    public static boolean writeConfigFile(MyConnection mc, String path, String changeLogName, String author, Dbms dbms) throws Exception{
        String file=path+Utils.getPathSeparator()+Constants.DEFAULT_CONFIG_FILE_NAME;
        File f;
        f = new File(file);
        //Escritura
        FileWriter w = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(w);
        PrintWriter wr = new PrintWriter(bw);	
        wr.write("jdbc.drivers=");wr.append(mc.getDriver());wr.write("\n");
        wr.append("jdbc.url=");wr.append(mc.getUrl());wr.write("\n");
        wr.append("jdbc.username=");wr.append(mc.getUser());wr.write("\n");
        wr.append("jdbc.password=");wr.append(mc.getPass());wr.write("\n");
        wr.append("changeLogName=");wr.append(changeLogName);wr.write("\n");
        wr.append("authorName=");wr.append(author);wr.write("\n");
        wr.append("dbmsName=");wr.append(dbms.name());wr.write("\n");
        wr.close();
        bw.close();
        return (null != mc.getConnectionFromFile(file));
   }
/**
     * Método utilizado para obtener la URL de la base de datos, la cual está contenida en el archivo de configuración.
     * 
     * @param configFile Nombre del archivo de configuración
     * @return Objeto del tipo MyConection, el cual contiene driver, url, user, pass y dbName;
     * @throws Excepción 
     */

   public static MyConnection getConnectionFromConfigFile(String configFile, Dbms dbms) throws Exception{
        FileInputStream in = null;
        Properties props = new Properties();
        in = new FileInputStream(configFile);
        props.load(in);
        in.close();
        String jdbcDriver = props.getProperty("jdbc.drivers");
        String jdbcUrl = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        try {
            if (null!=in){
                in.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new MyConnection(jdbcDriver, jdbcUrl,username, password, Util.getDatabaseNameFromJdbcUrl(jdbcUrl));
    }
  /**
    * Método que permite obtener el nombre del Changelog, el cual se encuentra especificado en el archivo de configuración
    * @param configFile Nombre del archivo de configuración
    * @return Nombre del changeLogFile
    * @throws Exception 
    */

    public static String getChangeLogNameFromConfigFile(String configFile)throws Exception{
        FileInputStream in = null;
        Properties props = new Properties();
        in = new FileInputStream(configFile);
        props.load(in);
        in.close();
        String changeLogName = props.getProperty("changeLogName");
        if (null!=in){
            in.close();
        }
        return changeLogName; 
    }
   /**
     * Método que permite obtener el nombre del autor del proyecto a partir de un archivo de configuración cuyo nombre es especificado con parámetro de la función
     * @param configFile Nombre del archivo de configuración
     * @return  Objeto del tipo autor, el cual contiene el nombre del mismo.
     * @throws Exception 
     */

    public static Author getAuthorFromConfigFile(String configFile)throws Exception{
        FileInputStream in = null;
        Properties props = new Properties();
        in = new FileInputStream(configFile);
        props.load(in);
        in.close();
        String author = props.getProperty("authorName");
        if (null!=in){
            in.close();
        }
        return new Author(author);
   }
  /**
     * Método que permite obtener las especificaciones del motor de base de datos utilizado, el cual se encuentra especificado en el archivo de configuración
     * 
     * @param configFile Nombre del archivo de configuración
     * @return Enum del tipo Dbms, el cual contiene el nombre del motor de base de datos, la Url y el driver de conexión 
     * @throws Exception 
     */
  
    public static Dbms getDbmsFromConfigFile(String configFile)throws Exception{
        FileInputStream in = null;
        Properties props = new Properties();
        in = new FileInputStream(configFile);
        props.load(in);
        in.close();
        String dbmsName = props.getProperty("dbmsName");
        if (null!=in){
            in.close();
        }
        return Dbms.valueOf(dbmsName);
   }
    /**
     * Método que permite obtener todos los valores del archivo de configuración
     * 
     * @param configFile Nombre del archivo de configuración
     * @return ConfigValues Objeto que contiene todos los valores del archivo de configuración 
     * @throws Exception 
     */
  
    public static ConfigValues getAllConfigValuesFromConfigFile(String configFile)throws Exception{
        FileInputStream in = null;
        Properties props = new Properties();
        in = new FileInputStream(configFile);
        props.load(in);
        in.close();
        ConfigValues values= new ConfigValues();
        values.setDbDriver(props.getProperty("jdbc.drivers"));
        values.setDbUrl(props.getProperty("jdbc.url"));
        values.setDbUser(props.getProperty("jdbc.username"));
        values.setDbPass(props.getProperty("jdbc.password"));
        values.setChangeLogName(props.getProperty("changeLogName"));
        values.setAuthor(props.getProperty("authorName"));
        values.setDbms(props.getProperty("dbmsName"));     
        if (null!=in){
            in.close();
        }
        return values;
   }
}
