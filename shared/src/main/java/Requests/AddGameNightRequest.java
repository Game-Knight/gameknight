package Requests;

import Entities.GameNight;

public class AddGameNightRequest {

    private GameNight gameNight;

    public AddGameNightRequest() {

    }

    public AddGameNightRequest(GameNight gameNight) {
        this.gameNight = gameNight;
    }

    public GameNight getGameNight() {
        return gameNight;
    }

    public void setGameNight(GameNight gameNight) {
        this.gameNight = gameNight;
    }
}
