/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class ColumnCreationException extends Exception {

    /**
     * Creates a new instance of
     * <code>ColumnCreationException</code> without detail message.
     */
    public ColumnCreationException() {
    }

    /**
     * Constructs an instance of
     * <code>ColumnCreationException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ColumnCreationException(String msg) {
        super(msg);
    }
}
