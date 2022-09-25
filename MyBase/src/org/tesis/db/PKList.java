package org.tesis.db;

import org.tesis.mybase.*;
import java.util.ArrayList;
import java.util.List;
import org.tesis.db.PK;

/**
 * Es una lista de PKs
 */
public class PKList {
    private List<PK> pks;

    public PKList() {
        pks=new ArrayList<>();
    }
    public void addPk(PK pk){
        pks.add(pk);
    }
    public List<PK> getPKs() {
        return pks;
    }
    public boolean isEmpty(){
        return this.pks.isEmpty();
    }
}
