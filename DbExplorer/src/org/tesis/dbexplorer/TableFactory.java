/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.dbexplorer;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.tesis.db.Table;

/**
 *
 * @author Claus
 */
public class TableFactory extends ChildFactory<Table> {
    List<Table> tables;
    public TableFactory(List<Table> tables){
        this.tables=tables;
    }
    @Override
    protected boolean createKeys(List<Table> list) {
        list.addAll(this.tables);
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Table key) {
        return new TableNode(key);
    }
    
}
