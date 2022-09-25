package org.tesis.changelog.type;

import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.db.Constants;
import org.tesis.db.DataFilterCriteria;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.InvalidConversionException;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.exception.InvalidValueException;

public class CharType extends Type{
    public CharType(Dbms dbms){
        super(dbms.getDbmsInstance().getCharacterTypeName(), Constants.TYPE_CHAR, true);//no tiene lenght
    }
    @Override
    public boolean hasLenght(){
        return false;
    }
    @Override
    public String getDefaultValue() {
        return "";
    }
    @Override
    public boolean validateValue(String value, boolean nullable){
        if(value == null || value.isEmpty()){
            return nullable;
        }else{
            return PropertyValueValidator.validateChar(value);
        }
    }
    @Override
    public String convertTo(Type newDataType, String value) throws InvalidConversionException{//convierte de CHAR el parametro value a el nuevo dato pasado por parámetro
        if(value!=null && this.validateValue(value, true)){
            switch(newDataType.getClass().getSimpleName()){
                case "BooleanType": return (value!=null||!value.isEmpty()?"true":"false");//si value es V entonces retorna true y en caso contrario false, esto incluye si el valor no es correcto
                case "CharType": return value;
                case "DateType": throw new InvalidConversionException(Constants.TYPE_CHAR,Constants.TYPE_DATE);
                case "FloatType": if(value.isEmpty()){
                                    return "";
                                  }else{
                                    Integer x=(int)value.charAt(0); 
                                    return x.toString();
                                  }
                //case "InetType": throw new InvalidConversionException(Constants.TYPE_CHAR,Constants.TYPE_INET);
                case "IntegerType": if(value.isEmpty()){
                                    return "";
                                  }else{
                                    Integer x=(int)value.charAt(0); 
                                    return x.toString();
                                  }
                case "StringType": return value;
                default: return null;
            }
        }else{
            System.err.println("error al validar valor: "+value+" en Char, con DataType: "+newDataType.getClass().getSimpleName());
            return null;
        }
    }
    @Override
    public DataFilterCriteria[] getAvailableDataFilterCriteriaList(){
        return new DataFilterCriteria[] {DataFilterCriteria.IGUAL_A, DataFilterCriteria.DIFERENTE_A};
    }
    @Override
    public boolean passFilter(DataFilterCriteria filterCriteria, String columnValue, String value1, String value2)throws Exception{
        if(value1==null || value1.isEmpty()){
            if(filterCriteria.getNumberOfFields()==1){
                return (columnValue==null || columnValue.isEmpty());
            }else{
                throw new InvalidValueException("El/los valores del filtro no deben estar vacíos.");
            }
        }else{
            if(filterCriteria.getNumberOfFields()==2 && (value2==null || value2.isEmpty())){
                throw new InvalidValueException("El segundo valor del filtro no debe estar vacío.");
            }
        }
        if(this.validateValue(columnValue, true)){
            switch(filterCriteria.name()){
               case "IGUAL_A": return columnValue.toUpperCase().equals(value1.toUpperCase());
               case "DIFERENTE_A": return !columnValue.toUpperCase().equals(value1.toUpperCase());
               default: throw new InvalidPropertyException("Criterio de filtro incorrecto para el tipo de dato."+Constants.TYPE_CHAR);
            }
        }else{
            throw new InvalidValueException("El valor de la columna no es válido.");
        }
    }
}