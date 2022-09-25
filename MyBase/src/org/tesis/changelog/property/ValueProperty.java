package org.tesis.changelog.property;

import org.tesis.db.Constants;
import org.tesis.exception.InvalidValueException;

public final class ValueProperty extends Property {
   
    public ValueProperty(String value) throws InvalidValueException{
        this.setKey();
        this.setValue(value);
    }
    
    @Override
    public boolean allowsSpaces(){
        return false;
    }
    
    @Override
    public boolean validateValue(String value) throws InvalidValueException{
        /*if(!PropertyValueValidator.validateBoolean(value)){
            throw new InvalidValueException("Valor '"+value+"' inválido para la propiedad '"+Constants.PROPERTY_VALUE+"'. ");
        }else{
            return true;
        }*/
        return true;//all values are valid
    }
    
    @Override
    public void setKey(){
        this.key=Constants.PROPERTY_VALUE;
    }
}