package com.example.android.trumpnews.entity;

import java.util.List;

/**
 * Created by ammar_saaddine on 03.04.18.
 */

public class NewsEntry {

    // Variables

    private String mTitle;
    private String mSectionName;
    private List<String> mAuthors;
    private String mPublicationDate;
    private String mUrl;

    // Constructors

    public NewsEntry(String title, String sectionName, List<String> authors, String publicationDate, String url) {
        this.mTitle = title;
        this.mSectionName = sectionName;
        this.mAuthors = authors;
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

    public List<String> getAuthors() {
        return mAuthors;
    }

    public String getPublicationDate() {
        return mPublicationDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
