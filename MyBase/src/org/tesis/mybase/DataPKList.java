package org.tesis.mybase;

import java.util.ArrayList;
import java.util.List;
import org.tesis.changelog.tag.PKTag;
import org.tesis.db.Constants;
import org.tesis.db.PK;
import org.tesis.exception.NotUniqueNameException;
import org.tesis.jaxb.JXPKTag;

/**
 * Es una lista de PKs
 */
public class DataPKList {
    private List<PKTag> pkTags;
    
    public DataPKList() {
        this.pkTags = new ArrayList<>();
    }
    public DataPKList(List<PKTag> pkTags) {
        this.pkTags = pkTags;
    }
    public void addPKTag(PKTag pkTag)throws NotUniqueNameException{
        for(PKTag c:pkTags){
            if(c.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME).equals(pkTag.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME))){
                throw new NotUniqueNameException("Nombre no válido para PK = "+pkTag.getPropertyValueByKey(Constants.PROPERTY_ID)+". ColumnName = "+c.getPropertyValueByKey(Constants.PROPERTY_COLUMN_NAME)+" ya está agregado.");
            }
        }
        this.pkTags.add(pkTag);
    }
    public List<PK> getPKList() throws Exception{
        List<PK> pks=new ArrayList<>();
        for(PKTag pkt: pkTags){
            PK c=(PK) pkt.exportDBObject();
            pks.add(c);
        }
        return pks;
    }
    public boolean isEmpty(){
        return this.pkTags.isEmpty();
    }
    public List<PKTag> getPKTags() {
        return pkTags;
    }
    public List<JXPKTag> exportJXPKTags(){
        List<JXPKTag> res=new ArrayList<>();
        for(PKTag c:this.pkTags){
            res.add((JXPKTag)c.exportJXTag());
        }
        return res;
    }
}
