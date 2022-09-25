package org.tesis.wizard.create;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ComboBoxModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.tesis.dbapi.ConfigValues;
import org.tesis.dbapi.Message;

public class WizardCreateWizardPanel2 implements WizardDescriptor.Panel<WizardDescriptor> , PropertyChangeListener {
    private WizardCreateVisualPanel2 component;
    private String db;//nombre de la base de datos obtenida del panel 1.
    private String url;
    private boolean changeLogSavedInStep1=false;
    private boolean isValid = false; 
    @Override
    public WizardCreateVisualPanel2 getComponent() {
        if (component == null) {
            component = new WizardCreateVisualPanel2();
        }
        return component;
    }
    @Override
    public HelpCtx getHelp() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    public boolean isValid() {
        return this.isValid;
    }

    @Override
    public void readSettings(WizardDescriptor wiz) {
        getComponent().addPropertyChangeListener(this);
        this.db=wiz.getProperty("db").toString();
        changeLogSavedInStep1=(boolean) wiz.getProperty("changeLogSaved");
        ConfigValues configValues=(ConfigValues) wiz.getProperty("configValues");
        this.updateAll(configValues);
        getComponent().setDbName(db);
        getComponent().updateUrl(); 
        this.isValid=this.checkValidity();
        if(! this.changeLogSavedInStep1){
            Message.showWarningMessage("El changelog no pudo ser guardado correctamente en el Paso 1. Favor verifíquelo.");
        }
    }
    public void updateAll(ConfigValues configValues){
        if(null!=configValues){
            this.getComponent().setHost(configValues.getHost());
            this.getComponent().setPort(configValues.getPort());
            this.getComponent().setUser(configValues.getDbUser());
            this.getComponent().setPass(configValues.getDbPass());
            this.getComponent().selectDBMSFromComboBox(configValues.getDbms());
        }
    }
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        
    }

    public String getDb() {
        return db;
    }

    public String getUrl() {
        return url;
    }
    
    public String getPort(){
        return getComponent().getTextFieldPuerto().getText();
    }
    
    public String getHost(){
        return getComponent().getTextFieldHost().getText();
    }
    
    public boolean isChangeLogSavedInStep1() {
        return changeLogSavedInStep1;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        boolean oldState = isValid; 
        isValid = checkValidity(); 
        fireChangeEvent(this, oldState, isValid); 
    }
    
    private boolean checkValidity() { 
         return !( getComponent().getTextFieldUrl().getText().isEmpty()||  
                   getComponent().getTextFieldUsuario().getText().isEmpty()||  
                   //(new String(getComponent().getPasswordFieldContrasena().getPassword())).isEmpty()||  comentado para admitir contraseña vacia
                   null == getComponent().getConnection()||
                   !this.changeLogSavedInStep1); 
    } 
     
    private final EventListenerList listeners =  new EventListenerList(); 
   
    @Override
    public void addChangeListener(ChangeListener l) { 
      listeners.add(ChangeListener.class, l); 
    } 
    @Override
    public void removeChangeListener(ChangeListener l) { 
       listeners.remove(ChangeListener.class, l); 
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
