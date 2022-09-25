package org.tesis.dbeditor;

import java.util.Collections;
import java.util.List;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.AddForeignKeyTag;
import org.tesis.changelog.tag.EditForeignKeyTag;
import org.tesis.changelog.tag.RemoveForeignKeyTag;
import org.tesis.changelog.tag.TagList;
import org.tesis.db.Constants;
import org.tesis.db.ForeignKey;
import org.tesis.db.Table;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.exception.InvalidParameterException;
import org.tesis.ui.FKEditor;
import org.tesis.ui.UIForeignKey;
import org.tesis.util.Utils;

@ConvertAsProperties(
        dtd = "-//org.tesis.dbeditor//AddEditFK//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "AddEditFKTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.tesis.dbeditor.AddEditFKTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AddEditFKAction",
        preferredID = "AddEditFKTopComponent")
@NbBundle.Messages({
    "CTL_AddEditFKAction=AddFK",
    "CTL_AddEditFKTopComponent=Agregar FK",
    "HINT_AddEditFKTopComponent=Esta es la ventana para agregar y editar FK a una tabla"
})
public class AddEditFKTopComponent extends AddEditTopComponent{
    Table table;
    List<Table> tables;
    public AddEditFKTopComponent() {
        setName(Bundle.CTL_AddEditFKTopComponent());
        setToolTipText(Bundle.HINT_AddEditFKTopComponent());
    }

