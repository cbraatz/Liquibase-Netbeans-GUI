package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.db.Constants;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.EditTablePK;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.jaxb.JXEditTablePKTag;
import org.tesis.jaxb.JXTag;

public class EditTablePKTag extends Tag{
    public EditTablePKTag(PropertyList properties, Dbms dbms) throws Exception{
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
    }

    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_TABLE_NAME, true)); 
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_PK_NAME, false));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_DATE, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_PKS, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_OLD_PKS, false));
        //ID es agregado en la clase Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag EditTablePKTag no fue cargada apropiadamente.");
        }else{
            return new EditTablePK(properties.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME),properties.getPropertyValueByKey(Constants.PROPERTY_PK_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_OLD_PKS),properties.getPropertyValueByKey(Constants.PROPERTY_PKS));
        }
    }
    
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       EditTablePK edit=(EditTablePK)this.exportDBObject();
       List<SqlQuery> result= new ArrayList<>();
       result.add(this.getDbms().getDbmsInstance().getEditTablePKStatement(edit));
       return result;
    }
    
    @Override
    public JXTag exportJXTag() {
        return new JXEditTablePKTag(this.getPropertyValueByKey(Constants.PROPERTY_ID), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE), this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.getPropertyValueByKey(Constants.PROPERTY_PK_NAME), this.getPropertyValueByKey(Constants.PROPERTY_OLD_PKS), this.getPropertyValueByKey(Constants.PROPERTY_PKS));
    }
}