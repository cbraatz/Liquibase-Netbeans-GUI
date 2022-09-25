package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.db.Constants;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.EditForeignKey;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.jaxb.JXEditForeignKeyTag;
import org.tesis.jaxb.JXTag;

public class EditForeignKeyTag extends Tag{
    public EditForeignKeyTag(PropertyList properties, Dbms dbms) throws Exception{
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
    }

    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_TABLE_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_COLUMN_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_FOREIGN_TABLE_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_FOREIGN_COLUMN_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_FK_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_OLD_COLUMN_NAME, false));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_OLD_FOREIGN_TABLE_NAME, false));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_OLD_FOREIGN_COLUMN_NAME, false));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_NEW_FK_NAME, false));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_DATE, true));
        //ID es agregado en la clase Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag EditForeingKey no fue cargada apropiadamente.");
        }else{
            return new EditForeignKey(properties.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_FOREIGN_TABLE_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_FOREIGN_COLUMN_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_FK_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_NEW_FK_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_OLD_COLUMN_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_OLD_FOREIGN_TABLE_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_OLD_FOREIGN_COLUMN_NAME));
        }
    }
    
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       EditForeignKey editFK=(EditForeignKey)this.exportDBObject();
       List<SqlQuery> result= new ArrayList<>();
       result.add(this.getDbms().getDbmsInstance().getEditForeignKeyStatement(editFK));
       return result;
    }
    
    @Override
    public JXTag exportJXTag() {
        return new JXEditForeignKeyTag(this.getPropertyValueByKey(Constants.PROPERTY_ID), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE),this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME),this.getPropertyValueByKey(Constants.PROPERTY_FOREIGN_TABLE_NAME), this.getPropertyValueByKey(Constants.PROPERTY_FOREIGN_COLUMN_NAME), this.getPropertyValueByKey(Constants.PROPERTY_FK_NAME), this.getPropertyValueByKey(Constants.PROPERTY_NEW_FK_NAME), this.getPropertyValueByKey(Constants.PROPERTY_OLD_COLUMN_NAME), this.getPropertyValueByKey(Constants.PROPERTY_OLD_FOREIGN_TABLE_NAME), this.getPropertyValueByKey(Constants.PROPERTY_OLD_FOREIGN_COLUMN_NAME));
    }
}