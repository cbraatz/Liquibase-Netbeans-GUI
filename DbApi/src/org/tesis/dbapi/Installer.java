package org.tesis.dbapi;
import org.openide.modules.ModuleInstall;


public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        //al iniciarse
    }
    
    @Override
    public void close() {
        //antes de cerrarse
    }

    @Override
    public boolean closing() {
        if(Message.showYesNoQuestionMessage("¿Realmente desea salir?")){
            org.tesis.ui.WindowManager.closeAllComponents();
            return true;
        }else{
            return false;
        }
    }
}

