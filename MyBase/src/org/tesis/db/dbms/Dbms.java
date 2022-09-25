package org.tesis.db.dbms;

public enum Dbms {
    MYSQL_5_6("com.mysql.jdbc.Driver","jdbc:mysql://"),
    POSTGRESQL_9_2("org.postgresql.Driver","jdbc:postgresql://");
    
    
    private String driver;
    private String Url;
    private Dbms(String driver,String Url) {
        this.driver = driver;
        this.Url=Url;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String Url) {
        this.Url = Url;
    }
     
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
    
    public DbmsManagement getDbmsInstance(){
        switch(this){
            case MYSQL_5_6: return new MySQLv5_6();
            case POSTGRESQL_9_2: return new PostgresSQLv9_2();
            default: return null;
        }
    }
}
