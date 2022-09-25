/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class PropertiesNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>PropertiesNotFoundException</code> without detail message.
     */
    public PropertiesNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>PropertiesNotFoundException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public PropertiesNotFoundException(String msg) {
        super(msg);
    }
}
