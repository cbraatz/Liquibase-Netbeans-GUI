/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.exception;

/**
 *
 * @author Claus
 */
public class RequiredPropertyNotPresentException extends Exception {

    /**
     * Creates a new instance of
     * <code>RequiredPropertyNotPresentException</code> without detail message.
     */
    public RequiredPropertyNotPresentException() {
    }

    /**
     * Constructs an instance of
     * <code>RequiredPropertyNotPresentException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public RequiredPropertyNotPresentException(String msg) {
        super(msg);
    }
}
