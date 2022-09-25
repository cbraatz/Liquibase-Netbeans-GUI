package org.tesis.changelog.property;

import org.tesis.db.Constants;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.exception.InvalidValueException;
import org.tesis.util.MyBaseLogger;

/**
 * Es una clase factory de Propiedades.
 * @author Claus
 */
public class PropertyFactory {
    /**
     * Devuelve la instancia del objeto Property que se pasa por el parámetro key y setea su value.
     * @param key El nombre de la propiedad.
     * @param value El valor de la propiedad.
     * @return La instancia del objeto Property
     * @throws InvalidPropertyException Cuando el key pasado por parámetro no es válido.
     * @throws InvalidValueException Cuando el value pasado por parámetro es inválido.
     */
    public static Property getPropertyInstance(String key, String value) throws InvalidPropertyException, InvalidValueException{
        switch(key){
            case Constants.PROPERTY_NAME: return new NameProperty(value);
            case Constants.PROPERTY_ID: return new IdProperty(value);
            case Constants.PROPERTY_INTERNAL_ID: return new InternalIdProperty(value);
            case Constants.PROPERTY_AUTHOR: return new AuthorProperty(value);
            case Constants.PROPERTY_TYPE: return new TypeProperty(value);
            case Constants.PROPERTY_LENGHT: return new LenghtProperty(value);
            case Constants.PROPERTY_NULLABLE: return new NullableProperty(value);
            case Constants.PROPERTY_PRIMARY_KEY: return new PrimaryKeyProperty(value);
            case Constants.PROPERTY_OLD_TYPE: return new OldTypeProperty(value);
            case Constants.PROPERTY_OLD_LENGHT: return new OldLenghtProperty(value);
            case Constants.PROPERTY_OLD_NULLABLE: return new OldNullableProperty(value);
            case Constants.PROPERTY_DATE: return new DateProperty(value);
            case Constants.PROPERTY_OLD_NAME: return new OldNameProperty(value);    
            case Constants.PROPERTY_TABLE_NAME: return new TableNameProperty(value);  
            case Constants.PROPERTY_NEW_NAME: return new NewNameProperty(value);    
            case Constants.PROPERTY_COLUMN_NAME: return new ColumnNameProperty(value); 
            case Constants.PROPERTY_PKS: return new PKListProperty(value);
            case Constants.PROPERTY_OLD_PKS: return new OldPKListProperty(value);
            case Constants.PROPERTY_FOREIGN_COLUMN_NAME: return new ForeignColumnNameProperty(value); 
            case Constants.PROPERTY_FOREIGN_TABLE_NAME: return new ForeignTableNameProperty(value); 
            case Constants.PROPERTY_FK_NAME: return new ForeignKeyNameProperty(value); 
            case Constants.PROPERTY_PK_NAME: return new PrimaryKeyNameProperty(value); 
            case Constants.PROPERTY_NEW_FK_NAME: return new NewForeignKeyNameProperty(value);
            case Constants.PROPERTY_OLD_FOREIGN_COLUMN_NAME: return new OldForeignColumnNameProperty(value);
            case Constants.PROPERTY_OLD_FOREIGN_TABLE_NAME: return new OldForeignTableNameProperty(value);
            case Constants.PROPERTY_OLD_COLUMN_NAME: return new OldColumnNameProperty(value);
            case Constants.PROPERTY_OLD_INTERNAL_ID: return new OldInternalIdProperty(value);
            case Constants.PROPERTY_VALUE: return new ValueProperty(value);
            case Constants.PROPERTY_QUOTED: return new QuotedProperty(value);
            default: InvalidPropertyException ex= new InvalidPropertyException("Error al instanciar la propiedad '"+key+"'; propiedad no reconocida.");
                     MyBaseLogger.LogWarningMessage(ex.getMessage(), "PropertyFactory");
                     throw ex; 
        }
    }
}