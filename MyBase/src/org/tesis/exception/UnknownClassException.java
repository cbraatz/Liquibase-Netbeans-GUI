/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class UnknownClassException extends Exception {

    /**
     * Creates a new instance of
     * <code>UnknownClassException</code> without detail message.
     */
    public UnknownClassException() {
    }

    /**
     * Constructs an instance of
     * <code>UnknownClassException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public UnknownClassException(String msg) {
        super(msg);
    }
}
