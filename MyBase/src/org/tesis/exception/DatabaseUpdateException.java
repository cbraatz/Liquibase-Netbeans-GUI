/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class DatabaseUpdateException extends Exception {

    /**
     * Creates a new instance of
     * <code>DatabaseUpdateException</code> without detail message.
     */
    public DatabaseUpdateException() {
    }

    /**
     * Constructs an instance of
     * <code>DatabaseUpdateException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DatabaseUpdateException(String msg) {
        super(msg);
    }
}
