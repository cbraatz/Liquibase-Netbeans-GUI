package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.db.Constants;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.EditData;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.jaxb.JXEditDataTag;
import org.tesis.jaxb.JXTag;
import org.tesis.mybase.DataColumnList;

public class EditDataTag extends Tag{
    private DataColumnList dataColumns;
    
    public EditDataTag(PropertyList properties, DataColumnList dataColumns, Dbms dbms) throws Exception {
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
        this.dataColumns=dataColumns;
    }
    
    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_TABLE_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_DATE, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_INTERNAL_ID, true));
        //ID es agregado en la clase Tag
    }

    @Override
    public Object exportDBObject() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag EditData no fue cargada apropiadamente.");
        }else{
            return new EditData(this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME),dataColumns.getDataColumnList(), this.getInternalId());
        }
    }
    
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       EditData editData=(EditData)this.exportDBObject();
       List<SqlQuery> result= new ArrayList<>();
       result.add(this.getDbms().getDbmsInstance().getEditDataStatement(editData));
       return result;
    }

    public String getInternalId() {
        return this.getPropertyValueByKey(Constants.PROPERTY_INTERNAL_ID);
    }

    public DataColumnList getDataColumns() {
        return dataColumns;
    }

    @Override
    public JXTag exportJXTag(){
        JXEditDataTag jx=new JXEditDataTag(this.getId(), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE), this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.getInternalId(), this.dataColumns.exportJXDataColumnTags());
        return jx;
    }
}
