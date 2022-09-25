package org.tesis.exception;

public class InvalidConversionException extends Exception {

    /**
     * Creates a new instance of
     * <code>InvalidValueException</code> without detail message.
     */
    public InvalidConversionException() {
    }
    public InvalidConversionException(String inputType, String outputType) {
        super("No está permitido convertir un tipo "+inputType+" a "+outputType+".");
    }
    /**
     * Constructs an instance of
     * <code>InvalidValueException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidConversionException(String msg) {
        super(msg);
    }
}
