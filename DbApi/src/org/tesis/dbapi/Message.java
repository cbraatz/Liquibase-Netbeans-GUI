/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.dbapi;

import javax.swing.JOptionPane;

/**
 *
 * @author Claus
 * Clase que permite la implementación de mensajes  ya sea de INFORMACION, de ERROR, de ATENCION y de CONSULTA.
 */
public class Message {
     /**
     * Método que permite la implementación de un mensaje de ERROR, el método recibe como parámetro el   texto del mensaje que se desea que se visualice.
     * 
     * @param mensaje  Texto que se desea que se visualice en el mensaje
     */

    public static void showErrorMessage(String message){
        JOptionPane.showMessageDialog(null, message,"ERROR", JOptionPane.ERROR_MESSAGE);
    }
     /**
     * Método que permite la implementación de un mensaje de INFORMACIÓN, el método recibe como parámetro el   texto del mensaje que se desea que se visualice.
     * 
     * @param mensaje  Texto que se desea que se visualice en el mensaje
     */
    public static void showInformationMessage(String message){
        JOptionPane.showMessageDialog(null, message,"INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Método que permite la implementación de un mensaje de ATENCIÓN, el método recibe como parámetro el   texto del mensaje que se desea que se visualice.
     * 
     * @param mensaje  Texto que se desea que se visualice en el mensaje
     */
    public static void showWarningMessage(String message){
        JOptionPane.showMessageDialog(null, message,"ATENCIÓN", JOptionPane.WARNING_MESSAGE);
    }
    /**
     * Método que permite la implementación de un mensaje de CONSULTA, el método recibe como parámetro el   texto de la consulta que se desea realizar.
     * 
     * @param question  Texto que se desea que se visualice en el mensaje
     */

    public static boolean showYesNoQuestionMessage(String question){
        if (JOptionPane.showConfirmDialog(null, question, "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }
}
