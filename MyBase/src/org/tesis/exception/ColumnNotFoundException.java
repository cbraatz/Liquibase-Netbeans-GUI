/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class ColumnNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>ColumnNotFoundException</code> without detail message.
     */
    public ColumnNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>ColumnNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ColumnNotFoundException(String msg) {
        super(msg);
    }
}
