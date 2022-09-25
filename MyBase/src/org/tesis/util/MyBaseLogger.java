/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Claus
 */
public class MyBaseLogger {
    public static void LogInformationMessage(String message, String className){
        Logger.getLogger(className).log(Level.INFO, message);
    }
    public static void LogWarningMessage(String message, String className){
        Logger.getLogger(className).log(Level.WARNING, message);
    }
}
