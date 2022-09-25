package org.tesis.changelog.type;

import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.db.Constants;
import org.tesis.db.DataFilterCriteria;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.InvalidConversionException;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.exception.InvalidValueException;

public class IntegerType extends Type{
    public IntegerType(Dbms dbms){
        super(dbms.getDbmsInstance().getIntegerTypeName(), Constants.TYPE_INT, false);//no tiene lenght
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
            return PropertyValueValidator.validateInteger(value);
        }
    }
    @Override
    public String convertTo(Type newDataType, String value)throws InvalidConversionException{//convierte de INTEGER el parametro value a el nuevo dato pasado por parámetro
        if(value!=null && this.validateValue(value, true)){
            try{
               Integer x=Integer.parseInt(value);
               switch(newDataType.getClass().getSimpleName()){
                   case "BooleanType": return (value.isEmpty()?"false":"true");
                   case "CharType": return (value.isEmpty()?"":value.substring(0,1));//retorna el primer n°
                   case "DateType": String str=IntegerType.convertToStrDate(x);
                                    if(str!=null){
                                        return str;
                                    }else{
                                        throw new InvalidConversionException("El valor "+x+" no puede ser convertido a "+Constants.TYPE_DATE);
                                    }
                   case "FloatType": return value;
                   //case "InetType": throw new InvalidConversionException(Constants.TYPE_INT,Constants.TYPE_INET);
                   case "IntegerType": return value;
                   case "StringType": return value;
                   default: return null;
               }
            }catch(NumberFormatException e){
                System.err.println("NumberFormatException al validar valor: "+value+" en Integer, con DataType: "+newDataType.getClass().getSimpleName());
                return null;
            }
        }else{
            System.err.println("error al validar valor: "+value+" en Integer, con DataType: "+newDataType.getClass().getSimpleName());
            return null;
        }
    }
    public static String convertToStrDate(Integer date){
        String is=date.toString();
        //System.out.println("Input integer date="+is);
        String da;
        if(is.length()==8){
            da=is.substring(0, 4)+"-"+is.substring(4, 6)+"-"+is.substring(6);//agrega las - a da
            if(PropertyValueValidator.validateStrDate(da)){
                return da;
            }else{
                System.err.println("Valor date invalido="+da);
                return null;
            }
        }
        return null;
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
            Integer col=Integer.parseInt(columnValue);
            Integer val1=Integer.parseInt(value1);
            Integer val2=(filterCriteria.getNumberOfFields()==2?Integer.parseInt(value2):null);
            if(!(filterCriteria.getNumberOfFields()==2 && val2==null)){
                switch(filterCriteria.name()){
                   case "MAYOR_QUE": return col.intValue()>val1.intValue();
                   case "MENOR_QUE": return col.intValue()<val1.intValue();
                   case "IGUAL_A": return col.intValue()==val1.intValue();
                   case "DIFERENTE_A": return col.intValue()!=val1.intValue();
                   case "ENTRE": return col.intValue()<=val1.intValue() && col.intValue()>=val2.intValue();
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
