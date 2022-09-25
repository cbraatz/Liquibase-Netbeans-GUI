package org.tesis.changelog.property;

import org.tesis.db.Constants;
import org.tesis.exception.InvalidValueException;

public final class InternalIdProperty extends Property{
    public InternalIdProperty(String value) throws InvalidValueException {
        this.setKey();
        this.setValue(value);
    }
    
    @Override
    public boolean allowsSpaces(){
        return false;
    }
    
    @Override
    public boolean validateValue(String value) throws InvalidValueException{
        if(!PropertyValueValidator.validateNotEmptyString(value)){
            throw new InvalidValueException("Valor '"+value+"' inválido para la propiedad '"+Constants.PROPERTY_INTERNAL_ID+"'. ");
        }else{
            return true;
        }
    }
    
    @Override
    public void setKey(){
        this.key=Constants.PROPERTY_INTERNAL_ID;
    }
}
