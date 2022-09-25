package org.tesis.changelog.type;

import org.tesis.db.Constants;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.InvalidTypeException;
import org.tesis.util.MyBaseLogger;

/**
 *
 * @author Claus
 */
public class TypeFactory {
    public static Type getTypeInstance(String key, Dbms dbms) throws InvalidTypeException{
        if(null!=key){
            switch(key){
                case Constants.TYPE_INT: return new IntegerType(dbms);
                case Constants.TYPE_FLOAT: return new FloatType(dbms);
                case Constants.TYPE_BOOLEAN: return new BooleanType(dbms);
                case Constants.TYPE_CHAR: return new CharType(dbms);  
                case Constants.TYPE_STRING: return new StringType(dbms);
                //case Constants.TYPE_INET: return new InetType(dbms);
                case Constants.TYPE_DATE: return new DateType(dbms);
                default: InvalidTypeException ex=new InvalidTypeException("Error al instanciar la tipo '"+key+"'; tipo de dato no reconocido.");
                         MyBaseLogger.LogWarningMessage(ex.getMessage(), "TypeFactory");
                         throw ex; 
            }
        }else{
            return null;
        }
    }
}
