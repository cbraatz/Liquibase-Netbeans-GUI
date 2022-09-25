package org.tesis.dbexplorer;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.Action;
import static javax.swing.Action.NAME;
import org.openide.awt.StatusDisplayer;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;
import org.openide.windows.WindowManager;
import org.tesis.db.Table;
import org.tesis.db.TableList;
import org.tesis.db.dbms.Dbms;
import org.tesis.dbapi.Author;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.dbapi.Path;
import org.tesis.dbeditor.AddEditFKTopComponent;
import org.tesis.dbeditor.EditTableTopComponent;
import org.tesis.dbeditor.InsertDataTopComponent;
import org.tesis.dbeditor.RemoveTableTopComponent;
import org.tesis.dbeditor.RenameTableTopComponent;
import org.tesis.dbeditor.RestoreDataTopComponent;
import org.tesis.util.Utils;

/**
 *
 * @author Claus
 */
public class TableNode extends AbstractNode {
    private final String RENAME_TABLE_TEXT="Renombrar Tabla";
    private final String EDIT_TABLE_TEXT="Editar Tabla";
    private final String REMOVE_TABLE_TEXT="Borrar Tabla";
    private final String ADD_FK_TEXT="Agregar/Editar Clave Foránea";
    private final String TABLE_DDL="Ver DDL";
    private final String ADD_EDIT_DATA="Inspeccionar Tabla";
    private final String RESTORE_TABLE_DATA="Restaurar Datos";
    
    public TableNode(Table table) {
        super (Children.create(new ColumnFactory(table.getColumns(), table), true), Lookups.singleton(table));
        setDisplayName (table.getName());
    }
    
    //retorna un icono
    @Override
    public Image getIcon (int type) {
        return ImageUtilities.loadImage ("org/tesis/dbexplorer/table.png");
    }
    //retorna el icono del nodo abierto
    @Override
    public Image getOpenedIcon(int i) {
        return getIcon (i);//setea el mismo icono para el nodo abierto
    }
    //retorna las acciones disponibles
    @Override
    public Action[] getActions (boolean popup) {
        return new Action[] {new RenameAction(), new EditAction(), new RemoveAction(), new AddFKAction(), new ViewTableDDLAction(), new AddEditDataAction(), new RestoreTableDataAction()};
    }
    
    private class RenameAction extends AbstractAction {
        public RenameAction () {
            putValue (NAME, RENAME_TABLE_TEXT);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
            Table table = getLookup().lookup(Table.class);
            Author author = dbe.getLookup().lookup(Author.class);
            Dbms dbms = dbe.getLookup().lookup(Dbms.class);
            RenameTableTopComponent tc = (RenameTableTopComponent) WindowManager.getDefault().findTopComponent("RenameTableTopComponent");
            tc.setAuthor(author);
            tc.setDbms(dbms);
            tc.setTable(table);
            tc.open();
        }
    }
    
    private class EditAction extends AbstractAction {
        public EditAction () {
            putValue (NAME, EDIT_TABLE_TEXT);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
            Author author = dbe.getLookup().lookup(Author.class);
            Table table = getLookup().lookup(Table.class);
            Dbms dbms = dbe.getLookup().lookup(Dbms.class);
            EditTableTopComponent tc = (EditTableTopComponent) WindowManager.getDefault().findTopComponent("EditTableTopComponent");
            tc.setAuthor(author);
            tc.setDbms(dbms);
            tc.setTable(table);
            tc.open();
        }
    }
    private class RemoveAction extends AbstractAction {
        public RemoveAction () {
            putValue (NAME, REMOVE_TABLE_TEXT);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
            Author author = dbe.getLookup().lookup(Author.class);
            Table table = getLookup().lookup(Table.class);
            Dbms dbms = dbe.getLookup().lookup(Dbms.class);
            RemoveTableTopComponent tc = (RemoveTableTopComponent) WindowManager.getDefault().findTopComponent("RemoveTableTopComponent");
            tc.setAuthor(author);
            tc.setDbms(dbms);
            tc.setTable(table);
            tc.open();
        }
    }
    private class AddFKAction extends AbstractAction {
        public AddFKAction () {
            putValue (NAME, ADD_FK_TEXT);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
            Author author = dbe.getLookup().lookup(Author.class);
            Table table = getLookup().lookup(Table.class);
            TableList tables = dbe.getLookup().lookup(TableList.class);
            Dbms dbms = dbe.getLookup().lookup(Dbms.class);
            AddEditFKTopComponent tc = (AddEditFKTopComponent) WindowManager.getDefault().findTopComponent("AddEditFKTopComponent");
            tc.setAuthor(author);
            tc.setDbms(dbms);
            tc.setTable(table);
            tc.setTables(tables.getTables());
            tc.open();
        }
    }
    private class ViewTableDDLAction extends AbstractAction {

        public ViewTableDDLAction () {
            putValue (NAME, TABLE_DDL);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
                Dbms dbms = dbe.getLookup().lookup(Dbms.class);
                
                Table table = getLookup().lookup(Table.class);
                Path directoryPath=dbe.getLookup().lookup(Path.class);
                String path=directoryPath+table.getName()+".sql";
                Utils.writeFile(path, dbms.getDbmsInstance().getTableDDL(table));
                File file = new File(path);
                if (file.exists()) {
                    FileObject foToOpen = FileUtil.toFileObject(file);
                    DataObject.find(foToOpen).getLookup().lookup(OpenCookie.class).open();
                } else {
                    StatusDisplayer.getDefault().setStatusText(path+" no existe!");
                }
            }catch (DataObjectNotFoundException ex) {
                MyLogger.LogErrorMessage(ex);
                Message.showErrorMessage("DataObjectNotFoundException  al imprimir el DDL de una tabla. "+ex.getMessage());
            } catch (Exception ex) {
                MyLogger.LogErrorMessage(ex);
                Message.showErrorMessage("Exception  al imprimir el DDL de una tabla. "+ex.getMessage());
            }
        }
    }
    private class AddEditDataAction extends AbstractAction {
        public AddEditDataAction () {
            putValue (NAME, ADD_EDIT_DATA);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
            Table table = getLookup().lookup(Table.class);
            Author author = dbe.getLookup().lookup(Author.class);
            Dbms dbms = dbe.getLookup().lookup(Dbms.class);
            InsertDataTopComponent tc = (InsertDataTopComponent) WindowManager.getDefault().findTopComponent("InsertDataTopComponent");
            tc.setAuthor(author);
            tc.setDbms(dbms);
            tc.setTable(table);
            tc.open();
        }
    }
    private class RestoreTableDataAction extends AbstractAction {
        public RestoreTableDataAction () {
            putValue (NAME, RESTORE_TABLE_DATA);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
            Table table = getLookup().lookup(Table.class);
            Author author = dbe.getLookup().lookup(Author.class);
            Dbms dbms = dbe.getLookup().lookup(Dbms.class);
            RestoreDataTopComponent tc = (RestoreDataTopComponent) WindowManager.getDefault().findTopComponent("RestoreDataTopComponent");
            tc.setAuthor(author);
            tc.setDbms(dbms);
            tc.setTable(table);
            tc.open();
        }
    }
}
                
            