package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.db.Constants;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.RemoveData;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.jaxb.JXRemoveDataTag;
import org.tesis.jaxb.JXTag;

public class RemoveDataTag extends Tag{
    
    public RemoveDataTag(PropertyList properties, Dbms dbms) throws Exception {//pks puede ser nulo en el caso de que se quiera borrar todos los datos de la table usando getRemoveAllDataFromTableStatement en dbms
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
    }
    
    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_TABLE_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_DATE, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_INTERNAL_ID, true));
        //ID es agregado en la clase Tag
    }
    public String getInternalId() {
        return this.getPropertyValueByKey(Constants.PROPERTY_INTERNAL_ID);
    }
    
    @Override
    public Object exportDBObject() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag RemoveData no fue cargada apropiadamente.");
        }else{
            return new RemoveData(this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.getInternalId());
        }
    }
    
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       RemoveData removeData=(RemoveData)this.exportDBObject();
       List<SqlQuery> result= new ArrayList<>();
       result.add(this.getDbms().getDbmsInstance().getRemoveDataStatement(removeData));
       return result;
    }

    @Override
    public JXTag exportJXTag(){
        JXRemoveDataTag jx=new JXRemoveDataTag(this.getId(), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE), this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.getInternalId());
        return jx;
    }
}