    @Override
    public void initEditor(){
        try {
            editor= new FKEditor(tables, table);
            editor.setKeyNameLabel("Nombre de la Clave Foranea:");
            this.setTableName(table.getName());
            this.getTextFieldTableName().setEditable(false);
        } catch (InvalidParameterException ex) {
            Message.showErrorMessage(ex.getMessage());
            MyLogger.LogErrorMessage(ex);
        } 
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    @Override
    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    @Override
    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    @Override
    public void runAction() {
        try {
            TagList tags=new TagList();
            List<UIForeignKey> newFks=(List<UIForeignKey>)this.editor.exportEditorResults();
            
            //ForeignKeyList foreignKeys = this.table.getForeignKeys();
            
            for(ForeignKey ofk:table.getForeignKeys().getForeignKeys()){
                boolean found=false;
                for(UIForeignKey nfk:newFks){
                    if(ofk.getInternalId()==nfk.getInternalID()){//si existe la columna
                        found=true;
                        //System.out.println("ofk ID="+ofk.getInternalId()+", FKName= "+ofk.getFKName()+", Table= "+ofk.getTable().getName()+", Column= "+ofk.getColumn().getName()+", ForeignTable= "+ofk.getForeignTable().getName()+", ForeignColumn= "+ofk.getForeignColumn().getName());
                        //System.out.println("nfk ID="+nfk.getInternalID()+", FKName= "+nfk.getFKName()+", Table= "+nfk.getTableName()+", Column= "+nfk.getColumnName()+", ForeignTable= "+nfk.getForeignTableName()+", ForeignColumn= "+nfk.getForeignColumnName());
                        if(!nfk.compareWithForeignKey(ofk)){
                            if(table.getRows().size()>0){
                                throw new Exception("No es posible editar FKs de una tabla con datos cargados.");
                            }
                            //agrega el removeFK
                          /*  PropertyList pl=new PropertyList();
                            pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                            pl.addProperty(Constants.PROPERTY_COLUMN_NAME, ofk.getColumn().getName());
                            pl.addProperty(Constants.PROPERTY_TABLE_NAME, ofk.getTable().getName());
                            pl.addProperty(Constants.PROPERTY_FK_NAME, ofk.getFKName());
                            pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor().getAuthorName());
                            pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                            RemoveForeignKeyTag rem=new RemoveForeignKeyTag(pl, this.getDbms());
                            tags.addTag(rem);
                            //agrega el addFK
                            pl=new PropertyList();
                            pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                            pl.addProperty(Constants.PROPERTY_COLUMN_NAME, nfk.getColumn().getName());
                            pl.addProperty(Constants.PROPERTY_TABLE_NAME, nfk.getTable().getName());
                            pl.addProperty(Constants.PROPERTY_FK_NAME, nfk.getFKName());
                            pl.addProperty(Constants.PROPERTY_FOREIGN_COLUMN_NAME, nfk.getForeignColumn().getName());
                            pl.addProperty(Constants.PROPERTY_FOREIGN_TABLE_NAME, nfk.getForeignTable().getName());
                            pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor().getAuthorName());
                            pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                            AddForeignKeyTag add=new AddForeignKeyTag(pl, this.getDbms());
                            tags.addTag(add);
                            break;*/
                            PropertyList pl=new PropertyList();
                            pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                            pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor().getAuthorName());
                            pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                            pl.addProperty(Constants.PROPERTY_COLUMN_NAME, nfk.getColumnName());
                            pl.addProperty(Constants.PROPERTY_TABLE_NAME, nfk.getTableName());
                            if(!nfk.getFKName().equals(ofk.getFKName())){//si el nuevo nombre es distinto al viejo
                                pl.addProperty(Constants.PROPERTY_NEW_FK_NAME, nfk.getFKName());// agrega el nuevo nombre
                            }
                            pl.addProperty(Constants.PROPERTY_FK_NAME, ofk.getFKName());
                            pl.addProperty(Constants.PROPERTY_FOREIGN_COLUMN_NAME, nfk.getForeignColumnName());
                            pl.addProperty(Constants.PROPERTY_FOREIGN_TABLE_NAME, nfk.getForeignTableName());
                            EditForeignKeyTag edit=new EditForeignKeyTag(pl, this.getDbms());
                            tags.addTag(edit);
                            break;
                        }
                    }
                }
                if(!found){
                    //agrega el removeFK
                    PropertyList pl=new PropertyList();
                    pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                    pl.addProperty(Constants.PROPERTY_COLUMN_NAME, ofk.getColumn().getName());
                    pl.addProperty(Constants.PROPERTY_TABLE_NAME, ofk.getTable().getName());
                    pl.addProperty(Constants.PROPERTY_FK_NAME, ofk.getFKName());
                    pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor().getAuthorName());
                    pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                    RemoveForeignKeyTag rem=new RemoveForeignKeyTag(pl, this.getDbms());
                    tags.addTag(rem);
                }
            }
            //ahora recorre la lista nueva primero para ver si alguno se agregó
            for(UIForeignKey nfk:newFks){
                boolean found=false;
                for(ForeignKey ofk:table.getForeignKeys().getForeignKeys()){
                    if(ofk.getColumn().getName().equals(nfk.getColumnName())){//si existe la columna
                        found=true;
                        break;
                    }
                }
                if(!found){
                    if(table.getRows().size()>0){
                       throw new Exception("No es posible agregar FKs a una tabla con datos cargados.");
                    }
                    //agrega el addFK
                    PropertyList pl=new PropertyList();
                    pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                    pl.addProperty(Constants.PROPERTY_COLUMN_NAME, nfk.getColumnName());
                    pl.addProperty(Constants.PROPERTY_TABLE_NAME, nfk.getTableName());
                    pl.addProperty(Constants.PROPERTY_FOREIGN_COLUMN_NAME, nfk.getForeignColumnName());
                    pl.addProperty(Constants.PROPERTY_FOREIGN_TABLE_NAME, nfk.getForeignTableName());
                    pl.addProperty(Constants.PROPERTY_FK_NAME, nfk.getFKName());
                    pl.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor().getAuthorName());
                    pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                    AddForeignKeyTag add=new AddForeignKeyTag(pl, this.getDbms());
                    tags.addTag(add);
                }
            }
            if(!tags.isEmpty()){
                this.getContent().set(Collections.singleton(tags), null);//agrega al lookup
                this.getContent().remove(tags);//borra el Tag de content porque sino vuelve a agregarlo al lookup al volver a esta ventana
                MyLogger.LogInformationMessage("Éxito al guardar el RemoveTableTag en el lookup.", "RemoveTableTopComponent");
                this.close();
            }else{
                Message.showWarningMessage("No hay cambios a ser aplicados."); 
            }
        } catch (Exception ex) {
            MyLogger.LogErrorMessage(ex);
            Message.showErrorMessage(ex.getMessage());
        }
    }
    
    @Override
    public void resetEditor(){
        try {
            editor.reset();
        } catch (Exception ex) {
            MyLogger.LogErrorMessage(ex);
            Message.showErrorMessage(ex.getMessage());
        }
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }    
    
}
