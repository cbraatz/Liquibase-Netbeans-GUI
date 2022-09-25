package org.tesis.db;

public class RemoveTable {
    private String name;
    
    public RemoveTable() {  
    }
    
    public RemoveTable(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
