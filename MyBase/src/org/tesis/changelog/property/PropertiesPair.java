package org.tesis.changelog.property;

public final class PropertiesPair{
    Property oldProperty,newProperty;

    public PropertiesPair(Property oldProperty, Property newProperty) {
        this.oldProperty = oldProperty;
        this.newProperty = newProperty;
    }

    public Property getOldProperty() {
        return oldProperty;
    }

    public Property getNewProperty() {
        return newProperty;
    }

}