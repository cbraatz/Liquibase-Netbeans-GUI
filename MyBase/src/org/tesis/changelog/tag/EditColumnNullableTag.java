package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.property.PropertiesPair;
import org.tesis.changelog.property.Property;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.type.Type;
import org.tesis.changelog.type.TypeFactory;
import org.tesis.db.Constants;
import org.tesis.db.EditColumnNullable;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.jaxb.JXEditColumnNullableTag;
import org.tesis.jaxb.JXTag;

public class EditColumnNullableTag extends Tag{
    public EditColumnNullableTag(PropertyList properties, Dbms dbms) throws Exception {
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties); 
    }
    private void setAvailableProperties(){
        availableProperties.add(new TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_DATE, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_TABLE_NAME, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_TYPE, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_NULLABLE, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_LENGHT, false));
        availableProperties.add(new TagProperty(Constants.PROPERTY_COLUMN_NAME, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_OLD_NULLABLE, true));
        //ID es agregado en la clase Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        PropertiesPair pp=new PropertiesPair(properties.getPropertyByKey(Constants.PROPERTY_OLD_NULLABLE), properties.getPropertyByKey(Constants.PROPERTY_NULLABLE));
        
        Property colNameProp=properties.getPropertyByKey(Constants.PROPERTY_COLUMN_NAME);
        Property tableName=properties.getPropertyByKey(Constants.PROPERTY_TABLE_NAME);
        if(null != tableName){     
            if(null != colNameProp){
                Property typeProp=properties.getPropertyByKey(Constants.PROPERTY_TYPE);//el tipo es requerido para el rename table en mysql asi que se le pedira a todos
                if(null != typeProp){
                    Type type=TypeFactory.getTypeInstance(typeProp.getValue(),dbms);
                    if(type.hasLenght()){
                        Property len=properties.getPropertyByKey(Constants.PROPERTY_LENGHT);
                        if(null!=len){
                            type.setLenght(len.getValue());
                        }else{
                            throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_LENGHT+" es requerida por el tipo de dato '"+type.getPublicName()+"' en el tag EditColumnNullable. ");
                        }
                    }
                    //if(null != pp.getOldProperty() && null != pp.getNewProperty()){
                    if(!pp.getOldProperty().getValue().equals(pp.getNewProperty().getValue())){
                        return new EditColumnNullable(tableName.getValue(), colNameProp.getValue(), type, Boolean.parseBoolean(pp.getOldProperty().getValue()), Boolean.parseBoolean(pp.getNewProperty().getValue()));
                    }else{
                        throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_OLD_NULLABLE+" y "+Constants.PROPERTY_NULLABLE+" son iguales y no deben serlo en el tag EditColumnNullable. ");
                    }
                    // }
                }else{
                    throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_TYPE+" es requerida y no fue encontrada en el tag EditColumnNullable. ");
                } 
            }else{
               throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_COLUMN_NAME+" es requerida y no fue encontrada en el tag EditColumnNullable. ");
            }
        }else{
           throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_TABLE_NAME+" es requerida y no fue encontrada en el tag Edit Column. ");
        }
    }
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       List<SqlQuery> sqls=new ArrayList<>();
       sqls.add(this.dbms.getDbmsInstance().getEditColumnNullableStatement((EditColumnNullable)this.exportDBObject()));
       return sqls;
    }
    
    @Override
    public JXTag exportJXTag(){
        return new JXEditColumnNullableTag(this.getId(), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE), this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME), this.getPropertyValueByKey(Constants.PROPERTY_TYPE), this.getPropertyValueByKey(Constants.PROPERTY_LENGHT), this.getPropertyValueByKey(Constants.PROPERTY_OLD_NULLABLE), this.getPropertyValueByKey(Constants.PROPERTY_NULLABLE));
    }
}
