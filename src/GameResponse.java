import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Igor Royd
 */
public class GameResponse {
    int activePlayer;
    long gameId;
    GameAction gameState;
    Set<Integer> votedPlayerIds;
    Set<Integer> playerIds;
    Map<Integer, Integer> score = new HashMap<Integer, Integer>();
    List<Integer> tabledCards;
    PreviousResult prevResult;

    public GameResponse(Game game) {
        activePlayer = game.activePlayer.getId();
        gameId = game.getId();
        gameState = game.state;
        votedPlayerIds = game.votes.keySet();
        playerIds = game.players.keySet();
        score = game.score;
        tabledCards = game.tabledCards;
        prevResult = game.prevRes;
    }
}
