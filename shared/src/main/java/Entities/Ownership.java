package Entities;

import java.io.Serializable;

public class Ownership implements Serializable {

    private String userId;
    private String boardGameId;

    public Ownership(String userId, String boardGameId) {
        this.userId = userId;
        this.boardGameId = boardGameId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBoardGameId() {
        return boardGameId;
    }

    public void setBoardGameId(String boardGameId) {
        this.boardGameId = boardGameId;
    }
}
