import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Royd
 */
public class Bot {
    static Map<Integer, Player> players;
    static List<Card> cards;

    static {
        cards = new ArrayList<Card>();
        for (int i = 0; i < 100; i++) {
            cards.add(new Card(i));
        }
    }

    public Bot() {
        players = new HashMap<Integer, Player>();
    }

    public void play() throws Exception {
        long gameId = -1;
        if (players.size() > 3) {
            gameId = GameManager.newGame(players, cards);
        }
        Game game = GameManager.getGame(gameId);
        if (game != null) {
            while (game.state != Action.END) {
                Thread.sleep(200);
                for (Player player : players.values()) {
                    Response resp = RequestHandler.process(new Request(gameId, player.getId(), null, null, Action.REFRESH));
                    Action action = Action.REFRESH;
                    String comment = null;
                    int[] cards = null;
                    switch (resp.gameState) {
                        case TURN:
                            if (player.isTurn()) {
                                System.out.println("Player " + player.getId() + " makes a turn");
                                Card card = player.getCards().get(Util.RND.nextInt(player.getCards().size()));
                                cards = new int[]{card.getId()};
                                comment = "{" + card.getId() + "}";
                                action = Action.TURN;
                            }
                            break;
                        case ADD:
                            if (!player.isTurn()) {
                                Card card = player.getCards().get(Util.RND.nextInt(player.getCards().size()));
                                cards = new int[] {card.getId()};
                                action = Action.ADD;
                            }
                            break;
                        case VOTE:
                            if (!player.isTurn()) {
                                int choice = pickVote(game, player);
                                cards = new int[] {choice};
                                action = Action.VOTE;
                            }
                            break;
                        default: //NOP
                    }
                    RequestHandler.process(new Request(gameId, player.getId(), cards, comment, action));
                }
            }
        }
    }

    private int pickVote(Game game, Player player) {
        int choice = Util.RND.nextInt(game.tabledCards.size());
        if (!game.tabledCards.get(choice).equals(player.getAddedCard())) {
            return choice;
        } else {
            return pickVote(game, player);
        }
    }

    public static void main(String[] args) throws Exception {
        Bot bot = new Bot();
        for (int i = 0; i < 4; i++) {
            Player p = new Player(i);
            players.put(p.getId(), p);
        }
        bot.play();
    }
}