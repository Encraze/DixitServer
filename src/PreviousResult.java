import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Royd
 */
public class PreviousResult {
    int activePlayerId;
    Map<Integer, List<Integer>> votes;
    int activeCardId;
    Map<Integer, Integer> addedCards;
    Map<Integer, Integer> levelScore;
    
    public PreviousResult(Game game) {
        activePlayerId = game.activePlayer.getId();
        votes = new HashMap<Integer, List<Integer>>(game.votes);
        activeCardId = game.activeCard.getId();
        addedCards = new HashMap<Integer, Integer>(game.addedCards);
        levelScore = new HashMap<Integer, Integer>(game.levelScore);
    }
}
