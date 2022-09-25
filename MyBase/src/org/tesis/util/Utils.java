/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tesis.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.tesis.changelog.property.PropertyValueValidator;
import org.tesis.db.Constants;
import org.tesis.exception.InvalidValueException;


public class Utils {
    public static String getPathSeparator(){
        return "\\";
    }
    public static String getDbUrlSeparator(){
        return "/";
    }
    public static boolean writeFileTest(String path){
        FileWriter w;
        try {
            String file=path+Utils.getPathSeparator()+"mbTest.test";
            File f;
            f = new File(file);
            //Escritura
            w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write("test");
            wr.close();
            bw.close();
            f.delete();
            return true;
        } catch (IOException ex) {
            return false;
        } 
    }
    public static boolean writeFile(String filePath, String content){
        FileWriter w;
        try {
            File f;
            f = new File(filePath);
            //Escritura
            w = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(content);
            wr.close();
            bw.close();
            return true;
        } catch (IOException ex) {
            return false;
        } 
    }
    public static String getCurrentDate(){
        //SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
	//return sdf.format(new Date()); 
        return Utils.formatDate(new Date(), Constants.LOG_DATE_FORMAT);
    }
    public static String formatDate(Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
	return sdf.format(date); 
    }
    public static boolean validateDate(String date){
        if(date == null){
            return false;
        }else{
            try{
                Utils.strToDate(date);
                return true;
            }catch (ParseException e) {
                return false;
            }
        }
    }
    public static boolean validateLogDate(String date){
        if(date == null){
            return false;
        }else{
            try{
                Utils.strToLogDate(date);
                return true;
            }catch (ParseException e) {
                return false;
            }
        }
    }
    public static Date strToLogDate(String date) throws ParseException{
        if(date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.LOG_DATE_FORMAT);
        sdf.setLenient(false);
        return sdf.parse(date);
        
    }
    public static Date strToDate(String date) throws ParseException{
        if(date == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
        sdf.setLenient(false);
        return sdf.parse(date);
        
    }
    public static long generateUniqueID(){  
        long id=UUID.randomUUID().getMostSignificantBits();
        if(id<0){
            return (id * -1);
        }else{
            return id;
        }
    }
    /**
     * Retorna la lista de nombres de columnas, si no es válida tira una excepción
     * @param names lista de nombres en un solo String
     * @return lista de nombres
     * @throws Exception 
     */
    public static List<String> getColumnNames(String names) throws Exception{
        List<String> res=new ArrayList<>();
        if(names.isEmpty()){
            return res;
        }else{
            int ini=0;
            for(int i=0;i<names.length();i++){
                char c=names.charAt(i);
                if(c==' '){
                    throw new InvalidValueException("Lista de nombres de columnas PKs: "+names+" no admite espacios en blanco.");
                }else{
                    if(c==','){
                        String name=names.substring(ini, i);
                        if(!PropertyValueValidator.validateName(name) || i==ini || i==(names.length()-1)){//si el nombre no es valido o si hay una coma al principio o al fin o 2 comas seguidas
                            throw new InvalidValueException("Lista de nombres de columnas PKs: "+names+"; estructura inválida.");
                        }else{
                            res.add(name);
                        }
                        ini=i+1;
                    }else{
                        if(i==(names.length()-1)){//si llegó al final
                            String name=names.substring(ini, i+1);
                            if(!PropertyValueValidator.validateName(name)){
                                throw new InvalidValueException("Lista de nombres de columnas PKs: "+names+"; estructura inválida.");
                            }else{
                                res.add(name);
                                break;
                            }
                        }
                    }
                }
            }
            return res;
        }
    }
}
