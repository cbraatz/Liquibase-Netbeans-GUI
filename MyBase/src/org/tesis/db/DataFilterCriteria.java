package org.tesis.db;
public enum DataFilterCriteria {
    MENOR_QUE(1),
    MAYOR_QUE(1),
    ENTRE(2),
    IGUAL_A(1),
    DIFERENTE_A(1),
    CONTIENE(1);
    private final int numberOfFields;
    DataFilterCriteria(int numberOfFields){
        this.numberOfFields=numberOfFields;
    }
    public int getNumberOfFields(){
        return this.numberOfFields;
    }
}
