package org.tesis.ui;

import javax.swing.JPanel;

/**
 * Clase abstracta de la que extienden los paneles que van insertos en el Editor.
 */
public abstract class MyEditor extends JPanel{
    KeyNamePanel keyNamePanel;
    private boolean editMode=false;
    public Object exportEditorResults()throws Exception{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void reset() throws Exception{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void setValues(Object obj){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public KeyNamePanel getKeyNamePanel() {
        return keyNamePanel;
    }
    public void setKeyNameVisibility(boolean visible){
        this.keyNamePanel.setVisible(visible);
        this.validate();
    }
    public void setKeyNameLabel(String keyLabel){
        keyNamePanel.setKeyNameLabel(keyLabel);
        this.validate();
    }
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    public boolean isEditMode() {
        return editMode;
    }
    
}
