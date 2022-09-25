/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class InvalidParameterException extends Exception {

    /**
     * Creates a new instance of
     * <code>InvalidParameterException</code> without detail message.
     */
    public InvalidParameterException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidParameterException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidParameterException(String msg) {
        super(msg);
    }
}
