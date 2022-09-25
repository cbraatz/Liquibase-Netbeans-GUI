package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.AddColumn;
import org.tesis.db.Column;
import org.tesis.db.Constants;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.jaxb.JXAddColumnTag;
import org.tesis.jaxb.JXTag;

public class AddColumnTag extends Tag{
    public AddColumnTag(PropertyList properties, Dbms dbms) throws Exception {
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties); 
    }
    private void setAvailableProperties(){
        availableProperties.add(new TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_DATE, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_TABLE_NAME, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_NAME, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_TYPE, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_PRIMARY_KEY, false));
        availableProperties.add(new TagProperty(Constants.PROPERTY_NULLABLE, false));
        availableProperties.add(new TagProperty(Constants.PROPERTY_LENGHT, false));
        //ID es agregado en la clase Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        return new AddColumn(new Column(this.properties, this.getDbms()),this.properties.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME));
    }
    
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       List<SqlQuery> result= new ArrayList<>();
       result.add(this.getDbms().getDbmsInstance().getAddColumnStatement((AddColumn)this.exportDBObject()));
       return result;
    }
    
    @Override
    public JXTag exportJXTag(){
        return new JXAddColumnTag(this.getId(), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE), this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.getPropertyValueByKey(Constants.PROPERTY_NAME), this.getPropertyValueByKey(Constants.PROPERTY_TYPE), this.getPropertyValueByKey(Constants.PROPERTY_LENGHT), this.getPropertyValueByKey(Constants.PROPERTY_NULLABLE), this.getPropertyValueByKey(Constants.PROPERTY_PRIMARY_KEY));
    }
}
