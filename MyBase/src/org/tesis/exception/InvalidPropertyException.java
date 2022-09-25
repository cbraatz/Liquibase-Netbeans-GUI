/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class InvalidPropertyException extends Exception {

    /**
     * Creates a new instance of
     * <code>InvalidPropertyException</code> without detail message.
     */
    public InvalidPropertyException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidPropertyException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidPropertyException(String msg) {
        super(msg);
    }
}
