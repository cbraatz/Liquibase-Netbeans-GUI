package org.tesis.dbapi;
/**
 * Clase que permite  tanto la obtención como el seteo del autor del proyecto
 * @author Denis Maernitz
 */

public class Author {
    String author;
    /**
     * Método utilizado para setea  el autor de un proyecto, verifica que el nombre del autor recibido por parámetro sea correcto
     * @param author Nombre del autor del proyecto
     */

    public Author(String author) {
        if(null!=author){
            this.author = author;
        }else{
            throw new NullPointerException("El nombre de autor no puede ser nulo.");
        }
    }
    /**
     * Método que permite obtener el nombre de autor  de un protecto
     * @return author El nombre del autor del proyecto  
     */
    public String getAuthorName() {
        return author;
    }
}
