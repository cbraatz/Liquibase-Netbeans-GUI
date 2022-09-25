/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class NotUniqueIDException extends Exception {

    /**
     * Creates a new instance of
     * <code>NotUniqueIDException</code> without detail message.
     */
    public NotUniqueIDException() {
    }

    /**
     * Constructs an instance of
     * <code>NotUniqueIDException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public NotUniqueIDException(String msg) {
        super(msg);
    }
}
