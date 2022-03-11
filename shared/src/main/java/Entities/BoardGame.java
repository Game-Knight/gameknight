package Entities;

import java.io.Serializable;

import Utils.EntityUtils;

public class BoardGame implements Serializable {

    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private boolean borrowed;

    public BoardGame(String title, String description, String imageUrl) {
        this.id = EntityUtils.generateId();
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.borrowed = false;
    }

    public String getId() {
        return id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }
}