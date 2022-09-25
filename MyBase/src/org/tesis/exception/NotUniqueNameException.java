/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class NotUniqueNameException extends Exception {

    /**
     * Creates a new instance of
     * <code>NotUniqueNameException</code> without detail message.
     */
    public NotUniqueNameException() {
    }

    /**
     * Constructs an instance of
     * <code>NotUniqueNameException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NotUniqueNameException(String msg) {
        super(msg);
    }
}
