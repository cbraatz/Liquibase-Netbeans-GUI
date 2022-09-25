package org.tesis.dbeditor;

import java.util.Collections;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.changelog.tag.NewTableTag;
import org.tesis.changelog.tag.TagList;
import org.tesis.db.Column;
import org.tesis.db.Constants;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.mybase.ColumnList;
import org.tesis.ui.TableEditor;
import org.tesis.util.Utils;

@ConvertAsProperties(
        dtd = "-//org.tesis.dbeditor//AddTable//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "AddTableTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.tesis.dbeditor.AddTableTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AddTableAction",
        preferredID = "AddTableTopComponent")
@NbBundle.Messages({
    "CTL_AddTableAction=AddTable",
    "CTL_AddTableTopComponent=Agregar Tabla",
    "HINT_AddTableTopComponent=Esta es la ventana de Adición de Tablas"
})
public class AddTableTopComponent extends AddEditTopComponent{

    public AddTableTopComponent() {
        setName(Bundle.CTL_AddTableTopComponent());
        setToolTipText(Bundle.HINT_AddTableTopComponent());
    }

    @Override
    public void initEditor() {
        editor= new TableEditor(this.getDbms());
        editor.setKeyNameLabel("Nombre del PK de la nueva tabla:");
    }

    @Override
    public void resetEditor() {
        try {
            this.setTableName("");
            this.editor.reset();
            this.getPanelContainer().revalidate();
            validate();
        } catch (Exception ex) {
            MyLogger.LogErrorMessage(ex);
            Message.showErrorMessage(ex.getMessage());
        }
    }
    

    @Override
    public void componentClosed() {
        this.getTextFieldTableName().setText("");//limpia para que la próxima vez que se abra la ventana no aparezca el nombre de la tabla cargado a mano.
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
        String table=getTableName();
        String fkName=editor.getKeyNamePanel().getTextFieldKeyName().getText();
        if(!table.isEmpty()){
            if(!fkName.isEmpty()){
                if(PropertyValueValidator.validateName(table)){
                    try {
                        ColumnList columns=(ColumnList)this.getEditor().exportEditorResults();
                        if(!columns.isEmpty()){
                            boolean hasPK=false;
                            for(Column c:columns.getColumnList()){
                                if(c.isPk()){
                                    hasPK=true;
                                    break;
                                }
                            }
                            if(hasPK){ 
                                PropertyList properties=new PropertyList();
                                properties.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                properties.addProperty(Constants.PROPERTY_NAME, table);
                                properties.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor().getAuthorName());
                                properties.addProperty(Constants.PROPERTY_PK_NAME, fkName);
                                properties.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                NewTableTag newTableTag=new NewTableTag(properties, columns, this.getDbms());
                                TagList tags=new TagList();
                                tags.addTag(newTableTag);
                                this.getContent().set(Collections.singleton(tags), null);//agrega al lookup
                                this.getContent().remove(tags);//borra el Tag de content porque sino vuelve a agregarlo al lookup al volver a esta ventana
                                MyLogger.LogInformationMessage("Éxito al guardar el NewTableTag en el lookup.", "AddTableTopComponent");
                                resetEditor();
                            }else{
                                Message.showWarningMessage("No se encontró clave primaria en la tabla.");
                            }
                        }else{
                            Message.showWarningMessage("Debe cargar al menos una columna a la tabla antes de continuar.");
                        }
                    } catch (Exception ex) {
                        MyLogger.LogErrorMessage(ex);
                        Message.showErrorMessage("Error en el botón guardar de New Table. "+ex.getMessage());
                    }
                }else{
                    Message.showWarningMessage("El nombre de la tabla no es válido.");
                }
            }else{
                Message.showWarningMessage("Debe proporcionar un nombre de la clave primaria antes de continuar.");
            }
        }else{
            Message.showWarningMessage("Debe proporcionar un nombre de tabla antes de continuar.");
        }
    }
}
