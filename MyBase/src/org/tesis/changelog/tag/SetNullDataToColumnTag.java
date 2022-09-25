package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.property.Property;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.type.Type;
import org.tesis.changelog.type.TypeFactory;
import org.tesis.db.Constants;
import org.tesis.db.SetNullDataToColumn;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.jaxb.JXSetNullDataToColumnTag;
import org.tesis.jaxb.JXTag;

public class SetNullDataToColumnTag extends Tag{
    public SetNullDataToColumnTag(PropertyList properties, Dbms dbms) throws Exception {
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties); 
    }
    private void setAvailableProperties(){
        availableProperties.add(new TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_DATE, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_TABLE_NAME, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_TYPE, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_LENGHT, false));
        availableProperties.add(new TagProperty(Constants.PROPERTY_COLUMN_NAME, true));
        //ID es agregado en la clase Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
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
                            throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_LENGHT+" es requerida por el tipo de dato '"+type.getPublicName()+"' en el tag SetNullDataToColumnTag. ");
                        }
                    }
                    return new SetNullDataToColumn(tableName.getValue(), colNameProp.getValue(), type);
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
       sqls.add(this.dbms.getDbmsInstance().getSetNullDataToColumnStatement((SetNullDataToColumn)this.exportDBObject()));
       return sqls;
    }
    
    @Override
    public JXTag exportJXTag(){
        return new JXSetNullDataToColumnTag(this.getId(), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE), this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME), this.getPropertyValueByKey(Constants.PROPERTY_TYPE), this.getPropertyValueByKey(Constants.PROPERTY_LENGHT));
    }
}
