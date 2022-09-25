
package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.db.Constants;
import org.tesis.db.Table;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.mybase.ColumnList;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.type.Type;
import org.tesis.changelog.type.TypeFactory;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.jaxb.JXNewTableTag;
import org.tesis.jaxb.JXTag;

/**
 *
 * @author Claus
 */
public class NewTableTag extends Tag{
    private ColumnList columns;
    
    public NewTableTag(PropertyList properties, ColumnList columns, Dbms dbms) throws Exception {
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties);
        this.columns=columns;
    }
    
    private void setAvailableProperties(){
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_PK_NAME, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new Tag.TagProperty(Constants.PROPERTY_DATE, true));
        //ID es agregado en la clase Tag
    }

    @Override
    public Object exportDBObject() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag NewTable no fue cargada apropiadamente.");
        }else{
            return new Table(properties.getPropertyValueByKey(Constants.PROPERTY_NAME), properties.getPropertyValueByKey(Constants.PROPERTY_PK_NAME), columns.getColumnList());
        }
    }
    
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       Table table=(Table)this.exportDBObject();
       //String sql="CREATE TABLE "+table.getName()+" "+columns.toString()+";";
       //MyBaseLogger.LogInformationMessage(sql, "NewTableTag");
       //return new SqlQuery(sql);
       List<SqlQuery> result= new ArrayList<>();
       Type type=TypeFactory.getTypeInstance(Constants.DEFAULT_TABLE_ID_TYPE,dbms);
       type.setLenght(Constants.DEFAULT_TABLE_ID_LENGHT);
       result.add(this.getDbms().getDbmsInstance().getCreateTableStatement(table, type.toString()));
       return result;
    }

    public List<ColumnTag> getColumnTags(){
        return this.columns.getColumnTags();
    }
    
    @Override
    public JXTag exportJXTag(){
        JXNewTableTag jx=new JXNewTableTag(this.getId(), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE), this.getPropertyValueByKey(Constants.PROPERTY_NAME), this.getPropertyValueByKey(Constants.PROPERTY_PK_NAME), this.columns.exportJXColumnTags());
        return jx;
    }
    /**
     * Retorna el id de la tabla y todos los ids de sus columas
     * @return lista de ids
     */
    @Override
    public List<String> getIDsList(){
        List<String> res=new ArrayList<>();
        res.add(this.getPropertyValueByKey(Constants.PROPERTY_ID));
        res.addAll(this.columns.getAllColumnIDs());
        return res;
    }
}
