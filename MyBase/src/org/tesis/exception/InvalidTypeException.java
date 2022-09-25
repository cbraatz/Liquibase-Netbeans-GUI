/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class InvalidTypeException extends Exception {

    /**
     * Creates a new instance of
     * <code>InvalidTypeException</code> without detail message.
     */
    public InvalidTypeException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidTypeException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidTypeException(String msg) {
        super(msg);
    }
}
