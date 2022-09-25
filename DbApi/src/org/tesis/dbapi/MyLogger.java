package org.tesis.dbapi;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que permite la implementación  de mensajes  en el LOG ya sea  de ERROR, INFORMACION o ATENCION.
 * @author Claus
 */
public class MyLogger {
     /**
     * Método que permite implementar un mensaje de ERROR en el LOG, el método recibe como parámetro la excepción que se produjo
     * @param ex Excepción que se produjo
     */
    public static void LogErrorMessage(/*String message, String className, */Exception ex){
        //Logger.getLogger(className).log(Level.SEVERE, message, ex);
        ex.printStackTrace();
    }
   /**
     * Método que permite implementar un mensaje de INFORMACION en el LOG, el método recibe como parámetro  el mensaje que se desea visualizar y el nombre de la clase,
     * <br> El nombre de la clase es utilizada para  especificar de qué clase fue llamado el mensaje para darle más claridad al mismo.
     * @param message Mensaje a ser visualizado el  LOG.
     * @param className  Nombre de la clase
     */

    public static void LogInformationMessage(String message, String className){
        Logger.getLogger(className).log(Level.INFO, message);
    }
    /**
     * Método que permite implementar un mensaje de ADVERTENCIA  en el LOG, el método recibe como parámetro  el mensaje que se desea visualizar y el nombre de la clase,
     * <br> El nombre de la clase es utilizada para  especificar de qué clase fue llamado el mensaje para darle más claridad al mismo.
     * @param message Mensaje a ser visualizado el  LOG.
     * @param className  Nombre de la clase
     */

    public static void LogWarningMessage(String message, String className){
        Logger.getLogger(className).log(Level.WARNING, message);
    }
}
