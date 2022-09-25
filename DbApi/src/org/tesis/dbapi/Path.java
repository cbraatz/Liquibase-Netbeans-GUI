package org.tesis.dbapi;

public class Path {
    private final String path;

    public Path(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return path;
    }
    
}
