package org.tesis.changelog.property;

import org.tesis.exception.InvalidValueException;

/**
 * Clase abstracta de property
 * @author Claus
 */
public abstract class Property {
    public String value;
    public String key;
    /**
     * Especifica si esa instancia de Property admite espacio o no.
     * @return si esa instancia de Property admite espacio o no.
     */
    public boolean allowsSpaces(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * Verifica si el valor para value es el correcto para la instancia de Property
     * @param value valor de property
     * @return si el valor para value es el correcto para la instancia de Property
     * @throws InvalidValueException 
     */
    public boolean validateValue(String value) throws InvalidValueException{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * Setea el valor de value
     * @param value el valor de la propiedad
     * @return si el valor es válido.
     * @throws InvalidValueException 
     */
    public void setValue(String value) throws InvalidValueException{
        if(validateValue(value)){
            this.value = value;
            //System.out.println("Value '"+value+"' válido");
        }
    }
    /**
     * Setea el valor de key
     */
    public void setKey(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * Retorna el valor de value
     * @return el valor de value
     */
    public String getValue() {
        return value;
    }
    /**
     * Retorna el valor de key
     * @return el valor de key
     */
    public String getKey() {
        return key;
    }
    @Override 
    public String toString(){
        return "'"+this.key+"' = '"+this.value+"'";
    }
}
