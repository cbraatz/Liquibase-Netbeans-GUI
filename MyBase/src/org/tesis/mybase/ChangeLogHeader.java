package org.tesis.mybase;

public class ChangeLogHeader {
    private String author;
    private String creationDate;
    private String projectName;

    public ChangeLogHeader(String projectName, String author, String creationDate) {
        this.author = author;
        this.creationDate = creationDate;
        this.projectName = projectName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}