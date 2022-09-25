/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.changelog.property;

import org.tesis.db.Constants;
import org.tesis.exception.InvalidValueException;

/**
 *
 * @author Claus
 */
public final class NameProperty extends Property{
    public NameProperty(String value) throws InvalidValueException {
        this.setKey();
        this.setValue(value);
    }
    
    @Override
    public boolean allowsSpaces(){
        return false;
    }
    
    @Override
    public boolean validateValue(String value) throws InvalidValueException{
        if(!PropertyValueValidator.validateName(value)){
            throw new InvalidValueException("Valor '"+value+"' inválido para la propiedad '"+Constants.PROPERTY_NAME+"'. ");
        }else{
            return true;
        }
    }
    
    @Override
    public void setKey(){
        this.key=Constants.PROPERTY_NAME;
    }
}
