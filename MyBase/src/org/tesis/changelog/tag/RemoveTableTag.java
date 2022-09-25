package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.Constants;
import org.tesis.db.RemoveTable;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.jaxb.JXRemoveTableTag;
import org.tesis.jaxb.JXTag;

public class RemoveTableTag extends Tag{
    public RemoveTableTag(PropertyList properties, Dbms dbms) throws Exception{
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
    }

    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_DATE, true));
        //ID es agregado en la clase padre Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag RemoveTable no fue cargada apropiadamente.");
        }else{
            return new RemoveTable(properties.getPropertyValueByKey(Constants.PROPERTY_NAME));
        }
    }
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       RemoveTable rem=(RemoveTable)this.exportDBObject();
       List<SqlQuery> result= new ArrayList<>();
       result.add(this.getDbms().getDbmsInstance().getRemoveTableStatement(rem));
       return result;
    }
    @Override
    public JXTag exportJXTag() {
        return new JXRemoveTableTag(this.getPropertyValueByKey(Constants.PROPERTY_ID), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE),this.getPropertyValueByKey(Constants.PROPERTY_NAME));
    }
}