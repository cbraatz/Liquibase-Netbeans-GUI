/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.wizard.open;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import org.openide.WizardDescriptor;
import org.openide.util.HelpCtx;
import org.tesis.db.dbms.Dbms;
import org.tesis.dbapi.Author;

public class WizardOpenWizardPanel1 implements WizardDescriptor.Panel<WizardDescriptor>, PropertyChangeListener {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private final EventListenerList listeners =  new EventListenerList(); 
    private WizardOpenVisualPanel1 component;
    private boolean isValid=false;
    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public WizardOpenVisualPanel1 getComponent() {
        if (component == null) {
            component = new WizardOpenVisualPanel1();
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }
    
    public Author getAuthor(){
       return this.getComponent().getAuthor();
    }
    
    public Dbms getDbms(){
       return this.getComponent().getDbms();
    }
    
    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        return isValid;
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

   @Override
    public void propertyChange(PropertyChangeEvent evt) {
        boolean oldState = isValid; 
        isValid = checkValidity(); 
        fireChangeEvent(this, oldState, isValid); 
    }
    
     private boolean checkValidity() { 
         return ! (getComponent().getTextFieldPath().getText().isEmpty()); 
    } 
    @Override
    public void readSettings(WizardDescriptor wiz) {
        getComponent().addPropertyChangeListener(this); 
    }

    @Override
    public void storeSettings(WizardDescriptor wiz) {

    }
   
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
