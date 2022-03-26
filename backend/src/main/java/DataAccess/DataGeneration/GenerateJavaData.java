package DataAccess.DataGeneration;

import Entities.BoardGame;
import Exceptions.DataAccessException;

public class GenerateJavaData {
    public static void main(String[] args) {
        try {
            InMemoryDB db = InMemoryDB.getInstance();
            for (BoardGame game: db.boardGameTable.values()) {
                System.out.println("gamesList.add(new BoardGame(\"" + game.getId() + "\", \"" + game.getThumbnailUrl() + "\", \"" + game.getImageUrl() + "\", \"" + game.getName() + "\", \"" +
                        game.getDescription() + "\", " + game.getYearPublished() + ", " + game.getMinPlayers() + ", " + game.getMaxPlayers() + ", " +
                        game.getPlayingTime() + ", " + game.getMinPlayingTime() + ", " + game.getMaxPlayingTime() + ", " + game.getMinAge() + ", \"" + game.getRulesURL() + "\"));");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }


    }
}
