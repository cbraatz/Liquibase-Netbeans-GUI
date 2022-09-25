package org.tesis.dbexplorer;

import java.awt.BorderLayout;
import java.util.Collection;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.util.Utilities;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.tesis.changelog.tag.TagList;
import org.tesis.db.MyConnection;
import org.tesis.db.TableList;
import org.tesis.db.dbms.Dbms;
import org.tesis.dbapi.Author;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.dbapi.Path;
import org.tesis.dbapi.Util;
import org.tesis.exception.InvalidValueException;
import org.tesis.mybase.MyBase;

@ConvertAsProperties(
        dtd = "-//org.tesis.dbexplorer//DbExplorer//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "DbExplorerTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "explorer", openAtStartup = false)
@ActionID(category = "Window", id = "org.tesis.dbexplorer.DbExplorerTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_DbExplorerAction",
        preferredID = "DbExplorerTopComponent")
@Messages({
    "CTL_DbExplorerAction=DbExplorer",
    "CTL_DbExplorerTopComponent=DbExplorer",
    "HINT_DbExplorerTopComponent=ventana DbExplorer"
})
public final class DbExplorerTopComponent extends TopComponent implements LookupListener, ExplorerManager.Provider{
    private final InstanceContent content= new InstanceContent();
    private Lookup.Result<? extends TagList> lookupResult = null;
    private MyBase myBase;
    private final String PROGRESS_BAR_TEXT="Por favor espere...";
    public DbExplorerTopComponent() {
        initComponents();
        associateLookup(new AbstractLookup(content));
        setName(Bundle.CTL_DbExplorerTopComponent());//setea el titulo de la ventana del explorer
        setToolTipText(Bundle.HINT_DbExplorerTopComponent());
    }
    /*
     * Inicializa el explorador y muestra una barra de progreso mientras se carga
     */
    public void init(final MyConnection mc, final String changeLogPath, final Author author, final Dbms dbms){
        Runnable runn = new Runnable() {
            @Override
            public void run() {
                final ProgressHandle progress=ProgressHandleFactory.createHandle(PROGRESS_BAR_TEXT);
                progress.start();
                initExplorerComponents(mc, changeLogPath, author, dbms);
                //progress.progress("...");
                progress.finish();
            }

        };
        RequestProcessor.getDefault().post(runn);
    }
    /*
     * Actualiza la base de datos y el objeto Mybase y muestra una barra de progreso mientras se carga
     */
    public void resetAllWithProgressBar(){
        Runnable runn = new Runnable() {
            @Override
            public void run() {
                final ProgressHandle progress=ProgressHandleFactory.createHandle(PROGRESS_BAR_TEXT);
                progress.start();
                resetAll();
                progress.finish();
            }

        };
        RequestProcessor.getDefault().post(runn);
    }
    /**
     * Resetea todo, el changelog, la base de datos y el objeto database.
     */
    private void resetAll(){
        Author author = this.getLookup().lookup(Author.class);
        Dbms dbms = this.getLookup().lookup(Dbms.class);
        this.init(this.myBase.getDatabase().getConnection(), this.myBase.getDatabase().getChangeLog().getChangeLogPath(), author, dbms);
    }
    private void initExplorerComponents(MyConnection mc, String changeLogPath, Author author, Dbms dbms){
        try{
            setLayout(new BorderLayout());
            add(new BeanTreeView(), BorderLayout.CENTER);
            this.myBase=new MyBase(mc, changeLogPath, dbms);
            this.refreshExplorer();
            //agrega autor al lookup
            Author oldAuthor = this.getLookup().lookup(Author.class);
            if(null != oldAuthor){
                content.remove(oldAuthor);
            }
            content.add(author);  
            //agrega dbms al lookup
            Dbms oldDbms = this.getLookup().lookup(Dbms.class);
            if(null != oldDbms){
                content.remove(oldDbms);
            }
            content.add(dbms);  
            //agrega path al lookup
            Path oldPath = this.getLookup().lookup(Path.class);
            if(null != oldPath){
                content.remove(oldPath);
            }
            content.add(new Path(Util.getDirectoryPath(changeLogPath)));  
        }catch(InvalidValueException ex){
            String msg="Ha ocurrido un error al leer el Change-Log. "+ex.getMessage();
            MyLogger.LogErrorMessage(ex);
            Message.showErrorMessage(msg);
        }catch(Exception ex){
            String msg="Ha ocurrido un error al inicializar el explorer. "+ex.getMessage();
            MyLogger.LogErrorMessage(ex);
            Message.showErrorMessage(msg);
        }finally{
            //pb.stop();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        //String version = p.getProperty("version");
        // TODO read your settings according to their version
    }

    private final ExplorerManager mgr = new ExplorerManager();
    //private InstanceContent content = new InstanceContent();
    //private Lookup dynamicLookup = new AbstractLookup(content);
    @Override
    public ExplorerManager getExplorerManager() {
        return mgr;
    }
    public void refreshExplorer(){
        DatabaseNode dataBaseNode=new DatabaseNode(this.myBase.getDatabase());
        mgr.setRootContext(dataBaseNode);
        
        //agrega lista de tablas al lookup cada vez que se actualiza la base de datos
        TableList oldTableList = this.getLookup().lookup(TableList.class);
        if(null != oldTableList){
            content.remove(oldTableList);
        }
        content.add(new TableList(this.myBase.getDatabase().getTables()));//se agrega un TableList para usarlo como contenedor de la lista ya que agregar un ArrayList es muy generico y no puede ser muy bien identificado como una lista de tablas o de otro objeto  
    }
    private void registerNewDbUpdate(TagList tagList) throws Exception{
        this.myBase.addNewTagListAndUpdateAll(tagList);//aca ya verifica si hay ids o nombre repetidos
    }
    /**
     * Método ejecutado al abrirse el Explorer
     */
    @Override
    public void componentOpened() {
        lookupResult = Utilities.actionsGlobalContext().lookupResult(TagList.class);
        lookupResult.addLookupListener (this);
    }
    /**
     * Método ejecutado al cerrarse el Explorer
     */
    @Override
    public void componentClosed() {
        lookupResult.removeLookupListener(this);
    }
    /**
     * Método disparado al detectarse un nuevo tag en el lookup.
     * @param le LookupEvent
     */
    @Override
    public void resultChanged(LookupEvent le) {
        Collection<? extends TagList> tags = lookupResult.allInstances();
        if (!tags.isEmpty()) {
            try {
                TagList tagList = tags.iterator().next();
                this.registerNewDbUpdate(tagList);
                this.refreshExplorer();
            } catch (Exception ex) {
                MyLogger.LogErrorMessage(ex);
                Message.showErrorMessage(ex.getMessage());
                this.resetAllWithProgressBar();//recarga todo a partir del changelog, que es el último al actualizarse y cuando se lanza una excepción en teoría no esta corrupto.
            } 
        }
            //System.out.println("Se encontró actividad en el lookup no del tipo Tag."); 
    }
}
