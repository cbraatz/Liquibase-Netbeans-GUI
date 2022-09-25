/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.wizard.open;

import java.io.FileNotFoundException;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.tesis.db.MyConnection;
import org.tesis.db.dbms.Dbms;
import org.tesis.dbapi.Author;
import org.tesis.dbapi.Config;
import org.tesis.dbapi.Constants;
import org.tesis.dbapi.FileChooser;
import org.tesis.dbapi.Message;

public final class WizardOpenVisualPanel1 extends JPanel implements DocumentListener{
    /**
     * Creates new form WizardOpenVisualPanel1
     */
    private MyConnection myConn=null;
    private String changeLogName=null;
    private Author author=null;
    private Dbms dbms=null;
    public WizardOpenVisualPanel1() {
        initComponents();
        this.textFieldPath.getDocument().addDocumentListener(this); 
    }    
        
    @Override
    public String getName() {
        return "Selección de Proyecto";
    }

    public MyConnection getConnection(){
        return myConn;
    }

    public String getChangeLogName() {
        return changeLogName;
    }
    public String getDirectory(){
        return this.textFieldPath.getText();
    }

    public Author getAuthor() {
        return author;
    }

    public Dbms getDbms() {
        return dbms;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        textFieldPath = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), org.openide.util.NbBundle.getMessage(WizardOpenVisualPanel1.class, "WizardOpenVisualPanel1.jPanel1.border.title"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(WizardOpenVisualPanel1.class, "WizardOpenVisualPanel1.jLabel6.text")); // NOI18N

        textFieldPath.setEditable(false);
        textFieldPath.setText(org.openide.util.NbBundle.getMessage(WizardOpenVisualPanel1.class, "WizardOpenVisualPanel1.textFieldPath.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(WizardOpenVisualPanel1.class, "WizardOpenVisualPanel1.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(textFieldPath)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 218, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldPath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        FileChooser fc;
        String path;

        fc=new FileChooser();
        boolean valid=false;
        do{
            fc.chooserAction();
            path=fc.getSelectedFile();
            boolean fileFound=false;
            if(null != path){
                try {
                    this.myConn=Config.getConnectionFromConfigFile(path+Constants.DEFAULT_CONFIG_FILE_NAME, dbms);
                    this.changeLogName=Config.getChangeLogNameFromConfigFile(path+Constants.DEFAULT_CONFIG_FILE_NAME);
                    this.author=Config.getAuthorFromConfigFile(path+Constants.DEFAULT_CONFIG_FILE_NAME);
                    this.dbms=Config.getDbmsFromConfigFile(path+Constants.DEFAULT_CONFIG_FILE_NAME);
                    fileFound=true;
                }catch(FileNotFoundException e){
                   // System.out.println("FileNotFoundException");
                } catch (Exception ex) {
                   // System.out.println("Exception");
                    fileFound=true;//si tira Exception es porque tira alguna otra exception pero FileNotFoundException
                }
                if(!fileFound){
                    Message.showWarningMessage("No se ha encontrado el archivo de configuración en este directorio.");
                    valid=false;
                }else{
                    if(!(null!=myConn && null!=changeLogName && null!=author)){
                        Message.showWarningMessage("El archivo de configuración no tiene los valores requeridos ('jdbc.drivers' , 'jdbc.url,jdbc.username' , 'jdbc.password', 'changeLogName' o 'authorName').");
                        valid=false;
                    }else{
                        valid=true;
                    }
                }
            }
        }while(!(valid || null == path));
        this.textFieldPath.setText(path);

    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField textFieldPath;
    // End of variables declaration//GEN-END:variables
    private void executeDocumentListenerAction(DocumentEvent e){
        if (this.textFieldPath.getDocument() == e.getDocument()) {//para recordar el valor anterior antes de cambiar de radiobutton
            firePropertyChange("DIRECTORIO_ORIGEN", null, this.textFieldPath.getText());
        }
    }

    public JTextField getTextFieldPath() {
        return textFieldPath;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        executeDocumentListenerAction(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        executeDocumentListenerAction(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        executeDocumentListenerAction(e);
    }
}