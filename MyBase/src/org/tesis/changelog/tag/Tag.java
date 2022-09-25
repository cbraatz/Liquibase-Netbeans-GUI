/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.changelog.tag;

import java.util.ArrayList;
import java.util.List;
import org.tesis.db.Constants;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.exception.RequiredPropertyNotPresentException;
import org.tesis.changelog.property.PropertyList;
import org.tesis.db.SqlQuery;
import org.tesis.db.dbms.Dbms;
import org.tesis.jaxb.JXTag;

public abstract class Tag {
    PropertyList properties=null;
    Dbms dbms=null;
    List<TagProperty> availableProperties=new ArrayList<>();
    public Tag(Dbms dbms){
        availableProperties.add(new TagProperty(Constants.PROPERTY_ID, true));
        this.dbms=dbms;
    }
    /**
     * Retorna la lista de propiedades.
     * @return lista de propiedades
     */
    public PropertyList getProperties() {
        return properties;
    }
    /**
     * setea la lista de propiedades y retorna si son válidas para ese tag en específico
     * @param properties
     * @return si el la lista de propiedades es válida.
     */
    public void setProperties(PropertyList properties) throws Exception {
        if(verifyPropertiesList(properties)){
            this.properties = properties;
            //System.out.print("Propiedades válidas");
        }
    }

    public Dbms getDbms() {
        return dbms;
    }
    
    /**
     * Debe ser implementado en los descendientes y de acuerdo a la instancia de tag, 
     * debe determinar si la lista de propiedades es válida o no.
     * @return si la lista de propiedades es válida o no
     */
    public boolean verifyPropertiesList(PropertyList properties)throws InvalidPropertyException, RequiredPropertyNotPresentException{
        List<String> list=properties.invalidPropertiesPresent(getAvailableProperties());
        if(!list.isEmpty()){
            throw new InvalidPropertyException("\nLas siguientes propiedades no son válidas para el tag: "+list.toString()+".\nPropiedades actuales leídas en el tag: "+properties.toString());//
        }
        list=properties.requiredPropertiesNotPresent(getAvailableProperties());
        if(!list.isEmpty()){
            throw new RequiredPropertyNotPresentException("\nLas siguientes propiedades requeridas no están presentes en el tag: "+list.toString()+".\nPropiedades actuales leídas en el tag: "+properties.toString());
        }
        return true;
    }
    public List<TagProperty> getAvailableProperties() {
        return availableProperties;
    }
    /**
     * Debe ser implementado en los descendientes y de acuerdo a la instancia de tag, 
     * debe retornar el objeto de base de datos específico.
     * @return objeto de base de datos de acuerdo a la instancia de tag.
     */
    public Object exportDBObject() throws Exception{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * Debe ser implementado en los descendientes y de acuerdo a la instancia de tag, 
     * debe retornar la consulta SQL específica para ese tag.
     * @return la consulta SQL específica para ese tag.
     */
    public List<SqlQuery> exportSQLQuery() throws Exception{
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public JXTag exportJXTag(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * Retorna la lista de keys requeridos.
     * @return la lista de keys requeridos.
     */
    public List<String> getRequiredKeys(){
        List<String> result=new ArrayList<>();
        for(TagProperty p: availableProperties){
            if(p.isRequired()){
                result.add(p.getKey());
            }
        }
        return result;
    }/**
     * retorna el id del tag
     * @return el id del tag
     */
    public String getId(){
        return properties.getPropertyValueByKey(Constants.PROPERTY_ID);
    }
    /**
     * Retorna la propiedad por el key
     * @param key nombre de la propiedad
     */
    public String getPropertyValueByKey(String key){
        return this.properties.getPropertyValueByKey(key);
    }
    /**
     * inner class TagProperty
     */
    public class TagProperty {
        private final String _key;
	private final boolean _required; 
        
        TagProperty(String key, boolean isRequired){
            _key=key;
            _required=isRequired;
        }

        public String getKey() {
            return _key;
        }

        public boolean isRequired() {
            return _required;
        }        
    }
    /**
     * retorna la lista de ids utilizados por este tag, solo en caso de table retorna una lista con mas de un id por las columnas que contiene
     * @return lista de ids utilizados por este tag
     */
    public List<String> getIDsList(){
        List<String> res=new ArrayList<>();
        res.add(this.getPropertyValueByKey(Constants.PROPERTY_ID));
        return res;
    }
    /**
     * Retorna si tiene un nombre que lo identifica como nombre de tablas.
     */
    /*public boolean hasUniqueName(){
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
    /**
     * Retorna si se está agregando nuevo objeto de base de datos como tablas.
     */
    /*public boolean addsNewDbObject(){
        throw new UnsupportedOperationException("Not supported yet.");
    }*/
}
