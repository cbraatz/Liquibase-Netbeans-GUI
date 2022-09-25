/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.dbexplorer;

import java.util.List;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.tesis.db.Column;
import org.tesis.db.Table;

/**
 *
 * @author Claus
 */
public class ColumnFactory  extends ChildFactory<Column>{
    List <Column> columns;
    Table table;
    public ColumnFactory(List <Column> columns, Table table){
        this.columns=columns;
        this.table=table;
    }
    @Override
    protected boolean createKeys(List<Column> list) {
        list.addAll(this.columns);
        return true;
    }
    
    @Override
    protected Node createNodeForKey(Column key) {
        return new ColumnNode(key, this.table);
    }
}
