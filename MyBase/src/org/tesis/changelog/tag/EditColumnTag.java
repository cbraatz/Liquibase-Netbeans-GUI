package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.property.PropertiesPair;
import org.tesis.changelog.property.Property;
import org.tesis.changelog.property.PropertyList;
import org.tesis.changelog.type.Type;
import org.tesis.changelog.type.TypeFactory;
import org.tesis.db.Constants;
import org.tesis.db.EditColumnObj;
import org.tesis.db.EditColumnType;
import org.tesis.db.RenameColumn;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.exception.PropertiesNotFoundException;
import org.tesis.jaxb.JXEditColumnTag;
import org.tesis.jaxb.JXTag;

public class EditColumnTag extends Tag{
    private List<EditColumnObj> ecos = new ArrayList<>();
    public EditColumnTag(PropertyList properties, Dbms dbms) throws Exception {
        super(dbms);
        this.setAvailableProperties();
        this.setProperties(properties); 
        this.updateEditObjectList();
    }
    private void setAvailableProperties(){
        availableProperties.add(new TagProperty(Constants.PROPERTY_AUTHOR, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_DATE, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_TABLE_NAME, true));
        availableProperties.add(new TagProperty(Constants.PROPERTY_NEW_NAME, false));
        availableProperties.add(new TagProperty(Constants.PROPERTY_TYPE, false));
        //availableProperties.add(new TagProperty(Constants.PROPERTY_NULLABLE, false));
        availableProperties.add(new TagProperty(Constants.PROPERTY_LENGHT, false));
        availableProperties.add(new TagProperty(Constants.PROPERTY_COLUMN_NAME, true));//el nombre de la tabla es requerido
        availableProperties.add(new TagProperty(Constants.PROPERTY_OLD_TYPE, false));
        //availableProperties.add(new TagProperty(Constants.PROPERTY_OLD_NULLABLE, false));
        availableProperties.add(new TagProperty(Constants.PROPERTY_OLD_LENGHT, false));
        //ID es agregado en la clase Tag
    }
    public List<EditColumnObj> getEditColumnObjectList(){
        return ecos;
    }
    @Override
    public Object exportDBObject() throws Exception{
        this.updateEditObjectList();
        return ecos;
    }
    public EditColumnObj getEditColumnObjectByClassName(Class className){
        for(EditColumnObj o:ecos){
           if (o.getClass().equals(className)){
               return o;
           }
       }
       return null;
    }
    /**
     * Agrega el Objeto RenameColumn si el ColumnName y el NewName son distintos y no son nulos.
     * Lo agrega solo si los parametros que recibe requieren que se agregue
     * @param tableName el nombre de la tabla
     * @throws Exception tira cuando el OldType o el ColumnName son nulos o cuando el ColumnName y el NewName son iguales.
     */
    private void addRenameColumnToEditObjectList(String tableName) throws Exception{
        PropertiesPair pp=new PropertiesPair(properties.getPropertyByKey(Constants.PROPERTY_COLUMN_NAME), properties.getPropertyByKey(Constants.PROPERTY_NEW_NAME));
        if(null != pp.getOldProperty()){
            if(null != pp.getNewProperty()){
                if(!pp.getOldProperty().getValue().equals(pp.getNewProperty().getValue())){
                    Property oldTypeProp=properties.getPropertyByKey(Constants.PROPERTY_OLD_TYPE);//el tipo es requerido para el rename table en mysql asi que se le pedira a todos
                    if(null != oldTypeProp){
                        Type oldType=TypeFactory.getTypeInstance(oldTypeProp.getValue(),dbms);
                        if(oldType.hasLenght()){
                            Property len=properties.getPropertyByKey(Constants.PROPERTY_OLD_LENGHT);
                            if(null!=len){
                                oldType.setLenght(len.getValue());
                            }else{
                                throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_OLD_LENGHT+" es requerida por el tipo de dato '"+oldType.getPublicName()+"' en el tag EditColumn. ");
                            }
                        }
                        ecos.add(new RenameColumn(tableName, pp.getOldProperty().getValue(), pp.getNewProperty().getValue(), oldType));
                    }else{
                        throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_OLD_TYPE+" es requerida y no fue encontrada en el tag EditColumn. ");
                    } 
                }else{
                    throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_COLUMN_NAME+" y "+Constants.PROPERTY_NEW_NAME+" son iguales y no deben serlo en el tag EditColumn. ");
                }
            }
        }else{
           throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_COLUMN_NAME+" es requerida y no fue encontrada en el tag EditColumn. ");
        }
    }
    /**
     * Agrega el Objeto EditColumnType si el oldType y newType - oldLenght y newLenght son distintos y no nulos.
     * Lo agrega solo si los parametros que recibe requieren que se agregue
     * @param tableName el nombre de la tabla
     * @throws Exception
     */
    private void addEditColumnTypeToEditObjectList(String tableName) throws Exception{
        PropertiesPair pp1=new PropertiesPair(properties.getPropertyByKey(Constants.PROPERTY_OLD_TYPE), properties.getPropertyByKey(Constants.PROPERTY_TYPE));
        PropertiesPair pp2=new PropertiesPair(properties.getPropertyByKey(Constants.PROPERTY_OLD_LENGHT), properties.getPropertyByKey(Constants.PROPERTY_LENGHT));
        Property colNewName=properties.getPropertyByKey(Constants.PROPERTY_NEW_NAME);
        Property colNameProp=(null != colNewName?colNewName:properties.getPropertyByKey(Constants.PROPERTY_COLUMN_NAME));//si se editó el nombre, se usa el nombre nuevo xq se actualiza el nombre primero
        if(null != colNameProp){
            if(null != pp1.getNewProperty() && null != pp1.getOldProperty()){//si el tipo viejo y nuevo no son nulos
                if(pp1.getOldProperty().getValue().equals(pp1.getNewProperty().getValue())){ //si los dos tipos son iguales
                    if(null != pp2.getNewProperty() && null != pp2.getOldProperty()){ //si el lenght viejo y nuevo no son nulos
                        if(pp2.getOldProperty().getValue().equals(pp2.getNewProperty().getValue())){ //si los lenght son iguales
                            throw new InvalidPropertyException("Las propiedades "+Constants.PROPERTY_OLD_TYPE+" y "+Constants.PROPERTY_TYPE+" son iguales y las propiedades "+Constants.PROPERTY_OLD_LENGHT+" y "+Constants.PROPERTY_LENGHT+" también son igualesy no deben serlo en el tag EditColumn. ");
                        }
                    }else{
                        throw new InvalidPropertyException("Las propiedades "+Constants.PROPERTY_OLD_TYPE+" y "+Constants.PROPERTY_TYPE+" son iguales y no deben serlo si ambas propiedades "+Constants.PROPERTY_OLD_LENGHT+" y "+Constants.PROPERTY_LENGHT+" no existen en el tag EditColumn. ");
                    }
                }
                //si hay diferencias en los tipos o en los lenght
                Type oldType=TypeFactory.getTypeInstance(pp1.getOldProperty().getValue(),dbms);
                Type newType=TypeFactory.getTypeInstance(pp1.getNewProperty().getValue(),dbms);
                if(oldType.hasLenght()){
                    if(null!=pp2.getOldProperty()){
                        oldType.setLenght(pp2.getOldProperty().getValue());
                    }else{
                        throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_OLD_LENGHT+" es requerida por el tipo de dato '"+oldType.getPublicName()+"' en el tag EditColumn. ");
                    }
                }
                if(newType.hasLenght()){
                    if(null!=pp2.getNewProperty()){
                        newType.setLenght(pp2.getNewProperty().getValue());
                    }else{
                        throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_LENGHT+" es requerida por el tipo de dato '"+newType.getPublicName()+"' en el tag EditColumn. ");
                    }
                }
                ecos.add(new EditColumnType(tableName, colNameProp.getValue(), oldType, newType));
                
            }
        }else{
           throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_COLUMN_NAME+" es requerida y no fue encontrada en el tag EditColumn. ");
        }
    }
     /**
     * Agrega el Objeto EditColumnNullable si el oldNullable y el newNullable son distintos y no son nulos.
     * Lo agrega solo si los parametros que recibe requieren que se agregue
     * @param tableName el nombre de la tabla
     * @throws Exception
     */
    /*private void addEditColumnNullableToEditObjectList(String tableName) throws Exception{
        PropertiesPair pp=new PropertiesPair(properties.getPropertyByKey(Constants.PROPERTY_OLD_NULLABLE), properties.getPropertyByKey(Constants.PROPERTY_NULLABLE));
        Property colNewName=properties.getPropertyByKey(Constants.PROPERTY_NEW_NAME);
        Property colNameProp=(null != colNewName?colNewName:properties.getPropertyByKey(Constants.PROPERTY_COLUMN_NAME));//si se editó el nombre, se usa el nombre nuevo xq se actualiza el nombre primero
        if(null != colNameProp){
            Property oldTypeProp=properties.getPropertyByKey(Constants.PROPERTY_OLD_TYPE);//el tipo es requerido para el rename table en mysql asi que se le pedira a todos
            if(null != oldTypeProp){
                Type oldType=TypeFactory.getTypeInstance(oldTypeProp.getValue(),dbms);
                if(oldType.hasLenght()){
                    Property len=properties.getPropertyByKey(Constants.PROPERTY_OLD_LENGHT);
                    if(null!=len){
                        oldType.setLenght(len.getValue());
                    }else{
                        throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_OLD_LENGHT+" es requerida por el tipo de dato '"+oldType.getPublicName()+"' en el tag EditColumn. ");
                    }
                }
                if(null != pp.getOldProperty() && null != pp.getNewProperty()){
                    if(!pp.getOldProperty().getValue().equals(pp.getNewProperty().getValue())){
                        ecos.add(new EditColumnNullable(tableName, colNameProp.getValue(), oldType, Boolean.parseBoolean(pp.getOldProperty().getValue()), Boolean.parseBoolean(pp.getNewProperty().getValue())));
                    }else{
                        throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_OLD_NULLABLE+" y "+Constants.PROPERTY_NULLABLE+" son iguales y no deben serlo en el tag EditColumn. ");
                    }
                }
            }else{
                throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_OLD_TYPE+" es requerida y no fue encontrada en el tag EditColumn. ");
            } 
        }else{
           throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_COLUMN_NAME+" es requerida y no fue encontrada en el tag EditColumn. ");
        }
    }*/
    /**
     * Agrega a la una lista todas las actualizaciones de bases de datos relacionadas con EditTable como cambiar el nombre de la columna, cambiar el tipo de dato, etc.
     * @throws Exception si algún parámetro no fue agregado correctamente como el nombre de la tabla, columna, etc.
     */
    private void updateEditObjectList() throws Exception{
        if(this.properties.isEmpty()){
            throw new PropertiesNotFoundException("La lista de propiedades del tag EditTable no fue cargada apropiadamente.");
        }else{
            ecos=new ArrayList<>();
            //implementado el rename column
            Property tableName=properties.getPropertyByKey(Constants.PROPERTY_TABLE_NAME);
            if(null != tableName){
                this.addRenameColumnToEditObjectList(tableName.getValue());
                //this.addEditColumnNullableToEditObjectList(tableName.getValue());
                this.addEditColumnTypeToEditObjectList(tableName.getValue());
            }else{
               throw new InvalidPropertyException("Propiedad "+Constants.PROPERTY_TABLE_NAME+" es requerida y no fue encontrada en el tag Edit Column. ");
            }
        }
    }
    @Override
    public List<SqlQuery> exportSQLQuery()throws Exception{
       this.updateEditObjectList();
       List<SqlQuery> result= new ArrayList<>();
       for(EditColumnObj o:ecos){
           result.add(o.getSQLStatement(dbms));
       }
       return result;
    }
    
    @Override
    public JXTag exportJXTag(){
        return new JXEditColumnTag(this.getId(), this.getPropertyValueByKey(Constants.PROPERTY_AUTHOR), this.getPropertyValueByKey(Constants.PROPERTY_DATE), this.getPropertyValueByKey(Constants.PROPERTY_TABLE_NAME), this.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME), this.getPropertyValueByKey(Constants.PROPERTY_NEW_NAME), this.getPropertyValueByKey(Constants.PROPERTY_OLD_TYPE), this.getPropertyValueByKey(Constants.PROPERTY_TYPE), this.getPropertyValueByKey(Constants.PROPERTY_OLD_LENGHT), this.getPropertyValueByKey(Constants.PROPERTY_LENGHT));
    }
}
