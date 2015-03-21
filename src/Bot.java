import java.util.Map;

/**
 * @author Igor Royd
 */
public class Bot {

    public void play(Long roomId) throws Exception {
        Room room = GameManager.getRoom(roomId);
        Game game = room.getGame();
        Map<Integer, Player> players = game.players;
        if (game != null) {
            while (game.state != GameAction.END) {
                Thread.sleep(200);
                for (Player player : players.values()) {
                    if (player instanceof BotPlayer) {
                        GameResponse resp = RequestHandler.processGame(new GameRequest(roomId, player.getId(), null, null, GameAction.REFRESH));
                        GameAction action = GameAction.REFRESH;
                        String comment = null;
                        Integer[] cards = null;
                        switch (resp.gameState) {
                            case TURN:
                                if (player.isTurn()) {
                                    System.out.println("Player " + player.getId() + " makes a turn");
                                    Card card = player.getCards().get(Util.RND.nextInt(player.getCards().size()));
                                    cards = new Integer[]{card.getId()};
                                    comment = "{" + card.getId() + "}";
                                    action = GameAction.TURN;
                                }
                                break;
                            case ADD:
                                if (!player.isTurn()) {
                                    Card card = player.getCards().get(Util.RND.nextInt(player.getCards().size()));
                                    cards = new Integer[]{card.getId()};
                                    action = GameAction.ADD;
                                }
                                break;
                            case VOTE:
                                if (!player.isTurn()) {
                                    int choice = pickVote(game, player);
                                    cards = new Integer[]{choice};
                                    action = GameAction.VOTE;
                                }
                                break;
                            default: //NOP
                        }
                        RequestHandler.processGame(new GameRequest(roomId, player.getId(), cards, comment, action));
                    }
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
        RoomRequest req = new RoomRequest(-1, 100L, RoomAction.CREATE, 6);
        RoomResponse resp = RequestHandler.processRoom(req);
        Long roomId = resp.getRoomId();

        RoomResponse botResp;
        do {
            RoomRequest botReq = new RoomRequest(-1, resp.getRoomId(), RoomAction.ADD_BOT, 100);
            botResp = RequestHandler.processRoom(botReq);
        } while (botResp.getPlayersToWait() != 0);

        bot.play(roomId);
    }
}