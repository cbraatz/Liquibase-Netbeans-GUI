package org.tesis.db;


public final class RenameTable {
    private String name;
    private String oldName;
    public RenameTable() {
        
    }
    public RenameTable(String name, String oldName) {
        this.name=name;
        this.oldName=oldName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getOldName() {
        return oldName;
    }
    
    public void setOldName(String oldName) {
        this.oldName = oldName;
    }
}
