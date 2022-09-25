package org.tesis.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *Esta es la clase header del ChangeLog
 */
//@XmlRootElement(name="Header")
@XmlType(name="header", propOrder = {"project", "author", "date"})
public class JXHeader{
    private String project;
    private String author;
    private String date;
    
    public JXHeader() {
    }
    
    public JXHeader(String project, String author, String date) {
        this.project = project;
        this.author = author;
        this.date = date;
    }
    
    public String getAuthor() {
        return author;
    }
    @XmlElement(name="author")
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }
    @XmlElement(name="date")
    public void setDate(String date) {
        this.date = date;
    }

    public String getProject() {
        return project;
    }
    @XmlElement(name="project")
    public void setProject(String project) {
        this.project = project;
    }
}
