/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class TableNotFoundException extends Exception {

    /**
     * Creates a new instance of
     * <code>TableNotFoundException</code> without detail message.
     */
    public TableNotFoundException() {
    }

    /**
     * Constructs an instance of
     * <code>TableNotFoundException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public TableNotFoundException(String msg) {
        super(msg);
    }
}
