package org.tesis.db;

import java.util.List;

/**
 *Clase contenedora de una lista de tablas
 */
public final class TableList {
    private List<Table> tables;

    public TableList(List<Table> tables) {
        this.tables = tables;
    }

    public List<Table> getTables() {
        return tables;
    }
    
}
