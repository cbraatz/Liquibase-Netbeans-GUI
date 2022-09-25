package org.tesis.changelog.tag;

import org.tesis.db.Column;
import org.tesis.db.Constants;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.dbms.Dbms;
import org.tesis.jaxb.JXColumnTag;
import org.tesis.jaxb.JXTag;

/**
 *
 * @author Claus
 */
public class ColumnTag extends Tag{
    public ColumnTag(PropertyList properties, Dbms dbms) throws Exception{
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
    }

    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_TYPE, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_NULLABLE, false));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_PRIMARY_KEY, false));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_LENGHT, false));
        //ID es agregado en la clase Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        return new Column(properties, dbms);
    }

    @Override
    public JXTag exportJXTag() {
        return new JXColumnTag(this.getPropertyValueByKey(Constants.PROPERTY_ID), this.getPropertyValueByKey(Constants.PROPERTY_NAME), this.getPropertyValueByKey(Constants.PROPERTY_TYPE),this.getPropertyValueByKey(Constants.PROPERTY_LENGHT), this.getPropertyValueByKey(Constants.PROPERTY_NULLABLE), this.getPropertyValueByKey(Constants.PROPERTY_PRIMARY_KEY));
    }
    
}