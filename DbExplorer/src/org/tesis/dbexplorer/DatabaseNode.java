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
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.openide.util.lookup.Lookups;
import org.openide.windows.WindowManager;
import org.tesis.db.Database;
import org.tesis.db.dbms.Dbms;
import org.tesis.dbapi.Author;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.dbapi.Path;
import org.tesis.dbapi.Util;
import org.tesis.dbeditor.AddTableTopComponent;
import org.tesis.util.Utils;



/**
 *
 * @author Claus
 */
public class DatabaseNode extends AbstractNode{
    private final String ADD_TABLE_TEXT="Agregar Tabla";
    private final String UPDATE_DATABASE_TEXT="Recargar Base de Datos";
    private final String VIEW_DB_DDL="Ver DDL";
    private final String VIEW_DB_SQL="Operaciones realizadas - BD";
    public DatabaseNode(Database db) {
        super (Children.create(new TableFactory(db.getTables()), true), Lookups.singleton(db));
        setDisplayName (db.getName());
    }
    @Override
    public Image getIcon (int type) {
        return ImageUtilities.loadImage ("org/tesis/dbexplorer/database.png");
    }
    //retorna el icono del nodo abierto
    @Override
    public Image getOpenedIcon(int i) {
        return getIcon (i);//setea el mismo icono para el nodo abierto
    }
    //retorna las acciones disponibles
    @Override
    public Action[] getActions (boolean popup) {
        return new Action[] { new AddTableAction(),new UpdateDataBaseAction(), new ViewDDLAction(), new ViewDMLAction()};
    }
    
    private class AddTableAction extends AbstractAction {

        public AddTableAction () {
            putValue (NAME, ADD_TABLE_TEXT);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
            Author author = dbe.getLookup().lookup(Author.class);
            Dbms dbms = dbe.getLookup().lookup(Dbms.class);
            AddTableTopComponent tc = (AddTableTopComponent) WindowManager.getDefault().findTopComponent("AddTableTopComponent");
            tc.setAuthor(author);
            tc.setDbms(dbms);
            tc.open();
        }
    }
    private class ViewDDLAction extends AbstractAction {

        public ViewDDLAction () {
            putValue (NAME, VIEW_DB_DDL);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
            DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
            Dbms dbms = dbe.getLookup().lookup(Dbms.class);
            Database db=getLookup().lookup(Database.class);
            Path directoryPath=dbe.getLookup().lookup(Path.class);
            String path=directoryPath+"Db.sql";
            Utils.writeFile(path, dbms.getDbmsInstance().getDatabaseCreationDDL(db.getName()));
            File file = new File(path);
            if (file.exists()) {
                FileObject foToOpen = FileUtil.toFileObject(file);
                DataObject.find(foToOpen).getLookup().lookup(OpenCookie.class).open();
            } else {
                StatusDisplayer.getDefault().setStatusText(path+" no existe!");
            }
            }catch (DataObjectNotFoundException ex) {
                MyLogger.LogErrorMessage(ex);
                Message.showErrorMessage("DataObjectNotFoundException  al imprimir el DDL de la BD. "+ex.getMessage());
            } catch (Exception ex) {
                MyLogger.LogErrorMessage(ex);
                Message.showErrorMessage("Exception  al imprimir el DDL de la BD. "+ex.getMessage());
            }
        }
    }
    private class ViewDMLAction extends AbstractAction {

        public ViewDMLAction () {
            putValue (NAME, VIEW_DB_SQL);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
                Dbms dbms = dbe.getLookup().lookup(Dbms.class);
                Database db=getLookup().lookup(Database.class);
                Path directoryPath=dbe.getLookup().lookup(Path.class);
                String path=directoryPath+"Consultas.sql";
                Utils.writeFile(path, dbms.getDbmsInstance().getDatabaseQueries(db));
                File file = new File(path);
                if (file.exists()) {
                    FileObject foToOpen = FileUtil.toFileObject(file);
                    DataObject.find(foToOpen).getLookup().lookup(OpenCookie.class).open();
                } else {
                    StatusDisplayer.getDefault().setStatusText(path+" no existe!");
                }
            }catch (DataObjectNotFoundException ex) {
                MyLogger.LogErrorMessage(ex);
                Message.showErrorMessage("DataObjectNotFoundException  al imprimir los SQLs que se ejecutaron sobre la BD. "+ex.getMessage());
            } catch (Exception ex) {
                MyLogger.LogErrorMessage(ex);
                Message.showErrorMessage("Exception  al imprimir los SQLs que se ejecutaron sobre la BD. "+ex.getMessage());
            }
        }
    }
    private class UpdateDataBaseAction extends AbstractAction {

        public UpdateDataBaseAction () {
            putValue (NAME, UPDATE_DATABASE_TEXT);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DbExplorerTopComponent dbe = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
            dbe.resetAllWithProgressBar();
        }
    }
}
