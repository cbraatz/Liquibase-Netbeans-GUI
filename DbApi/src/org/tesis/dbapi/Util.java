/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.dbapi;

import java.util.UUID;

/**
 * Clase en la cual se van  escribiendo métodos de propósito general, los métodos  escritos en esta clase  son  considerados  utilitarios, son métodos que cumplen 
 * funciones muy específicas, y que son utilizadas por otros métodos.
 * 
 * @author Claus
 */
public class Util {
    
    /**
     * Método que permite obtener el nombre de un archivo a partir de la  dirección de origen del mismo.
     * 
     * @param path ruta del archivo del cual se quiere obtener el nombre
     *@return Nombre del archivo 
     */
    public static String getFileName(String path){
        int c=path.length()-1;
        for(;c >= 0;c--){
            if(path.charAt(c)=='/' || path.charAt(c)=='\\'){
                break;
            }
        }
        if(c>=0){
            return path.substring(c+1);
        }
        return null;
    }
    
    /**
         * Método que permite obtener el nombre de una base de datos a partir de la URL de la misma.
         * 
         * @param jdbcUrl Url completa de la base de datos 
         * @return  Nombre de la base de datos
    */

    public static String getDatabaseNameFromJdbcUrl(String jdbcUrl){
        return Util.getFileName(jdbcUrl);
    }
    
    /**
     * Método que obtiene la ruta de algún directorio, recibe como parámetro la ruta completa en donde se encuentra el archivo, y devuelve la ruta para acceder al directorio
     * 
     * @param path Ruta completa de en donde se encuentra el archivo
     * @return Ruta del directorio 
     */

    public static String getDirectoryPath(String path){
        String file=Util.getFileName(path);
        if(null!=file){
            int i=path.indexOf(file);
            return path.substring(0, i);
        }else{
            return null;
        }
    }
    /**
     * Método que permite obtener la extensión de un archivo
     * 
     * @param path Ruta completa de en donde se encuentra el archivo
     * @return Extensión del archivo
     */

    public static String getFileExtension(String path){
        int c=path.length()-1;
        for(;c >= 0;c--){
            if(path.charAt(c)=='.'){
                break;
            }
        }
        if(c>=0){
            return path.substring(c+1);
        }
        return null;
    }
    
}
