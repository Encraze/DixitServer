import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Royd
 */
public class GameManager {
    private static long counter;
    private static Map<Long, Game> gameHolder = new HashMap<Long, Game>();
    
    public static long next() {
        return counter++;
    }
    
    public static Game getGame(long id) {
        return gameHolder.get(id);
    }

    public static long newGame(Map<Integer, Player> players, List<Card> deck) {
        int votes = 1;
        if (players.size() > 7) {
            votes = 2;
        }
        Game game = new Game(players, deck, votes, next());
        gameHolder.put(game.getId(), game);
        return game.getId();
    }
}
