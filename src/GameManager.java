import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Royd
 */
public class GameManager {
    private static final List<Card> defaultDeck = initDefaultDeck();

    private static Map<Long, Room> roomHolder = new HashMap<Long, Room>();

    public static long begin(long roomId, List<Card> deck) {
        Room room = roomHolder.get(roomId);
        
        int votes = 1;
        List<Integer> players = room.getPlayers();
        if (players.size() > 7) {
            votes = 2;
        }
        Game game = new Game(players, deck, votes, Util.nextId());
        roomHolder.put(game.getId(), game);
        return game.getId();
    }

    public static long begin(long roomId) {
        return begin(roomId, defaultDeck);
    }

    private static List<Card> initDefaultDeck() {
        List<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 100; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }
    
    
}
