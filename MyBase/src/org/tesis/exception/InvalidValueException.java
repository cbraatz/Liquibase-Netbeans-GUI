/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class InvalidValueException extends Exception {

    /**
     * Creates a new instance of
     * <code>InvalidValueException</code> without detail message.
     */
    public InvalidValueException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidValueException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidValueException(String msg) {
        super(msg);
    }
}
