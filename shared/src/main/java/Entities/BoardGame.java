package Entities;

import java.io.Serializable;

import Utils.EntityUtils;

public class BoardGame implements Serializable {

    private String id;
    private String thumbnailUrl;
    private String imageUrl;
    private String name;
    private String description;
    private int yearPublished;
    private int minPlayers;
    private int maxPlayers;
    private int playingTime;
    private int minPlayingTime;
    private int maxPlayingTime;
    private int minAge;
    private boolean borrowed;

    public BoardGame(String id, String thumbnailUrl, String imageUrl, String name,
                     String description, int yearPublished, int minPlayers, int maxPlayers,
                     int playingTime, int minPlayingTime, int maxPlayingTime, int minAge) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.imageUrl = imageUrl;
        this.name = name;
        this.description = description;
        this.yearPublished = yearPublished;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.playingTime = playingTime;
        this.minPlayingTime = minPlayingTime;
        this.maxPlayingTime = maxPlayingTime;
        this.minAge = minAge;
        this.borrowed = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(int playingTime) {
        this.playingTime = playingTime;
    }

    public int getMinPlayingTime() {
        return minPlayingTime;
    }

    public void setMinPlayingTime(int minPlayingTime) {
        this.minPlayingTime = minPlayingTime;
    }

    public int getMaxPlayingTime() {
        return maxPlayingTime;
    }

    public void setMaxPlayingTime(int maxPlayingTime) {
        this.maxPlayingTime = maxPlayingTime;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + "\nName: " + this.name + "\nDescription: " + this.description +
                "\nImage URL: " + this.imageUrl + "\nBorrowed: " + this.borrowed + "\n";
    }
}