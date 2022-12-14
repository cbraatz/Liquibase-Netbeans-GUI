package org.tesis.dbeditor;

import java.util.Collections;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.tag.RemoveTableTag;
import org.tesis.changelog.tag.TagList;
import org.tesis.changelog.tag.TestTag;
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
        dtd = "-//org.tesis.dbeditor//RemoveTable//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "RemoveTableTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "org.tesis.dbeditor.RemoveTableTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_RemoveTableAction",
        preferredID = "RemoveTableTopComponent")
@Messages({
    "CTL_RemoveTableAction=RemoveTable",
    "CTL_RemoveTableTopComponent=Borrar Tabla",
    "HINT_RemoveTableTopComponent=This is a RemoveTable window"
})
public final class RemoveTableTopComponent extends TopComponent {
    private final InstanceContent content= new InstanceContent();
    private Table table;
    private Author author;
    private Dbms dbms;
    public RemoveTableTopComponent() {
        initComponents();
        associateLookup(new AbstractLookup(content));
        setName(Bundle.CTL_RemoveTableTopComponent());
        setToolTipText(Bundle.HINT_RemoveTableTopComponent());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), org.openide.util.NbBundle.getMessage(RemoveTableTopComponent.class, "RemoveTableTopComponent.jPanel1.border.title"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(RemoveTableTopComponent.class, "RemoveTableTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(RemoveTableTopComponent.class, "RemoveTableTopComponent.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(RemoveTableTopComponent.class, "RemoveTableTopComponent.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            PropertyList properties=new PropertyList();
            properties.addProperty(Constants.PROPERTY_ID, Long.toString(Utils.generateUniqueID()));
            properties.addProperty(Constants.PROPERTY_NAME, table.getName());
            properties.addProperty(Constants.PROPERTY_AUTHOR, author.getAuthorName());
            properties.addProperty(Constants.PROPERTY_DATE, Utils.getCurrentDate());
            RemoveTableTag removeTableTag=new RemoveTableTag(properties, dbms);
            TagList tags=new TagList();
            tags.addTag(removeTableTag);
            content.set(Collections.singleton(tags), null);//agrega al lookup
            content.remove(tags);//borra el Tag de content porque sino vuelve a agregarlo al lookup al volver a esta ventana
            MyLogger.LogInformationMessage("??xito al guardar el RemoveTableTag en el lookup.", "RemoveTableTopComponent");
        } catch (Exception ex) {
            MyLogger.LogErrorMessage(ex);
            Message.showErrorMessage("Error en el bot??n de Remove Table. "+ex.getMessage());
        }
               
        this.close();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setDbms(null);
        this.setTable(null);
        this.setAuthor(null);
        this.close();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
    public void setTable(Table table) {
        this.table = table;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setDbms(Dbms dbms) {
        this.dbms = dbms;
    }
    
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
