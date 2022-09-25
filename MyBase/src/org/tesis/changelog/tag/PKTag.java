package org.tesis.changelog.tag;

import org.tesis.db.Constants;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.PK;
import org.tesis.db.dbms.Dbms;
import org.tesis.jaxb.JXPKTag;
import org.tesis.jaxb.JXTag;

public class PKTag extends Tag{
    public PKTag(PropertyList properties, Dbms dbms) throws Exception{
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
    }

    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_COLUMN_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_VALUE, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_QUOTED, true));
        //ID es agregado en la clase Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        return new PK(properties, dbms);
    }

    @Override
    public JXTag exportJXTag() {
        return new JXPKTag(this.getPropertyValueByKey(Constants.PROPERTY_ID), this.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME), this.getPropertyValueByKey(Constants.PROPERTY_VALUE),this.getPropertyValueByKey(Constants.PROPERTY_QUOTED));
    }
    
}