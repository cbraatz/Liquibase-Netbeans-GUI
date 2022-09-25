/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.dbeditor;

import java.util.Collections;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.changelog.tag.RenameTableTag;
import org.tesis.changelog.tag.TagList;
import org.tesis.db.Constants;
import org.tesis.db.Table;
import org.tesis.db.dbms.Dbms;
import org.tesis.dbapi.Author;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.ui.WindowManager;
import org.tesis.util.Utils;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//org.tesis.dbeditor//RenameTable//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "RenameTableTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.tesis.dbeditor.RenameTableTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_RenameTableAction",
        preferredID = "RenameTableTopComponent")
@Messages({
    "CTL_RenameTableAction=RenameTable",
    "CTL_RenameTableTopComponent=Renombrar Tabla",
    "HINT_RenameTableTopComponent=Esta es la ventana de Renombramiento de Tablas"
})
public final class RenameTableTopComponent extends TopComponent {
    private final InstanceContent content= new InstanceContent();
    private Table table;
    private Author author;
    private Dbms dbms;
    public RenameTableTopComponent() {
        initComponents();
        associateLookup(new AbstractLookup(content));
        setName(Bundle.CTL_RenameTableTopComponent());
        setToolTipText(Bundle.HINT_RenameTableTopComponent());

    }
    
    public void setTable(Table table) {
        this.table = table;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setDbms(Dbms dbms) {
        this.dbms = dbms;
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        textFieldNewName = new javax.swing.JTextField();
        buttonRename = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), org.openide.util.NbBundle.getMessage(RenameTableTopComponent.class, "RenameTableTopComponent.jPanel1.border.title"))); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(RenameTableTopComponent.class, "RenameTableTopComponent.jLabel1.text")); // NOI18N

        textFieldNewName.setText(org.openide.util.NbBundle.getMessage(RenameTableTopComponent.class, "RenameTableTopComponent.textFieldNewName.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(buttonRename, org.openide.util.NbBundle.getMessage(RenameTableTopComponent.class, "RenameTableTopComponent.buttonRename.text")); // NOI18N
        buttonRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRenameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonRename)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(10, 10, 10)
                            .addComponent(textFieldNewName, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel1)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textFieldNewName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonRename)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRenameActionPerformed
        String newName = this.textFieldNewName.getText();
        if(null!=newName){
            if(!newName.isEmpty()){
                if(PropertyValueValidator.validateName(newName)){
                    try{
                        PropertyList properties=new PropertyList();
                        properties.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
                        properties.addProperty(Constants.PROPERTY_OLD_NAME, table.getName());
                        properties.addProperty(Constants.PROPERTY_NAME, newName);
                        properties.addProperty(Constants.PROPERTY_AUTHOR, author.getAuthorName());
                        properties.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
                        RenameTableTag renameTableTag=new RenameTableTag(properties, dbms);
                        TagList tags=new TagList();
                        tags.addTag(renameTableTag);
                        content.set(Collections.singleton(tags), null);//agrega al lookup
                        this.textFieldNewName.setText("");
                        content.remove(tags);//borra el Tag de content porque sino vuelve a agregarlo al lookup al volver a esta ventana
                        MyLogger.LogInformationMessage("Éxito al guardar el RenameTableTag en el lookup.", "RenameTableTopComponent");
                        this.close();
                     }catch (Exception ex) {
                        MyLogger.LogErrorMessage(ex);
                        Message.showErrorMessage("Error en el botón de Rename Table. "+ex.getMessage());
                     }
                }else{
                    Message.showWarningMessage("El nombre de la tabla no es válido.");
                }
            }else{
                Message.showWarningMessage("Debe proporcionar un nombre de tabla antes de continuar.");
            }
        }else{
            Message.showWarningMessage("El nuevo nombre de tabla es nulo.");
        }     
    }//GEN-LAST:event_buttonRenameActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonRename;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField textFieldNewName;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        WindowManager.closeEditorComponentsWithException(this);
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
