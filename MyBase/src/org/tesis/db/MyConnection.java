package org.tesis.db;
import java.io.FileInputStream;
import java.sql.*;
import java.util.*;
import org.tesis.db.dbms.Dbms;
/**
 *
 * @author Claus
 */
public final class MyConnection {
    private String driver;
    private String url;
    private String user;
    private String pass;
    private String dbName;
    private static final int LIMIT = 300;//limite de intentos 
    
   public MyConnection(String jdbcDriver, String jdbcUrl, String dbUserName, String dbPassWord, String dbName) throws Exception{
       if(this.runConnectionTest(jdbcDriver, jdbcUrl, dbUserName, dbPassWord)){
           this.driver=jdbcDriver;
           this.url=jdbcUrl;
           this.user=dbUserName;
           this.pass=dbPassWord;
           this.dbName=dbName;
       }else{
           throw new Exception("Ha ocurrido un error en el constructor de MyConnection. Ha fallado el test de conexión.");
       }
    }

    public String getDatabaseName() {
        return dbName;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
   
   /**
    * Hace un test de conexión creando una tabla, agragando un valor, leyendo ese valor y borrando la tabla.
    * @param driver jdbcDriver
    * @param url jdbcUrl
    * @param user jdbcUser
    * @param pass jdbcPass
    * @return retorna si el test de conexión fue exitoso o no.
    * @throws Exception 
    */
   public boolean runConnectionTest(String driver, String url, String user, String pass) throws ClassNotFoundException, SQLException{
        String write="Connection test...!"; 
        String read;
        Connection conn=this.getConnection(driver, url, user, pass);
       // System.out.println("Inicio de comprobacion de conexión...!");

        Statement stat = conn.createStatement();
        stat.execute("CREATE TABLE test_system_h554_6xc254_652_trc_458_xc (Message CHAR(19))");
        stat.execute("INSERT INTO test_system_h554_6xc254_652_trc_458_xc VALUES ('"+write+"')");

        ResultSet result = stat.executeQuery("SELECT * FROM test_system_h554_6xc254_652_trc_458_xc");
        result.next();
        read=result.getString(1);
       // System.out.println("Resultado leido = "+read);
        stat.execute("DROP TABLE test_system_h554_6xc254_652_trc_458_xc");
        stat.close();
        conn.close();
        return read.equals(write);
   }
   
   /**
    * Ejecuta una lista de consultas sql.
    * @param sqls Lista de sqls a ejecutar
    * @throws Exception 
    */
   public void executeMultipleSqlQuery(List<SqlQuery> sqls) throws Exception{
        if(sqls.size() > 0){
           Connection conn=null;
           Statement stat=null;
           conn=this.getConnection(driver, url, user, pass);
           stat = conn.createStatement();
           try{//atrapar algunas exceptiones conocidas
                for(SqlQuery q : sqls){
                  //  System.out.println("Ejecutando query: "+q.getQuery());
                    stat.execute(q.getQuery());
                }
           }catch(com.mysql.jdbc.MysqlDataTruncation e1){
               throw new Exception("No es posible hacer Not Nullable una columna con datos nulos.");
           }
           if(stat!=null)
              stat.close();
           if(conn!=null)
              conn.close();  
        }
   }
   /**
    * Borra todas las tablas de la base de datos.
    * @throws Exception 
    */
   public void dropAllTables(Dbms dbms) throws Exception{
       Connection conn=null;
       Statement stat=null;

       conn=this.getConnection(driver, url, user, pass);
       stat = conn.createStatement();
       DatabaseMetaData meta=conn.getMetaData();
       ResultSet rs;
       
       int c;
       int limit=0;
       do{
           rs=meta.getTables(null, null, "%", new String[]{"TABLE"});
           c=0;
           while(rs.next()){//recorre las tablas e intenta borrarla
               c++;
               String tableName=rs.getString(3);
               try{//por si hay dependencias entre tablas o fks
                    /*ResultSet rsfk=rsfk = meta.getExportedKeys(conn.getCatalog(), null, tableName);
                    while (rsfk.next()) {//recorre los fks de la tabla e intenta borrarlo
                        String tbName = rsfk.getString("FKTABLE_NAME");//hace referencia aparentemente a la tabla donde la tabla actual tiene una clave foranea
                        String colName = rsfk.getString("FKCOLUMN_NAME");
                        String fkName = rsfk.getString("FK_NAME");
                        try{
                            SqlQuery sql=dbms.getDbmsInstance().getRemoveForeignKeyStatement(new RemoveForeignKey(tbName, colName, fkName));
                            System.out.println("Tratando de ejecutar la consulta: "+sql.getQuery());
                            stat.execute(sql.getQuery());
                        }catch(SQLException e1){
                            System.err.println("No se pudo borrar el FK: "+fkName+" de la tabla: "+tableName+" y Columna: "+colName+". Descripción: "+e1.getMessage());
                        }
                    }*/
                   SqlQuery sql=dbms.getDbmsInstance().getRemoveAllDataFromTableStatement(new RemoveData(tableName, null));//puede causar problemas en tablas que se referencian a si mismas. Solucion aqui: http://stackoverflow.com/questions/2483154/how-to-delete-all-data-from-a-table-which-contain-self-referencing-foreign-key
                   //System.out.println("Tratando de ejecutar la consulta de eliminación de todos los datos: "+sql.getQuery());
                   stat.execute(sql.getQuery());
                   sql=dbms.getDbmsInstance().getRemoveTableCascadeStatement(new RemoveTable(tableName));
                   stat.execute(sql.getQuery());
               }catch(SQLException e2){
                   System.err.println("No se pudo borrar la tabla "+tableName+" o los datos de dicha tabla. Descripción: "+e2.getMessage());
               }
           }
           limit++;
           if(limit >= LIMIT){
               throw new Exception("Se ha superado el límite de intentos para borrar la base de datos. Favor bórrela manualmente. Es posible que se deba a tablas precargadas con nombres en mayúsculas.");
           }
       }while(c != 0);
       if(stat!=null)
          stat.close();

       if(conn!=null)
          conn.close();    
   }

   /**
      Gets a connection from the properties specified
      in the file database.properties
      @return the database connection
   */
   public Connection getConnectionFromFile(String propertiesFile) throws Exception{
        FileInputStream in = null;
        Properties props = new Properties();
        in = new FileInputStream(propertiesFile);
        props.load(in);
        in.close();
        String jdbcDriver = props.getProperty("jdbc.drivers");
        String jdbcUrl = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");
        if (null!=in){
            in.close();
        }
        return this.getConnection(jdbcDriver, jdbcUrl, username, password);
   }
   
   private Connection getConnection(String driver, String url, String user, String pass) throws ClassNotFoundException, SQLException{
       Class.forName(driver);
       return DriverManager.getConnection(url, user, pass);      
   }   
}