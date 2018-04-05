package com.example.android.trumpnews.entity;

/**
 * Created by ammar_saaddine on 03.04.18.
 */

public class NewsEntry {

    // Variables

    private String mTitle;
    private String mSectionName;
    private String mAuthor;
    private String mPublicationDate;
    private String mUrl;

    // Constructors

    public NewsEntry(String title, String sectionName, String author, String publicationDate, String url) {
        this.mTitle = title;
        this.mSectionName = sectionName;
        this.mAuthor = author;
        this.mPublicationDate = publicationDate;
        this.mUrl = url;
    }

    // Getters


    public String getTitle() {
        return mTitle;
    }

    public String getSectionName() {
        return mSectionName;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
