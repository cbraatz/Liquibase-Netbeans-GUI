package org.tesis.dbeditor;

import java.util.Collections;
import java.util.List;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.changelog.tag.AddColumnTag;
import org.tesis.changelog.tag.DataColumnTag;
import org.tesis.changelog.tag.EditColumnNullableTag;
import org.tesis.changelog.tag.EditColumnTag;
import org.tesis.changelog.tag.EditDataTag;
import org.tesis.changelog.tag.EditTablePKTag;
import org.tesis.changelog.tag.PKTag;
import org.tesis.changelog.tag.RemoveColumnTag;
import org.tesis.changelog.tag.RenameTableTag;
import org.tesis.changelog.tag.SetNullDataToColumnTag;
import org.tesis.changelog.tag.TagList;
import org.tesis.db.Column;
import org.tesis.db.Constants;
import org.tesis.db.DataColumn;
import org.tesis.db.DataRow;
import org.tesis.db.PK;
import org.tesis.db.PKColumnList;
import org.tesis.db.Table;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.exception.InvalidParameterException;
import org.tesis.mybase.ColumnList;
import org.tesis.mybase.DataColumnList;
import org.tesis.mybase.DataPKList;
import org.tesis.ui.TableEditor;
import org.tesis.util.Utils;

@ConvertAsProperties(
        dtd = "-//org.tesis.dbeditor//EditTable//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "EditTableTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.tesis.dbeditor.EditTableTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_EditTableAction",
        preferredID = "EditTableTopComponent")
@NbBundle.Messages({
    "CTL_EditTableAction=EditTable",
    "CTL_EditTableTopComponent=Editar Tabla",
    "HINT_EditTableTopComponent=Esta es la ventana de Edición de Tablas"
})
public class EditTableTopComponent extends AddEditTopComponent{
    Table originalTable;
    public EditTableTopComponent() {
        setName(Bundle.CTL_EditTableTopComponent());
        setToolTipText(Bundle.HINT_EditTableTopComponent());
    }

    public Table getTable() {
        return originalTable;
    }

    public void setTable(Table table) {
        this.originalTable = table;
    }

    @Override
    public void initEditor() {
        editor= new TableEditor(this.getDbms());
        editor.setKeyNameVisibility(false);
        editor.setEditMode(true);
        this.loadTable();
    }
    
