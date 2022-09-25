package org.tesis.db;

import org.tesis.changelog.type.Type;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.type.TypeFactory;
import org.tesis.db.dbms.Dbms;
import org.tesis.util.Utils;

public class Column {
    private String name;
    private boolean pk=false;
    private boolean allowNull=true;
    private Type type;
    private long internalId=0;
    
    
    public Column(PropertyList properties, Dbms dbms) throws Exception{ //propertyList no incluye el fk -- parametro foreignKey puede ser null
        this.name=properties.getPropertyValueByKey(Constants.PROPERTY_NAME);
        String p=properties.getPropertyValueByKey(Constants.PROPERTY_PRIMARY_KEY);
        if(null!=p){
            this.pk=Boolean.parseBoolean(p);//solo asigna si se le dió un valor en el changelog, sino toma los valores por defecto definicos en Column
        }
        p=properties.getPropertyValueByKey(Constants.PROPERTY_NULLABLE);
        if(null!=p){
            this.allowNull=Boolean.parseBoolean(p);//solo asigna si se le dió un valor en el changelog, sino toma los valores por defecto definicos en Column
        }
        String typeName=properties.getPropertyValueByKey(Constants.PROPERTY_TYPE);
        String lenght=properties.getPropertyValueByKey(Constants.PROPERTY_LENGHT);
        Integer i = null;
        this.type=TypeFactory.getTypeInstance(typeName,dbms);
        type.setLenght(lenght);
        this.internalId=Long.parseLong(properties.getPropertyValueByKey(Constants.PROPERTY_ID));
    }

    public Column(String name, Type type, boolean pk, boolean nullable) {//foreignKey puede ser null
        this.name = name;
        this.type = type;
        this.pk=pk;
        this.allowNull=nullable;
        this.internalId=Utils.generateUniqueID();
    }
    public Column(String name, Type type, boolean pk, boolean nullable, long internalId) { //foreignKey puede ser null
        this.name = name;
        this.type = type;
        this.pk=pk;
        this.allowNull=nullable;
        this.internalId=internalId;
    }
    public String getName() {
        return name;
    }

    public long getInternalId() {
        return internalId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isPk() {
        return pk;
    }

    public boolean isAllowNull() {
        return allowNull;
    }

    public Type getType() {
        return type;
    }
    
    public boolean compareName(Column c){
        if(this.getName().equals(c.getName())){
            return true;
        }else{
            return false;
        }
    }
    public boolean compareType(Column c){
        if(this.getType().toString().equals(c.getType().toString())){
            return true;
        }else{
            return false;
        }
    }
    public boolean comparePk(Column c){
        if(this.isPk()==c.isPk()){
            return true;
        }else{
            return false;
        }
    }
    public boolean compareNullable(Column c){
        if(this.isAllowNull()==c.isAllowNull()){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public String toString(){
        return this.name;
    }
}
