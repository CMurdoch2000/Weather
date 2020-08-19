package com.example.weather;
// Created By Callum Macleod Murdoch - S1828149
public class RSSFeed {
    //Declaring variables
    private int uniqueID;
    private String title;
    private String description;
    private String link;
    private String DatePublished;

    //Initialising variables
    public RSSFeed() {
        this.title = "";
        this.description = "";
        this.link = "";
        this.DatePublished = "";
    }

    //Making constructor
    public RSSFeed(String title, String description, String link, String DatePublished) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.DatePublished = DatePublished;
    }

    //Declaring getters and setters
    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDatePublished() { return DatePublished; }

    public void setDatePublished(String published) { this.DatePublished = published; }

}
