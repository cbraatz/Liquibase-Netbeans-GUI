package org.tesis.changelog.type;

import java.util.Date;
import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.db.Constants;
import org.tesis.db.DataFilterCriteria;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.InvalidConversionException;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.exception.InvalidValueException;
import org.tesis.util.Utils;

public class DateType extends Type {
    public DateType(Dbms dbms){
        super(dbms.getDbmsInstance().getDateTypeName(), Constants.TYPE_DATE, true);
    }
    @Override
    public boolean hasLenght(){
        return false;
    }
    @Override
    public String getDefaultValue() {
        return "2017-01-31 12:00:00";
    }
    @Override
    public boolean validateValue(String value, boolean nullable){
        if(value == null || value.isEmpty()){
            return nullable;
        }else{
            return PropertyValueValidator.validateDate(value);
        }
    }
    @Override
    public String convertTo(Type newDataType, String value)throws InvalidConversionException{//convierte de DATE el parametro value a el nuevo dato pasado por parámetro
        if(value!=null && this.validateValue(value, true)){
            switch(newDataType.getClass().getSimpleName()){
                case "BooleanType": return (value.isEmpty()?"false":"true");
                case "CharType": return (value.isEmpty()?"":value.substring(0,1));//retorna el primer n°
                case "DateType": return value;
                case "FloatType": return value.replaceAll("-","").substring(0,8);//quita las -, y corta solo la fecha
               // case "InetType": throw new InvalidConversionException(Constants.TYPE_DATE,Constants.TYPE_INET);
                case "IntegerType": return value.replaceAll("-","").substring(0,8);//quita las -, y corta solo la fecha
                case "StringType": return value;
                default: return null;
            }
        }else{
            System.err.println("error al validar valor: "+value+" en Date, con DataType: "+newDataType.getClass().getSimpleName());
            return null;
        }
    }
    @Override
    public DataFilterCriteria[] getAvailableDataFilterCriteriaList(){
        return new DataFilterCriteria[] {DataFilterCriteria.MAYOR_QUE, DataFilterCriteria.MENOR_QUE, DataFilterCriteria.ENTRE};
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
        Date colDate=Utils.strToDate(columnValue);
        Date val1Date=Utils.strToDate(value1);
        Date val2Date=(filterCriteria.getNumberOfFields()==2?Utils.strToDate(value2):null);
        if(colDate!=null){
            if(!(filterCriteria.getNumberOfFields()==2 && val2Date==null)){
                switch(filterCriteria.name()){
                   case "MAYOR_QUE": return colDate.getTime()>val1Date.getTime();
                   case "MENOR_QUE": return colDate.getTime()<val1Date.getTime();
                   case "ENTRE": return colDate.getTime()>=val1Date.getTime() && colDate.getTime()<=val2Date.getTime();
                   default: throw new InvalidPropertyException("Criterio de filtro incorrecto para el tipo de dato."+Constants.TYPE_DATE);
                }
            }else{
                return false;
            }
        }else{
            throw new InvalidValueException("El valor de la columna no es válido.");
        }
    }
}