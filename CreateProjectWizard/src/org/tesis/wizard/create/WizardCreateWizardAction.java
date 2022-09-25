package org.tesis.wizard.create;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.tesis.db.MyConnection;
import org.tesis.db.dbms.Dbms;
import org.tesis.dbapi.Author;
import org.tesis.dbapi.Config;
import org.tesis.dbapi.Constants;
import org.tesis.dbexplorer.DbExplorerTopComponent;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.dbapi.Util;

// An example action demonstrating how the wizard could be called from within
// your code. You can move the code below wherever you need, or register an action:

@ActionID(category="...", id="org.tesis.wizard.create.WizardCreateWizardAction")
@ActionRegistration(displayName="Crear Proyecto",iconInMenu=true, iconBase="org/tesis/wizard/create/createico.png") 
@ActionReference(path="Menu/File", position=10)
public final class WizardCreateWizardAction extends TopComponent implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        WizardCreateWizardPanel1 panel1=new WizardCreateWizardPanel1();
        WizardCreateWizardPanel2 panel2=new WizardCreateWizardPanel2();
        panels.add(panel1);
        panels.add(panel2);
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            steps[i] = c.getName();// Default step name to component name of panel.
            if (c instanceof JComponent) { // assume Swing components
                JComponent jc = (JComponent) c;
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, i);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, true);
                jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, true);
            }
        }
        WizardDescriptor wiz = new WizardDescriptor(new WizardDescriptor.ArrayIterator<WizardDescriptor>(panels));
        // {0} will be replaced by WizardDesriptor.Panel.getComponent().getName()
        wiz.setTitleFormat(new MessageFormat("{0}"));
        wiz.setTitle("Definición de nuevo Proyecto");
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            MyConnection mc=panel2.getComponent().getConnection();
            Dbms dbms=panel2.getComponent().getSelectedDbms();
            if(null!=mc && null!=dbms){
                try {
                    if(Config.writeConfigFile(mc,panel1.getDirectory(),Util.getFileName(panel1.getChangeLogPath()),panel1.getAuthorName(),dbms)){
                        Message.showInformationMessage("Proyecto creado exitosamente");
                        Author author=Config.getAuthorFromConfigFile(panel1.getDirectory()+Constants.DEFAULT_CONFIG_FILE_NAME);
                        org.tesis.ui.WindowManager.closeAllComponents();//cierra todas las ventanas antes de abrir de nuevo el explorer.
                        DbExplorerTopComponent tc = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
                        tc.open();
                        tc.init(mc, panel1.getChangeLogPath(), author, dbms);
                    }else{
                        Message.showWarningMessage("El Proyecto no pudo ser creado");
                    }
                }catch (Exception ex) {
                    MyLogger.LogErrorMessage(ex);
                    Message.showErrorMessage("Ha ocurrido un error al crear el proyecto. "+ex.getMessage());
                }
            }
        }
    }
}
