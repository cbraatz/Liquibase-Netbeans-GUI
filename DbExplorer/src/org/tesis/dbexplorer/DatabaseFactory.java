/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.dbexplorer;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.tesis.db.Database;

/**
 *
 * @author Claus
 */
public class DatabaseFactory extends ChildFactory<Database> {
    Database db;
    public DatabaseFactory(Database db){
        this.db=db;
    }
    @Override
    protected boolean createKeys(List<Database> list) {
        
        list.add(this.db);
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Database key) {
        return new DatabaseNode(key);
    }
    
}
