/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class InvalidForeignKeyException extends Exception {

    /**
     * Creates a new instance of
     * <code>InvalidForeignKeyException</code> without detail message.
     */
    public InvalidForeignKeyException() {
    }

    /**
     * Constructs an instance of
     * <code>InvalidForeignKeyException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public InvalidForeignKeyException(String msg) {
        super(msg);
    }
}
