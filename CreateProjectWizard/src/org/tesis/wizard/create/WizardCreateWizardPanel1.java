package org.tesis.wizard.create;

import org.tesis.dbapi.Util;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.tesis.dbapi.MyLogger;
import org.tesis.jaxb.JXChangeLog;
import org.tesis.jaxb.JXHeader;
import org.tesis.util.Utils;

public class WizardCreateWizardPanel1 implements WizardDescriptor.Panel<WizardDescriptor> , PropertyChangeListener{
    private WizardCreateVisualPanel1 component;
    private boolean isValid = false; 
    private boolean existsChanteLog=false;//si ya existe o si el changelog fue creado correctamente
    private final EventListenerList listeners =  new EventListenerList(); 
    private String changeLogPath;
    private String directory;
    
    @Override
    public WizardCreateVisualPanel1 getComponent() {        
        if (component == null) {
            component = new WizardCreateVisualPanel1();
        }
        return component;
    }
    
    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        getComponent().addPropertyChangeListener(this); 
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {
        if (wiz.getValue().toString().equals(WizardDescriptor.NEXT_OPTION)) {
            if(getComponent().getRadioButtonChangeLogNuevo().isSelected()){
                changeLogPath=getComponent().getTextFieldPath().getText()+getComponent().getTextFieldNombreChangeLog().getText()+".xml";
                directory=getComponent().getTextFieldPath().getText();
                JXChangeLog chl=new JXChangeLog(new JXHeader(getComponent().getTextFieldProject().getText(),getComponent().getTextFieldAuthor().getText(),Utils.getCurrentDate()));
                try {
                    chl.generateXmlFile(changeLogPath);
                    existsChanteLog=true;
                } catch (Exception ex) {
                    MyLogger.LogErrorMessage(ex);
                    existsChanteLog=false;
                }
            }else{
                changeLogPath=getComponent().getTextFieldPath().getText();
                directory=Util.getDirectoryPath(getComponent().getTextFieldPath().getText());
                existsChanteLog=true;
            }
            (wiz).putProperty("db", (getComponent()).getTextFieldNombreDb().getText());//nombre de la bd
            (wiz).putProperty("projectName", (getComponent()).getTextFieldProject().getText());//nombre del proyecto
            (wiz).putProperty("changeLogSaved", existsChanteLog); //si se pudo crear o encontrar el changelog
            (wiz).putProperty("configValues", getComponent().getConfigValues()); //valores del archivo de configuracion (es nulo si no se seleccionó un changeLog)
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        boolean oldState = isValid; 
        isValid = checkValidity(); 
        fireChangeEvent(this, oldState, isValid); 
    }
    
     private boolean checkValidity() { 
         return ! (this.getComponent().getTextFieldProject().getText().isEmpty() || getComponent().getTextFieldAuthor().getText().isEmpty() || getComponent().getTextFieldNombreDb().getText().isEmpty() || !getComponent().isValidChangeLogValues()); 
    } 

    
   
    @Override
    public void addChangeListener(ChangeListener l) { 
       listeners.add(ChangeListener.class, l); 
    } 
    
    @Override
    public void removeChangeListener(ChangeListener l) { 
       listeners.remove(ChangeListener.class, l); 
    } 

    public boolean isExistsChanteLog() {
        return existsChanteLog;
    }

    public String getChangeLogPath() {
        return changeLogPath;
    }

    public String getDirectory() {
        return directory;
    }

    public String getAuthorName(){
       return this.getComponent().getTextFieldAuthor().getText();
    }
    
    protected final void fireChangeEvent(Object source, boolean oldState, boolean newState) { 
       if(oldState != newState) { 
          ChangeEvent ev = new ChangeEvent(source); 
          for (ChangeListener listener : listeners.getListeners(ChangeListener.class)) { 
             listener.stateChanged(ev); 
          } 
       } 
    } 
}