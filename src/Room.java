import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Royd
 */
public class Room {
    private long id;
    private int capacity;
    private List<Integer> players;
    private String status;
    private Game game;

    public Room(int capacity, int starterId) {
        id = Util.nextId();
        this.capacity = capacity;
        players = new ArrayList<Integer>(capacity);
        players.add(starterId);
        status = "Waiting for players.";
    }
    
    public void join(int playerId) {
        players.add(playerId);
        if (players.size() == capacity) {
            start();
        }
    }

    public long modifyRoom(int starterId, RoomAction action, Integer... values) {
        if (starterId == players.get(0)) {
            return start();
        }
        return -1;
    }

    private long start() {
        Map<Integer, Player> playerMap = new HashMap<>();
        for (Integer id : players) {
            playerMap.put(id, new Player(id));
        }
        return GameManager.begin(playerMap, new ArrayList<Card>());
    }

    public List<Integer> getPlayers() {
        return players;
    }

    public Game getGame() {
        return game;
    }
}
