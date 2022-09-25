package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.db.Constants;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.DataRow;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.jaxb.JXDataInsertTag;
import org.tesis.jaxb.JXTag;
import org.tesis.mybase.DataColumnList;

public class InsertDataTag extends Tag{
    private DataColumnList dataColumns;
    
    public InsertDataTag(PropertyList properties, DataColumnList dataColumns, Dbms dbms) throws Exception {
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
        this.dataColumns=dataColumns;
    }
    
    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_TABLE_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_DATE, true));
         //ID es agregado en la clase Tag e ID también es InternalId
    }
       

    @Override
    public Object exportDBObject() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag InsertData no fue cargada apropiadamente.");
        }else{
            return new DataRow(this.getInternalId(), properties.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), dataColumns.getDataColumnList());
        }
    }
    
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       DataRow dataRow=(DataRow)this.exportDBObject();
       List<SqlQuery> result= new ArrayList<>();
       result.add(this.getDbms().getDbmsInstance().getAddDataStatement(dataRow));
       return result;
    }

    public List<DataColumnTag> getDataColumnTags(){
        return this.dataColumns.getDataColumnTags();
    }
    
    @Override
    public JXTag exportJXTag(){
        JXDataInsertTag jx=new JXDataInsertTag(this.getId(), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE), this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.dataColumns.exportJXDataColumnTags());
        return jx;
    }
    public String getInternalId() {
        return this.getPropertyValueByKey(Constants.PROPERTY_ID);
    }
    /**
     * Retorna el id del insert y todos los ids de sus columas
     * @return lista de ids
     */
    /*@Override
    public List<String> getIDsList(){
        List<String> res=new ArrayList<>();
        res.add(this.getPropertyValueByKey(Constants.PROPERTY_ID));
        res.addAll(this.dataColumns.getAllColumnIDs());
        return res;
    }*/
}
