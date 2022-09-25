package org.tesis.wizard.open;

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
import org.tesis.db.dbms.Dbms;
import org.tesis.dbapi.Author;
import org.tesis.dbapi.Message;
import org.tesis.dbapi.MyLogger;
import org.tesis.dbexplorer.DbExplorerTopComponent;

@ActionID(category="...", id="org.tesis.wizard.open.WizardOpenWizardAction")
@ActionRegistration(displayName="Abrir Proyecto",iconInMenu=true, iconBase="org/tesis/wizard/open/openico.png")
@ActionReference(path="Menu/File", position=11)
public final class WizardOpenWizardAction extends TopComponent implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        List<WizardDescriptor.Panel<WizardDescriptor>> panels = new ArrayList<WizardDescriptor.Panel<WizardDescriptor>>();
        WizardOpenWizardPanel1 panel1=new WizardOpenWizardPanel1();
        panels.add(panel1);
        String[] steps = new String[panels.size()];
        for (int i = 0; i < panels.size(); i++) {
            Component c = panels.get(i).getComponent();
            // Default step name to component name of panel.
            steps[i] = c.getName();
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
        wiz.setTitle("Abrir Proyecto MyBase");
        if (DialogDisplayer.getDefault().notify(wiz) == WizardDescriptor.FINISH_OPTION) {
            try{
                DbExplorerTopComponent tc = (DbExplorerTopComponent) WindowManager.getDefault().findTopComponent("DbExplorerTopComponent");
                Author author=panel1.getAuthor();
                Dbms dbms=panel1.getDbms();
                org.tesis.ui.WindowManager.closeAllComponents();//cierra todas las ventanas antes de abrir de nuevo el explorer.
                tc.open();
                tc.init(panel1.getComponent().getConnection(),panel1.getComponent().getTextFieldPath().getText()+panel1.getComponent().getChangeLogName(), author, dbms);
            }catch (Exception ex) {
                MyLogger.LogErrorMessage(ex);
                Message.showErrorMessage("Ha ocurrido un error al abrir el proyecto. "+ex.getMessage());
            }
        }
    }
}
