/*
 * Es una lista de propiedades de los tags del changelog.
 */
package org.tesis.changelog.property;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.tag.Tag;
import org.tesis.db.Constants;
import org.tesis.exception.InvalidPropertyException;
import org.tesis.exception.InvalidValueException;

/**
 *
 * @author Claus
 */
public class PropertyList {
    List<Property> properties;

    public PropertyList() {
        this.properties=new ArrayList<>();
    }
    
    public boolean addProperty(String key, String value) throws InvalidPropertyException, InvalidValueException{
        if(null!=value){
            try{
                for(Property p : properties) {
                    if(p.getKey().equals(key)){
                       // System.out.println("Propiedad "+key+" ya fue cargada a PropertyList");
                        return false;
                    }
                }
                properties.add(PropertyFactory.getPropertyInstance(key, value));//obtiene una instancia de TagProperty de acuerdo al key.
                return true;
            }catch(InvalidValueException ex){
                String id=getPropertyValueByKey(Constants.PROPERTY_ID);
                if(null!=id){
                    throw new InvalidValueException(ex.getMessage()+".\nPropiedades actuales leídas en el tag: "+this.toString());
                }else{
                    throw ex;
                }
            }
        }else{
            return false;
        }
    }

    public List<Property> getProperties() {
        return properties;
    }
    public boolean isEmpty(){
        return this.properties.isEmpty();
    }
    /**
     * Retorna la lista de keys de propiedades que no estan en la la lista de propiedades.
     * @param validProperties
     * @return lista de keys de propiedades que no estan en la la lista de propiedades
     */
    public List<String> requiredPropertiesNotPresent(List<Tag.TagProperty> validProperties){
        boolean p;
        List<String> result=new ArrayList<>();
        for(Tag.TagProperty tp: validProperties){
            if(tp.isRequired()){
                p=false;
                for(Property n : properties) {
                    if(n.getKey().equals(tp.getKey())){
                        p=true;
                        break;
                    }
                }
                if(!p){//agrega a la lista si no está presente
                    result.add(tp.getKey());
                }
            }
        }
        return result;
    }
    /**
     * Retorna la lista de keys de propiedades de la lista de propiedades que no son válidas.
     * @param validProperties
     * @return lista de keys de propiedades que no son válidas.
     */
    public List<String> invalidPropertiesPresent(List<Tag.TagProperty> validProperties){
        boolean p;
        List<String> result=new ArrayList<>();
        p=false;
        for(Property n : properties) {
            for(Tag.TagProperty pt: validProperties){
                if(n.getKey().equals(pt.getKey())){
                    p=true;
                    break;
                }
            }
            if(!p){//agrega a la lista si no está presente
                result.add(n.getKey());
            }
        }
        return result;
    }
    /**
     * retorna el valor de property dado el key
     * @param key nombre del property
     * @return valor de property
     */
    public String getPropertyValueByKey(String key){
        Property p= this.getPropertyByKey(key);
        if (null!=p){
            return p.getValue();
        }else{
            return null;
        }
    }
    /**
     * retorna la property dado el key
     * @param key nombre del property
     * @return la property
     */
    public Property getPropertyByKey(String key){
        for(Property p : properties) {
            if(p.getKey().equals(key)){
                return p;
            }
        }
        return null;
    }
    @Override 
    public String toString(){
        return this.getProperties().toString();
    }
}
