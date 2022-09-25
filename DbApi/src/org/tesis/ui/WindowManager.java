package org.tesis.ui;

import java.util.Set;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
/**
 * Clase que permite el manejo de las ventanas dentro de la aplicación,  dentro de esta clase se pueden ir creando diferentes métodos que ayuden a realizar operaciones sobre las ventanas 
 * <br> dentro de la aplicación.
 * @author Denis Maernitz
 */

public class WindowManager {
    /**
     * Metodo utilizado para cerrar todos los TopComponents
     */
    public static void closeAllComponents(){
        Set<? extends Mode> modes = org.openide.windows.WindowManager.getDefault().getModes();//se deben cerrar aca los topcomponents porque si se hace en el close() no se guardan los cambios y vuelve a iniciar con los TopComponents abiertos
        for(Mode m:modes){
            TopComponent[] tcs=m.getTopComponents();
            for(TopComponent tc:tcs){
                if(tc.isOpened()){
                    //System.out.println("Cerrando "+tc.getName());
                   // System.out.println(tc.close());     
                }
            }
        }
    }
    
    /**
     * Cierra todos los TopComponents excepto el pasado por parámetro, si este es null cierra todos.
     * @param exceptThis topComponent que no se cerrará.
     */
    public static void closeEditorComponentsWithException(TopComponent exceptThis){
        Set<? extends Mode> modes = org.openide.windows.WindowManager.getDefault().getModes();//se deben cerrar aca los topcomponents porque si se hace en el close() no se guardan los cambios y vuelve a iniciar con los TopComponents abiertos
        for(Mode m:modes){
            TopComponent[] tcs=m.getTopComponents();
            if(m.getName().equals("editor")){
                for(TopComponent tc:tcs){
                    if(tc.isOpened()){
                        if(null != exceptThis){
                            if(!tc.getName().equals(exceptThis.getName())){
                                //System.out.println("Cerrando "+tc.getName());
                                //System.out.println(tc.close());
                            }else{
                                //System.out.println("No se cierra "+tc.getName());
                            }
                        }
                    }
                }
                break;
            }
        }
    }
}
