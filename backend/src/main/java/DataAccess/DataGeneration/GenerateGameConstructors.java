package DataAccess.DataGeneration;

import Entities.BoardGame;
import Exceptions.DataAccessException;

public class GenerateGameConstructors {
    public static void main(String[] args) {
        try {
            APIDB db = APIDB.getInstance();
            for (BoardGame game: db.boardGameTable.values()) {
                System.out.println("gamesList.add(new BoardGame(" + q(game.getBggId()) + q(game.getBgaId()) + q(game.getThumbnailUrl()) +  q(game.getImageUrl()) + q(game.getName()) +
                        q(game.getDescription()) + game.getYearPublished() + ", " + game.getMinPlayers() + ", " + game.getMaxPlayers() + ", " +
                        game.getPlayingTime() + ", " + game.getMinPlayingTime() + ", " + game.getMaxPlayingTime() + ", " + game.getMinAge() + ", " + q(game.getPrimaryPublisher()) + q(game.getPrimaryDesigner()) + q(game.getOfficialURL()) + q(game.getRulesURL()) + q2(game.getUpc()) + "));");
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    private static String q(String input) {
        return (input != null) ? '"' + input + "\", " : "null, ";
    }

    private static String q2(String input) {
        return (input != null) ? '"' + input + '"' : "null";
    }
}
