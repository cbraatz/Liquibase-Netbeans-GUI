package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.Constants;
import org.tesis.db.RemoveColumn;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.jaxb.JXRemoveColumnTag;
import org.tesis.jaxb.JXTag;

public class RemoveColumnTag extends Tag{
    public RemoveColumnTag(PropertyList properties, Dbms dbms) throws Exception{
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
    }

    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_TABLE_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_DATE, true));
        //ID es agregado en la clase padre Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag RemoveColumn no fue cargada apropiadamente.");
        }else{
            return new RemoveColumn(properties.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_NAME));
        }
    }
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       RemoveColumn rem=(RemoveColumn)this.exportDBObject();
       List<SqlQuery> result= new ArrayList<>();
       result.add(this.getDbms().getDbmsInstance().getRemoveColumnStatement(rem));
       return result;
    }
    @Override
    public JXTag exportJXTag() {
        return new JXRemoveColumnTag(this.getPropertyValueByKey(Constants.PROPERTY_ID), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE), this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.getPropertyValueByKey(Constants.PROPERTY_NAME));
    }
}