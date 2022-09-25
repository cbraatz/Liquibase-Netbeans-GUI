package org.tesis.changelog.type;

import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.db.Constants;
import org.tesis.db.DataFilterCriteria;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.InvalidConversionException;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.exception.InvalidValueException;

public class StringType extends Type{
    public StringType(Dbms dbms){
        super(dbms.getDbmsInstance().getStringTypeName(), Constants.TYPE_STRING, true);
    }
    @Override
    public boolean hasLenght(){
        return true;
    }
    @Override
    public String toString(){
        return this.getDbName()+"("+this.getLenght()+")";
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
            return (value.length() <= this.getLenght());
        }
    }
    @Override
    public String convertTo(Type newDataType, String value)throws InvalidConversionException{//convierte de STRING el parametro value a el nuevo dato pasado por parámetro
        if(value!=null && this.validateValue(value, true)){
            switch(newDataType.getClass().getSimpleName()){
                case "BooleanType": return (value.isEmpty()?"false":"true");
                case "CharType": return (value.isEmpty()?"":value.substring(0,1));//retorna el primer char de value
                case "DateType": if(PropertyValueValidator.validateDate(value)){
                                    return value;
                                 }else{
                                    throw new InvalidConversionException("El valor "+value+" no puede ser convertido a "+Constants.TYPE_DATE);
                                 }
                case "FloatType": if(PropertyValueValidator.validateDecimal(value)){//no verifica la longitud del float, solo si es decimal
                                        return value;
                                  }else{
                                    throw new InvalidConversionException("El valor "+value+" no puede ser convertido a "+Constants.TYPE_FLOAT);
                                  }
                /*case "InetType": if(PropertyValueValidator.validateInet(value)){
                                        return value;
                                 }else{
                                    throw new InvalidConversionException("El valor "+value+" no puede ser convertido a "+Constants.TYPE_INET);
                                 }*/
                case "IntegerType": if(PropertyValueValidator.validateInteger(value)){
                                        return value;
                                    }else{
                                        throw new InvalidConversionException("El valor "+value+" no puede ser convertido a "+Constants.TYPE_INT);
                                    }
                case "StringType": return value;
                default: return null;
            }
        }else{
            System.err.println("Error al validar valor: "+value+" en String, con DataType: "+newDataType.getClass().getSimpleName());
            return null;
        }
    }
    @Override
    public DataFilterCriteria[] getAvailableDataFilterCriteriaList(){
        return new DataFilterCriteria[] {DataFilterCriteria.IGUAL_A, DataFilterCriteria.DIFERENTE_A, DataFilterCriteria.CONTIENE};
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
               case "CONTIENE": return columnValue.toUpperCase().indexOf(value1.toUpperCase())>=0;
               default: throw new InvalidPropertyException("Criterio de filtro incorrecto para el tipo de dato."+Constants.TYPE_STRING);
            }
        }else{
            throw new InvalidValueException("El valor de la columna no es válido.");
        }
    }
}