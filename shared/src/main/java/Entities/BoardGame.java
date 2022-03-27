package Entities;

import java.io.Serializable;

public class BoardGame implements Serializable {

    private String bggId;
    private String bgaId;
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
    private String officialURL;
    private String rulesURL;
    private String primaryPublisher;
    private String primaryDesigner;
    private String upc;
    private boolean borrowed;

    public BoardGame(String bggId, String thumbnailUrl, String imageUrl, String name,
                     String description, int yearPublished, int minPlayers, int maxPlayers,
                     int playingTime, int minPlayingTime, int maxPlayingTime, int minAge) {
        this.bggId = bggId;
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

    public BoardGame(String bggId, String thumbnailUrl, String imageUrl, String name,
                     String description, int yearPublished, int minPlayers, int maxPlayers,
                     int playingTime, int minPlayingTime, int maxPlayingTime, int minAge, String rulesURL) {
        this.bggId = bggId;
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
        this.rulesURL = rulesURL;
    }

    public BoardGame(String bggId, String bgaId, String thumbnailUrl, String imageUrl, String name,
                     String description, int yearPublished, int minPlayers, int maxPlayers,
                     int playingTime, int minPlayingTime, int maxPlayingTime, int minAge, String primaryPublisher, String primaryDesigner, String officialURL, String rulesURL, String upc) {
        this.bggId = bggId;
        this.bgaId = bgaId;
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
        this.primaryPublisher = primaryPublisher;
        this.primaryDesigner = primaryDesigner;
        this.officialURL = officialURL;
        this.rulesURL = rulesURL;
        this.upc = upc;
        this.borrowed = false;
    }

    public String getBggId() {
        return bggId;
    }

    public void setBggId(String bggId) {
        this.bggId = bggId;
    }

    public String getBgaId() {
        return bgaId;
    }

    public void setBgaId(String bgaId) {
        this.bgaId = bgaId;
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

    public String getOfficialURL() {
        return officialURL;
    }

    public void setOfficialURL(String officialURL) {
        this.officialURL = officialURL;
    }

    public String getRulesURL() {
        return rulesURL;
    }

    public void setRulesURL(String rulesURL) {
        this.rulesURL = rulesURL;
    }

    public String getPrimaryPublisher() {
        return primaryPublisher;
    }

    public void setPrimaryPublisher(String primaryPublisher) {
        this.primaryPublisher = primaryPublisher;
    }

    public String getPrimaryDesigner() {
        return primaryDesigner;
    }

    public void setPrimaryDesigner(String primaryDesigner) {
        this.primaryDesigner = primaryDesigner;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        return "Id: " + this.bggId + "\nName: " + this.name + "\nDescription: " + this.description +
                "\nImage URL: " + this.imageUrl + "\nBorrowed: " + this.borrowed + "\n";
    }
}