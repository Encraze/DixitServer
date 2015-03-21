import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Royd
 */
public class Room {
    private long id;
    private int capacity;
    private List<Player> players;
    private String status;
    private Game game;

    public Room(int capacity, int starterId) {
        id = Util.nextId();
        this.capacity = capacity;
        players = new ArrayList<Player>(capacity);
        players.add(new Player(starterId));
        status = "Waiting for players.";
    }

    public long getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
