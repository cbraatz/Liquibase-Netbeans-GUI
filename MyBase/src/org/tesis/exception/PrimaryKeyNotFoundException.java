/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class PrimaryKeyNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>PrimaryKeyNotFoundException</code> without detail message.
     */
    public PrimaryKeyNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>PrimaryKeyNotFoundException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public PrimaryKeyNotFoundException(String msg) {
        super(msg);
    }
}
