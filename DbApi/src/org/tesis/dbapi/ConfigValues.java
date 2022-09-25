package org.tesis.dbapi;

import org.tesis.exception.InvalidValueException;

public class ConfigValues {
    private String dbDriver;
    private String dbUrl;
    private String dbUser;
    private String dbPass;
    private String changeLogName;
    private String dbms;
    private String author;
    private String host;
    private String port;
    private String dbName;
    public ConfigValues(){
    }
    public ConfigValues(String configFile)throws Exception{
        ConfigValues val=Config.getAllConfigValuesFromConfigFile(configFile);
        this.author=val.getAuthor();
        this.changeLogName=val.getChangeLogName();
        this.dbDriver=val.dbDriver;
        this.dbPass=val.dbPass;
        this.dbUrl=val.dbUrl;
        this.dbUser=val.dbUser;
        this.dbms=val.dbms;
        if(null!=dbUrl){
            this.getValuesFromDbUrl(dbUrl);
        }
        if(null==author || null==changeLogName || null==dbDriver || null==dbPass || null==dbUrl || null==dbUser || null==dbms || null==host || null==port){
            throw new InvalidValueException("Alguno de los valores del archivo de configuración es nulo.");
        }
    }
    private void getValuesFromDbUrl(String url){
        String aux=url.substring(url.indexOf("//")+2);//saca todo lo que sigue a la // del url
        int x=aux.indexOf("/");
        this.dbName=aux.substring(x+1);//saca el nombre de la base de datos
        String aux2=aux.substring(0, x);
        x=aux2.indexOf(":");
        if(x>0){
            this.host=aux2.substring(0, x);
            this.port=aux2.substring(x+1);
        }else{//si no tiene puerto o sea ":"
            this.host=aux2;
            this.port="";
        }
    }
    
    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }

    public String getChangeLogName() {
        return changeLogName;
    }

    public String getDbms() {
        return dbms;
    }

    public String getAuthor() {
        return author;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
    }

    public void setDbUrl(String dbUrl) throws InvalidValueException{
        this.dbUrl = dbUrl;
        if(null!=dbUrl){
            this.getValuesFromDbUrl(dbUrl);
        }else{
            throw new InvalidValueException("El valor del parámetro dbUrl es nulo.");
        }
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }

    public void setChangeLogName(String changeLogName) {
        this.changeLogName = changeLogName;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
}
