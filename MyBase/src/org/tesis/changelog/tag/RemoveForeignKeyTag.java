package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.Constants;
import org.tesis.db.RemoveForeignKey;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.jaxb.JXRemoveForeignKeyTag;
import org.tesis.jaxb.JXTag;

public class RemoveForeignKeyTag extends Tag{
    public RemoveForeignKeyTag(PropertyList properties, Dbms dbms) throws Exception{
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
    }

    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_COLUMN_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_TABLE_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_FK_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_DATE, true));
        //ID es agregado en la clase padre Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag RemoveForeignKey no fue cargada apropiadamente.");
        }else{
            return new RemoveForeignKey(properties.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_FK_NAME));
        }
    }
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       RemoveForeignKey rem=(RemoveForeignKey)this.exportDBObject();
       List<SqlQuery> result= new ArrayList<>();
       result.add(this.getDbms().getDbmsInstance().getRemoveForeignKeyStatement(rem));
       return result;
    }
    @Override
    public JXTag exportJXTag() {
        return new JXRemoveForeignKeyTag(this.getPropertyValueByKey(Constants.PROPERTY_ID), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE),this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME),this.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_FK_NAME));
    }
}