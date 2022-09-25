package org.tesis.changelog.tag;

import org.tesis.changelog.property.PropertyList;
import org.tesis.db.Constants;
import org.tesis.db.DataColumn;
import org.tesis.db.dbms.Dbms;
import org.tesis.jaxb.JXDataColumnTag;
import org.tesis.jaxb.JXTag;

public class DataColumnTag extends Tag{
    public DataColumnTag(PropertyList properties, Dbms dbms) throws Exception {
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties); 
    }
    private void setAvailableProperties(){
        availableProperties.add(new TagProperty(Constants.PROPERTY_NAME, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_VALUE, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_QUOTED, true));
        //ID es agregado en la clase Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        return new DataColumn(this.properties.getPropertyValueByKey(Constants.PROPERTY_NAME),this.properties.getPropertyValueByKey(Constants.PROPERTY_VALUE),this.properties.getPropertyValueByKey(Constants.PROPERTY_QUOTED));
    }
    
    @Override
    public JXTag exportJXTag(){
        return new JXDataColumnTag(this.getId(), this.getPropertyValueByKey(Constants.PROPERTY_NAME), this.getPropertyValueByKey(Constants.PROPERTY_VALUE), this.getPropertyValueByKey(Constants.PROPERTY_QUOTED));
    }
}