    private void loadTable(){
        this.setTableName(originalTable.getName());
        this.getEditor().setValues(originalTable.getColumns());
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
        TagList tags=new TagList();
        TagList tags2=new TagList();//lo que se ejecuta despues del editPK
        //TagList auxTags=new TagList();//se crea otro xq todos los tags de editTable deben ir al final de la lista de tags, despues del EditTablaPKTag
        PKColumnList pks=new PKColumnList();
        String tableName=getTableName();
        if(!tableName.isEmpty()){
            if(PropertyValueValidator.validateName(tableName)){
                try {
                    ColumnList li=(ColumnList)this.getEditor().exportEditorResults();
                    List<Column> columns=li.getColumnList();
                    if(!columns.isEmpty()){
                        boolean hasPK=false;
                        for(Column c:columns){
                            if(c.isPk()){
                                hasPK=true;
                                break;
                            }
                        }
                        if(hasPK){ 
                            String newName = this.getTextFieldTableName().getText();
                            if(null!=newName){
                                if(!originalTable.getName().equals(newName)){
                                    if(!newName.isEmpty()){
                                        if(PropertyValueValidator.validateName(newName)){
                                            try {
                                                PropertyList properties=new PropertyList();
                                                properties.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                                properties.addProperty(Constants.PROPERTY_OLD_NAME, originalTable.getName());
                                                properties.addProperty(Constants.PROPERTY_NAME, newName);
                                                properties.addProperty(Constants.PROPERTY_AUTHOR, getAuthor().getAuthorName());
                                                properties.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                                RenameTableTag renameTableTag=new RenameTableTag(properties, this.getDbms());
                                                tags.addTag(renameTableTag);
                                            } catch (Exception ex) {
                                                MyLogger.LogErrorMessage(ex);
                                                Message.showErrorMessage("Error en el botón de Rename Table. "+ex.getMessage());
                                            }
                                        }else{
                                            Message.showWarningMessage("El nombre de la tabla no es válido.");
                                        }
                                    }else{
                                        Message.showWarningMessage("Debe proporcionar un nombre de tabla antes de continuar.");
                                    }
                                }
                            }else{
                                 Message.showWarningMessage("El nuevo nombre de tabla es nulo.");
                            } 
                            for(Column cc:columns){
                                boolean exists=false;
                                for(Column tc:originalTable.getColumns()){
                                    if(tc.getInternalId() == cc.getInternalId()){
                                        exists=true;
                                        break;
                                    }
                                        
                                }
                                if(!exists){//si la columna existe en la lista de columnas nuevas y no en la vieja
                                    PropertyList pl=new PropertyList();
                                    pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                    pl.addProperty(Constants.PROPERTY_AUTHOR, getAuthor().getAuthorName());
                                    pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                    pl.addProperty(Constants.PROPERTY_TABLE_NAME, newName);
                                    pl.addProperty(Constants.PROPERTY_NAME, cc.getName());
                                    pl.addProperty(Constants.PROPERTY_TYPE, cc.getType().getPublicName());
                                    if(cc.getType().hasLenght()){
                                        pl.addProperty(Constants.PROPERTY_LENGHT, cc.getType().getLenght().toString());
                                    }
                                    pl.addProperty(Constants.PROPERTY_NULLABLE, Boolean.toString(cc.isAllowNull()));
                                    pl.addProperty(Constants.PROPERTY_PRIMARY_KEY, Boolean.toString(cc.isPk()));
                                    AddColumnTag addColumnTag=new AddColumnTag(pl, this.getDbms());
                                    tags.addTag(addColumnTag);
                                    if(cc.isPk()){
                                       pks.addPkColumn(cc.getName());
                                    }
                                    /*agregado en 2017 para agregar valores por defecto de una columna not null y valores por defecto*/

                                    if(originalTable.getRows().size() > 0 && !cc.isAllowNull()){
                                         //Inserto datos por defecto a cada fila
                                         for(DataRow row:this.originalTable.getRows()){
                                             PropertyList props=new PropertyList();
                                             props.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                             props.addProperty(Constants.PROPERTY_TABLE_NAME, row.getTableName());
                                             props.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor().getAuthorName());
                                             props.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                             props.addProperty(Constants.PROPERTY_INTERNAL_ID, row.getInternalId());
                                             //DataColumn dc=row.getDataColumnByName(cc.getName());
                                             DataColumnList dcl=new DataColumnList();
                                             PropertyList propsx=new PropertyList();
                                             propsx.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                             propsx.addProperty(Constants.PROPERTY_NAME, cc.getName());
                                             propsx.addProperty(Constants.PROPERTY_VALUE, cc.getType().getDefaultValue());
                                             propsx.addProperty(Constants.PROPERTY_QUOTED, Boolean.toString(cc.getType().isInsertRequestWithQuotes()));
                                             dcl.addDataColumn(new DataColumnTag(propsx, this.getDbms()));
                                             tags.addTag(new EditDataTag(props, dcl, this.getDbms()));
                                         }
                                         //agrego el nullable de la columna
                                         if(!cc.isAllowNull()){
                                              PropertyList plx=new PropertyList();
                                              plx.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                              plx.addProperty(Constants.PROPERTY_AUTHOR, getAuthor().getAuthorName());
                                              plx.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                              plx.addProperty(Constants.PROPERTY_TABLE_NAME, newName);
                                              plx.addProperty(Constants.PROPERTY_COLUMN_NAME, cc.getName());
                                              plx.addProperty(Constants.PROPERTY_TYPE, cc.getType().getPublicName());
                                              if(cc.getType().hasLenght()){
                                                  plx.addProperty(Constants.PROPERTY_LENGHT, cc.getType().getLenght().toString());
                                              }
                                              plx.addProperty(Constants.PROPERTY_OLD_NULLABLE, "true");
                                              plx.addProperty(Constants.PROPERTY_NULLABLE, "false");
                                              tags.addTag(new EditColumnNullableTag(plx, this.getDbms()));
                                         }
                                    }//hasta aca agregado en 2017

                                }
                            }
                            
                            //INICIA EDICION DE DATOS
                            for(Column tc:originalTable.getColumns()){
                                boolean exists=false;
                                for(Column cc:columns){
                                    if(tc.getInternalId() == cc.getInternalId()){
                                       exists=true;
                                       if(!(tc.compareName(cc) && tc.compareType(cc))){//si hay alguna diferencia entre ambas columnas en cuanto a tipo de dato o nombre
                                            if(!(originalTable.getRows().size() > 0 && tc.isPk())){//si la columna es pk y la tabla tiene datos, entonces no hacer lo sgte
                                                if(!tc.compareType(cc)){//si hay diferencia en el tipo de dato
                                                     if(originalTable.getRows().size() > 0){
                                                         //#1 quito el nullable de la columna si tiene
                                                         if(!tc.isAllowNull()){
                                                              PropertyList pl=new PropertyList();
                                                              pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                                              pl.addProperty(Constants.PROPERTY_AUTHOR, getAuthor().getAuthorName());
                                                              pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                                              pl.addProperty(Constants.PROPERTY_TABLE_NAME, newName);
                                                              pl.addProperty(Constants.PROPERTY_COLUMN_NAME, tc.getName());
                                                              pl.addProperty(Constants.PROPERTY_TYPE, tc.getType().getPublicName());
                                                              if(tc.getType().hasLenght()){
                                                                  pl.addProperty(Constants.PROPERTY_LENGHT, tc.getType().getLenght().toString());
                                                              }
                                                              pl.addProperty(Constants.PROPERTY_OLD_NULLABLE, "false");
                                                              pl.addProperty(Constants.PROPERTY_NULLABLE, "true");
                                                              tags2.addTag(new EditColumnNullableTag(pl, this.getDbms()));
                                                         }
                                                         PropertyList pl=new PropertyList();
                                                         pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                                         pl.addProperty(Constants.PROPERTY_AUTHOR, getAuthor().getAuthorName());
                                                         pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                                         pl.addProperty(Constants.PROPERTY_TABLE_NAME, newName);
                                                         pl.addProperty(Constants.PROPERTY_COLUMN_NAME, tc.getName());
                                                         pl.addProperty(Constants.PROPERTY_TYPE, tc.getType().getPublicName());
                                                         if(tc.getType().hasLenght()){
                                                             pl.addProperty(Constants.PROPERTY_LENGHT, tc.getType().getLenght().toString());
                                                         }
                                                         tags2.addTag(new SetNullDataToColumnTag(pl, this.getDbms()));     
                                                     }
                                                }//termino de hacer los pasos previos para editar el tipo de dato, luego lo edito y despues de eso debo volver a poner todo a su lugar (nullable, pk, fk, etc)

                                                //edito el tipo de dato o actualizacion del nombre
                                                //ESTO YA TENIA ANTES DE EDITAR LOS DATOS
                                                PropertyList pl2=new PropertyList();
                                                pl2.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                                pl2.addProperty(Constants.PROPERTY_AUTHOR, getAuthor().getAuthorName());
                                                pl2.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                                pl2.addProperty(Constants.PROPERTY_TABLE_NAME, newName);
                                                pl2.addProperty(Constants.PROPERTY_COLUMN_NAME, tc.getName());
                                                pl2.addProperty(Constants.PROPERTY_OLD_TYPE, tc.getType().getPublicName());
                                                if(tc.getType().hasLenght()){
                                                    pl2.addProperty(Constants.PROPERTY_OLD_LENGHT, tc.getType().getLenght().toString());
                                                }
                                                if(!tc.compareName(cc)){
                                                    pl2.addProperty(Constants.PROPERTY_NEW_NAME, cc.getName());    
                                                }
                                                if(!tc.compareType(cc)){
                                                    pl2.addProperty(Constants.PROPERTY_TYPE, cc.getType().getPublicName());
                                                    if(cc.getType().hasLenght()){
                                                        pl2.addProperty(Constants.PROPERTY_LENGHT, cc.getType().getLenght().toString());
                                                    }
                                                }
                                                if(!tc.compareNullable(cc)){
                                                    pl2.addProperty(Constants.PROPERTY_OLD_NULLABLE, Boolean.toString(tc.isAllowNull()));
                                                    pl2.addProperty(Constants.PROPERTY_NULLABLE, Boolean.toString(cc.isAllowNull()));
                                                }
                                                tags2.addTag(new EditColumnTag(pl2, this.getDbms()));
                                                //HASTA ACA ESTO YA TENIA ANTES DE EDITAR LOS DATOS

                                                //ahora empiezo a convertir los datos
                                                if(!tc.compareType(cc)){//si hay diferencia en el tipo de dato
                                                     if(originalTable.getRows().size() > 0){
                                                         //#1 hago la conversion de cada dato al valor nuevo
                                                         for(DataRow row:this.originalTable.getRows()){
                                                             PropertyList props=new PropertyList();
                                                             props.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                                             props.addProperty(Constants.PROPERTY_TABLE_NAME, row.getTableName());
                                                             props.addProperty(Constants.PROPERTY_AUTHOR, this.getAuthor().getAuthorName());
                                                             props.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                                             props.addProperty(Constants.PROPERTY_INTERNAL_ID, row.getInternalId());
                                                             /*DataPKList pks2 = new DataPKList();
                                                             for(PK pk:this.originalTable.getPKsFromTableByRowId(row.getInternalId()).getPKs()){
                                                                 PropertyList props2=new PropertyList();
                                                                 props2.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                                                 props2.addProperty(Constants.PROPERTY_COLUMN_NAME, pk.getColumnName());
                                                                 props2.addProperty(Constants.PROPERTY_VALUE, pk.getValue());
                                                                 props2.addProperty(Constants.PROPERTY_QUOTED, Boolean.toString(pk.isQuoted()));
                                                                 pks2.addPKTag(new PKTag(props2, this.getDbms()));
                                                             }*/
                                                             try{
                                                                DataColumn dc=row.getDataColumnByName(tc.getName());//agrega solo el dato que se desea editar (re-agregar), no todas las columnas
                                                                DataColumnList dcl=new DataColumnList();
                                                                PropertyList props3=new PropertyList();
                                                                props3.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                                                props3.addProperty(Constants.PROPERTY_NAME, dc.getColumnName());
                                                                props3.addProperty(Constants.PROPERTY_VALUE, tc.getType().convertTo(cc.getType(), dc.getValue()));
                                                                props3.addProperty(Constants.PROPERTY_QUOTED, Boolean.toString(cc.getType().isInsertRequestWithQuotes()));
                                                                dcl.addDataColumn(new DataColumnTag(props3, this.getDbms()));
                                                                tags2.addTag(new EditDataTag(props, dcl, this.getDbms()));
                                                             }catch(InvalidParameterException e){//if = org.tesis.exception.InvalidParameterException: No se encontró DataColumn con nombre, o sea si el valor de esa columna es nulo
                                                                System.out.println("El valor de la columna "+tc.getName()+" es nulo, pero no importa, es nullable."); 
                                                             }
                                                             
                                                         }
                                                         //#2 vuelvo a agregar el nullable de la columna si tenía
                                                         if(!tc.isAllowNull()){
                                                              PropertyList pl=new PropertyList();
                                                              pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                                              pl.addProperty(Constants.PROPERTY_AUTHOR, getAuthor().getAuthorName());
                                                              pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                                              pl.addProperty(Constants.PROPERTY_TABLE_NAME, newName);
                                                              pl.addProperty(Constants.PROPERTY_COLUMN_NAME, cc.getName());
                                                              pl.addProperty(Constants.PROPERTY_TYPE, cc.getType().getPublicName());
                                                              if(cc.getType().hasLenght()){
                                                                  pl.addProperty(Constants.PROPERTY_LENGHT, cc.getType().getLenght().toString());
                                                              }
                                                              pl.addProperty(Constants.PROPERTY_OLD_NULLABLE, "true");
                                                              pl.addProperty(Constants.PROPERTY_NULLABLE, "false");
                                                              tags2.addTag(new EditColumnNullableTag(pl, this.getDbms()));
                                                         }

                                                     }
                                                }   
                                            }else{
                                               //Message.showWarningMessage("No es posible editar una clave primaria de una tabla con datos.");
                                                throw new Exception("No es posible editar una clave primaria de una tabla con datos.");
                                            }
                                            //TERMINA EDICION DE DATOS
                                       } 
                                       if(!(tc.compareNullable(cc))){//si hay alguna diferencia entre ambas columnas en cuanto a nullable
                                            if(!(originalTable.getRows().size() > 0 && tc.isPk())){//si la columna es pk y la tabla tiene datos, entonces no hacer lo sgte
                                                PropertyList pl=new PropertyList();
                                                pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                                pl.addProperty(Constants.PROPERTY_AUTHOR, getAuthor().getAuthorName());
                                                pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                                pl.addProperty(Constants.PROPERTY_TABLE_NAME, newName);
                                                pl.addProperty(Constants.PROPERTY_COLUMN_NAME, cc.getName());
                                                pl.addProperty(Constants.PROPERTY_TYPE, cc.getType().getPublicName());
                                                if(cc.getType().hasLenght()){
                                                    pl.addProperty(Constants.PROPERTY_LENGHT, cc.getType().getLenght().toString());
                                                }
                                                pl.addProperty(Constants.PROPERTY_OLD_NULLABLE, Boolean.toString(tc.isAllowNull()));
                                                pl.addProperty(Constants.PROPERTY_NULLABLE, Boolean.toString(cc.isAllowNull()));
                                                tags2.addTag(new EditColumnNullableTag(pl, this.getDbms()));
                                            }else{
                                                 throw new Exception("No es posible editar una clave primaria de una tabla con datos.");
                                            }
                                        } 
                                        if(cc.isPk()){
                                           pks.addPkColumn(cc.getName());
                                        }
                                        break;
                                         
                                     }
                                }
                                if(!exists){//si la columna existe en la lista de columnas vieja y no en la nueva debe borrarse
                                    PropertyList pl=new PropertyList();
                                    pl.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                                    pl.addProperty(Constants.PROPERTY_AUTHOR, getAuthor().getAuthorName());
                                    pl.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                                    pl.addProperty(Constants.PROPERTY_TABLE_NAME, newName);
                                    pl.addProperty(Constants.PROPERTY_NAME, tc.getName());
                                    RemoveColumnTag removeColumnTag=new RemoveColumnTag(pl, this.getDbms());
                                    tags2.addTag(removeColumnTag);
                                }
                            }
               
                            //al principio se agrega al add o remove column luego el edit-pk; si fue modificada la clave primaria de alguna columna
                            if(!pks.getPkColumnNames().equals(this.originalTable.getPKColumnList().getPkColumnNames())){
                               PropertyList ple=new PropertyList();
                               ple.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                               ple.addProperty(Constants.PROPERTY_AUTHOR, getAuthor().getAuthorName());
                               ple.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                               ple.addProperty(Constants.PROPERTY_TABLE_NAME, newName);
                               ple.addProperty(Constants.PROPERTY_PK_NAME, originalTable.getPrimaryKeyName());
                               //ple.addProperty(Constants.PROPERTY_OLD_PKS, originalTable.getPKColumnList().getPkColumnNames());
                               ple.addProperty(Constants.PROPERTY_PKS, pks.getPkColumnNames());
                               EditTablePKTag editTablePkTag=new EditTablePKTag(ple, this.getDbms());
                               tags.addTag(editTablePkTag);
                            }
                            if(!tags2.isEmpty()){//agrega el EditNullable y RemoveColumn despues del editTablePk
                                tags.addTags(tags2.getTagList());
                            }
                            if(!tags.isEmpty()){
                                this.getContent().set(Collections.singleton(tags), null);//agrega al lookup
                                this.getContent().remove(tags);//borra el TagList de content porque sino vuelve a agregarlo al lookup al volver a esta ventana
                                MyLogger.LogInformationMessage("Éxito al guardar el EditColumn-TagList en el lookup.", "EditTableTopComponent");
                                resetEditor();
                                this.close();
                            }else{
                                Message.showInformationMessage("No hay cambios que guardar.");
                            }
                        }else{
                            Message.showWarningMessage("No se encontró clave primaria en la tabla.");
                        }
                    }else{
                        Message.showWarningMessage("Debe cargar al menos una columna a la tabla antes de continuar.");
                    }
                } catch (Exception ex) {
                    MyLogger.LogErrorMessage(ex);
                    Message.showErrorMessage("Error en el botón guardar de Edit Table. "+ex.getMessage());
                }
            }else{
                Message.showWarningMessage("El nombre de la tabla no es válido.");
            }
        }else{
            Message.showWarningMessage("Debe proporcionar un nombre de tabla antes de continuar.");
        }
    }
    
    @Override
    public void resetEditor(){
        try {
            this.setTableName(originalTable.getName());
            this.editor.reset();
            this.getPanelContainer().revalidate();
            validate();
        } catch (Exception ex) {
            MyLogger.LogErrorMessage(ex);
            Message.showErrorMessage(ex.getMessage());
        }
        //this.initEditor();
       // this.loadTable();
    }
}
