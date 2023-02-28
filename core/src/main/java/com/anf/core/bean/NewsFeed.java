package com.anf.core.bean;

import java.io.Serializable;

/***
 * Begin Code
 * Name - Abhinav Chatharaboina*
 */
public class NewsFeed implements Serializable{
    private static final long serialVersionUID = 1L;

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlImage;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "NewsFeed{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }

    /* End Code*/
}
