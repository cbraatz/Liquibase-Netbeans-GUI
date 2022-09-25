package org.tesis.changelog.property;
import org.tesis.db.Constants;
import org.tesis.util.Utils;

public class PropertyValueValidator {
    public static boolean validateBoolean(String value){
         return (value.toUpperCase().equals("TRUE") | value.toUpperCase().equals("YES") | value.toUpperCase().equals("FALSE") | value.toUpperCase().equals("NO"));
    }
    public static boolean validateInteger(String value){
        if(value.isEmpty()){
            return false;
        }
        if(!value.matches("[0-9]+")){
            return false;
        }
        try{
            Integer.parseInt(value);
            return true;
        }catch(NumberFormatException e){
            //retorna false si el value no tiene el formato de entero
            return false;
        } 
    }
    /* public static boolean validateSimpleString(String value){//strings sin espacio
        return !(value.contains(" ") | value.contains("\t") | value.contains("\r") | value.contains("\f") | value.contains("\n"));
    }*/
    
    public static boolean validateName(String value){//nombres de tablas y atributos que no deben ser reservados
        String reservedNames[]={"INT","FLOAT","STRING","BOOLEAN","INTEGER","VARCHAR","CHAR","DOUBLE","FALSE","TRUE","TABLE","DB","DATABASE","COLUMN","TEST_SYSTEM_H554_6XC254_652_TRC_458_XC","INET","DATE","AS","TO","SELECT","INDEX","REMOVE","SERIAL"};
        boolean valid=true;
        for(String s:reservedNames){
            if(s.equals(value.toUpperCase())){
                valid=false;
                break;
            }
        }
        return !(value.contains(" ") || value.contains("\t") || value.contains("\r") || value.contains("\f") || value.contains("\n") || value.isEmpty() || !valid || !value.matches("^[a-z]([a-z]|[0-9]|_)+$"));
    }
    public static boolean validateNotEmptyString(String value){
        if(value.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    public static boolean validateTypeProperty(String value){
         return (value.toUpperCase().equals(Constants.TYPE_BOOLEAN.toUpperCase()) || value.toUpperCase().equals(Constants.TYPE_CHAR.toUpperCase()) || value.toUpperCase().equals(Constants.TYPE_FLOAT.toUpperCase()) || value.toUpperCase().equals(Constants.TYPE_INT.toUpperCase()) || value.toUpperCase().equals(Constants.TYPE_STRING.toUpperCase())/*|| value.toUpperCase().equals(Constants.TYPE_INET.toUpperCase())*/|| value.toUpperCase().equals(Constants.TYPE_DATE.toUpperCase()));
    }
    public static boolean validateStrDate(String value){
        if(value.isEmpty()){
            return false;
        }else{
            return Utils.validateDate(value);
        }
    }
    public static boolean validateStrLogDate(String value){
        if(value.isEmpty()){
            return false;
        }else{
            return Utils.validateLogDate(value);
        }
    }
    public static boolean validatePKList(String value){
        try{
            if(value.isEmpty()){
                return false;
            }
            Utils.getColumnNames(value);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    public static boolean validateChar(String value){
        return value.length()==1;
    }
    public static boolean validateDecimal(String value){
        return value.matches("-?\\d+(\\.\\d+)?");
    }
    public static boolean validateInet(String value){
        return value.matches("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
    }
    public static boolean validateDate(String value){
    	return Utils.validateDate(value);
    }
}
