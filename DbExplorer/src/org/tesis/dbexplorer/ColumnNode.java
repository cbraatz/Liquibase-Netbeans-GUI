/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.dbexplorer;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;
import org.openide.windows.WindowManager;
import org.tesis.db.Column;
import org.tesis.db.ForeignKey;
import org.tesis.db.Table;
import org.tesis.db.dbms.Dbms;
import org.tesis.dbapi.Author;
import org.tesis.dbeditor.RemoveColumnTopComponent;


/**
 *
 * @author Claus
 */
public class ColumnNode  extends AbstractNode{
    private final String REMOVE_COLUMN_TEXT="Borrar columna";
    Table table;
    boolean isPK=false;
    boolean isFK=false;
    public ColumnNode(Column col, Table table) {
        super (Children.LEAF, Lookups.singleton(col));
        setDisplayName (col.getName());
        String tooltip="";
        this.table=table;
        this.isPK=col.isPk();
        for(ForeignKey fk:table.getForeignKeys().getForeignKeys()){
            if(fk.getColumn().getName().equals(col.getName())){
                this.isFK=true;
                tooltip=fk.getForeignTable()+"-->"+fk.getForeignColumn()+" " ;
                this.setShortDescription(tooltip);
            }
        }
        this.setShortDescription(tooltip+col.getType().toString());        
    }
    @Override
    public Action[] getActions (boolean popup) {
        return new Action[] { new RemoveColumnAction() };
    }
    
     //retorna un icono
    @Override
    public Image getIcon (int type) {
        if(this.isPK){
            if(this.isFK){
               return ImageUtilities.loadImage ("org/tesis/dbexplorer/PK_FK.png");
            }else{
                return ImageUtilities.loadImage ("org/tesis/dbexplorer/pk.png");
            }
        }else{
           if(this.isFK){
               return ImageUtilities.loadImage ("org/tesis/dbexplorer/fk.png");
           }else{
                return ImageUtilities.loadImage ("org/tesis/dbexplorer/column.png");
           }
        }
    }

    private class RemoveColumnAction extends AbstractAction {
        public RemoveColumnAction () {
            putValue (NAME, REMOVE_COLUMN_TEXT);
        }
        
        //retorna las acciones disponibles
        @Override
        public void actionPerformed(ActionEvent e) {
            DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
            Column column = getLookup().lookup(Column.class);
            Author author = dbe.getLookup().lookup(Author.class);
            Dbms dbms = dbe.getLookup().lookup(Dbms.class);
            RemoveColumnTopComponent tc = (RemoveColumnTopComponent) WindowManager.getDefault().findTopComponent("RemoveColumnTopComponent");
            tc.setAuthor(author);
            tc.setDbms(dbms);
            tc.setTable(table);
            tc.setColumn(column);
            tc.open();
        }
    }
}
