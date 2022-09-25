package org.tesis.changelog.property;

import java.util.List;
import org.tesis.db.Constants;
import org.tesis.exception.InvalidValueException;
import org.tesis.util.Utils;

public final class OldPKListProperty extends Property{
    public OldPKListProperty(String value) throws InvalidValueException {
        this.setKey();
        this.setValue(value);
    }
    
    @Override
    public boolean allowsSpaces(){
        return false;
    }
    
    @Override
    public boolean validateValue(String value) throws InvalidValueException{
        if(!PropertyValueValidator.validatePKList(value)){
            throw new InvalidValueException("Valor '"+value+"' inválido para la propiedad '"+Constants.PROPERTY_OLD_PKS+"'. ");
        }else{
            return true;
        }
    }
    
    @Override
    public void setKey(){
        this.key=Constants.PROPERTY_OLD_PKS;
    }
    
    public List<String> getColumnNames() throws Exception{
        return Utils.getColumnNames(this.value);
    }
}
