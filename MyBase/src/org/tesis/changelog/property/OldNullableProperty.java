package org.tesis.changelog.property;

import org.tesis.db.Constants;
import org.tesis.exception.InvalidValueException;

public final class OldNullableProperty extends Property {
   
    public OldNullableProperty(String value) throws InvalidValueException{
        this.setKey();
        this.setValue(value);
    }
    
    @Override
    public boolean allowsSpaces(){
        return false;
    }
    
    @Override
    public boolean validateValue(String value) throws InvalidValueException{
        if(!PropertyValueValidator.validateBoolean(value)){
            throw new InvalidValueException("Valor '"+value+"' inválido para la propiedad '"+Constants.PROPERTY_OLD_NULLABLE+"'. ");
        }else{
            return true;
        }
    }
    
    @Override
    public void setKey(){
        this.key=Constants.PROPERTY_OLD_NULLABLE;
    }
}