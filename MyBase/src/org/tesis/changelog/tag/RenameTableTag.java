package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.db.Constants;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.RenameTable;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.jaxb.JXRenameTableTag;
import org.tesis.jaxb.JXTag;

public class RenameTableTag extends Tag{
    public RenameTableTag(PropertyList properties, Dbms dbms) throws Exception{
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
    }

    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_OLD_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_DATE, true));
        //ID es agregado en la clase Tag
    }
    @Override
    public Object exportDBObject() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag RenameTable no fue cargada apropiadamente.");
        }else{
            return new RenameTable(properties.getPropertyValueByKey(Constants.PROPERTY_NAME),properties.getPropertyValueByKey(Constants.PROPERTY_OLD_NAME));
        }
    }
    
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       RenameTable ren=(RenameTable)this.exportDBObject();
       List<SqlQuery> result= new ArrayList<>();
       result.add(this.getDbms().getDbmsInstance().getRenameTableStatement(ren));
       return result;
    }
    
    @Override
    public JXTag exportJXTag() {
        return new JXRenameTableTag(this.getPropertyValueByKey(Constants.PROPERTY_ID), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE),this.getPropertyValueByKey(Constants.PROPERTY_NAME), this.getPropertyValueByKey(Constants.PROPERTY_OLD_NAME));
    }
    
    /*@Override
    public boolean hasUniqueName(){
        return true;
    }
    
    @Override
    public boolean addsNewDbObject(){
        return false;
    }*/
}