import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Royd
 */
public class Game {
    private long id;
    private int turner;
    Map<Integer, List<Integer>> votes = new HashMap<Integer, List<Integer>>();
    static final int MAX_CARDS_IN_HAND = 6;
    Player activePlayer;
    Card activeCard;
    GameAction state;
    Map<Integer, Player> players;
    Map<Integer, Integer> addedCards = new HashMap<Integer, Integer>();
    List<Card> deck;
    Map<Integer, Integer> score = new HashMap<Integer, Integer>();
    Map<Integer, Integer> levelScore = new HashMap<Integer, Integer>();
    List<Integer> tabledCards;
    PreviousResult prevRes;
    int votesPerTurn;

    Game(Map<Integer, Player> players, List<Card> deck, int votesPerTurn, long id) {
        this.id = id;
        this.players = players;
        this.deck = deck;
        state = GameAction.TURN;
        this.votesPerTurn = votesPerTurn;
        init();
    }

    private void init() {
        Collections.shuffle(deck);
        for (Player player : players.values()) {
            score.put(player.getId(), 0);
            levelScore.put(player.getId(), 0);
        }
        turner = Util.RND.nextInt(players.size());
        activePlayer = players.get(turner);
        activePlayer.setTurn(true);
        for (Player player : players.values()) {
            for (int i = 0; i < MAX_CARDS_IN_HAND; i++) {
                int pick = Util.RND.nextInt(deck.size());
                Card card = deck.get(pick);
                player.getCards().add(card);
                deck.remove(pick);
            }
        }
        System.out.println("Players holding cards");
        for (Player player : players.values()) {
            System.out.print(player.getId() + "    " + player.getCards() + "\n");
        }
    }

    public void nextTurn() {
        prevRes = new PreviousResult(this);
        addedCards.clear();
        votes.clear();
        players.get(turner).setTurn(false);
        if (turner == players.size() - 1) {
            turner = 0;
        } else {
            turner++;
        }
        activePlayer = players.get(turner);
        activePlayer.setTurn(true);
        for (Player player : players.values()) {
            int pick;
            if (deck.size() > 0) {
                pick = Util.RND.nextInt(deck.size());
                Card card = deck.get(pick);
                player.getCards().add(card);
                deck.remove(pick);
            }
        }
    }

    public long getId() {
        return id;
    }
}