package org.tesis.changelog.type;

import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.db.Constants;
import org.tesis.db.DataFilterCriteria;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.InvalidConversionException;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.exception.InvalidValueException;

public class BooleanType extends Type{
    public BooleanType(Dbms dbms){
        super(dbms.getDbmsInstance().getBooleanTypeName(), Constants.TYPE_BOOLEAN, false);//no tiene lenght
    }
    @Override
    public boolean hasLenght(){
        return false;
    }
    @Override
    public String getDefaultValue() {
        return "false";
    }
    @Override
    public boolean validateValue(String value, boolean nullable){
        if(value == null || value.isEmpty()){
            return nullable;
        }else{
            return PropertyValueValidator.validateBoolean(value);
        }
    }
    @Override
    public String convertTo(Type newDataType, String value)throws InvalidConversionException{//convierte de BOOLEAN el parametro value a el nuevo dato pasado por parámetro
        if(value!=null && this.validateValue(value, true)){
            switch(newDataType.getClass().getSimpleName()){
                case "BooleanType": return value;
                case "CharType": return (value.toUpperCase().equals("TRUE")?"V":"F");//si value es TRUE entonces retorna "V" y en caso contrario "F", esto incluye si el valor no es correcto
                case "DateType": throw new InvalidConversionException(Constants.TYPE_BOOLEAN,Constants.TYPE_DATE);
                case "FloatType": return (value.toUpperCase().equals("TRUE")?"1":"0");
                //case "InetType": throw new InvalidConversionException(Constants.TYPE_BOOLEAN,Constants.TYPE_INET);
                case "IntegerType": return (value.toUpperCase().equals("TRUE")?"1":"0");
                case "StringType": return value;
                default: return null;
            }
        }else{
            System.err.println("error al validar valor: "+value+" en Boolean, con DataType: "+newDataType.getClass().getSimpleName());
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
               case "IGUAL_A": return Boolean.valueOf(columnValue).equals(Boolean.valueOf(value1));
               case "DIFERENTE_A": return !Boolean.valueOf(columnValue).equals(Boolean.valueOf(value1));
               default: throw new InvalidPropertyException("Criterio de filtro incorrecto para el tipo de dato."+Constants.TYPE_BOOLEAN);
            }
        }else{
            throw new InvalidValueException("El valor de la columna no es válido.");
        }
    }
    
}
