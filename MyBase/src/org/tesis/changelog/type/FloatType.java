package org.tesis.changelog.type;

import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.db.Constants;
import org.tesis.db.DataFilterCriteria;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.InvalidConversionException;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.exception.InvalidValueException;

public class FloatType extends Type{
    public FloatType(Dbms dbms){
        super(dbms.getDbmsInstance().getFloatTypeName(), Constants.TYPE_FLOAT, false);//no tiene lenght
    }
    @Override
    public boolean hasLenght(){
        return false;
    }
    @Override
    public String getDefaultValue() {
        return "0";
    }
    @Override
    public boolean validateValue(String value, boolean nullable){
        if(value == null || value.isEmpty()){
            return nullable;
        }else{
            return PropertyValueValidator.validateDecimal(value);
        }
    }
    @Override
    public String convertTo(Type newDataType, String value)throws InvalidConversionException{//convierte de FLOAT el parametro value a el nuevo dato pasado por parámetro
        if(value!=null && this.validateValue(value, true)){
            try{
               Float x=Float.parseFloat(value);
               switch(newDataType.getClass().getSimpleName()){
                   case "BooleanType": return (value.isEmpty()?"false":"true");
                   case "CharType": return (value.isEmpty()?"":value.substring(0,1));//retorna el primer n°
                   case "DateType": Integer i=(int)x.floatValue();
                                    String str=IntegerType.convertToStrDate(i);
                                    if(str!=null){
                                        return str;
                                    }else{
                                        throw new InvalidConversionException("El valor "+x+" no puede ser convertido a fecha.");
                                    }
                   case "FloatType": return value;
                   //case "InetType": return (value.isEmpty()?"0.0.0.0":"1.1.1.1");
                   case "IntegerType": Integer x2=(int)x.floatValue();
                                       return x2.toString();
                   case "StringType": return value;
                   default: return null;
               }
            }catch(NumberFormatException e){
                System.err.println("NumberFormatException al validar valor: "+value+" en Float, con DataType: "+newDataType.getClass().getSimpleName());
                return null;
            }
        }else{
            System.err.println("error al validar valor: "+value+" en Float, con DataType: "+newDataType.getClass().getSimpleName());
            return null;
        }
    }
    @Override
    public DataFilterCriteria[] getAvailableDataFilterCriteriaList(){
        return new DataFilterCriteria[] {DataFilterCriteria.IGUAL_A, DataFilterCriteria.DIFERENTE_A, DataFilterCriteria.MAYOR_QUE, DataFilterCriteria.MENOR_QUE, DataFilterCriteria.ENTRE};
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
        try{
            Float col=Float.parseFloat(columnValue);
            Float val1=Float.parseFloat(value1);
            Float val2=(filterCriteria.getNumberOfFields()==2?Float.parseFloat(value2):null);
            if(!(filterCriteria.getNumberOfFields()==2 && val2==null)){
                switch(filterCriteria.name()){
                   case "MAYOR_QUE": return col.floatValue()>val1.floatValue();
                   case "MENOR_QUE": return col.floatValue()<val1.floatValue();
                   case "IGUAL_A": return col.floatValue()==val1.floatValue();
                   case "DIFERENTE_A": return col.floatValue()!=val1.floatValue();
                   case "ENTRE": return col.floatValue()<=val1.floatValue() && col.floatValue()>=val2.floatValue();
                   default: throw new InvalidPropertyException("Criterio de filtro incorrecto para el tipo de dato."+Constants.TYPE_FLOAT);
                }
            }else{
                return false;
            }
        }catch(NumberFormatException e){
            throw new InvalidValueException("Al menos uno de los valores de entrada no es válido.");
        }
    }
}
