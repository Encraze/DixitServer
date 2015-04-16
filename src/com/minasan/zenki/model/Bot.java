package com.minasan.zenki.model;

import java.util.List;
import java.util.Map;

import com.minasan.zenki.model.game.Game;
import com.minasan.zenki.model.game.GameAction;
import com.minasan.zenki.model.game.GameManager;
import com.minasan.zenki.model.game.GameRequest;
import com.minasan.zenki.model.game.GameResponse;
import com.minasan.zenki.model.room.Room;
import com.minasan.zenki.model.room.RoomAction;
import com.minasan.zenki.model.room.RoomRequest;
import com.minasan.zenki.model.room.RoomResponse;

public class Bot {
    public static void play(Long roomId) {
        Room room = GameManager.getRoom(roomId);
        Game game = room.getGame();
        Map<Integer, Player> players = game.getPlayers();
        if (game.getState() != GameAction.END) {
            try {
                Thread.sleep(200);
            } catch (Exception e) { /*NOP*/ }
            for (Player player : players.values()) {
                if (player.isBot()) {
                    GameResponse gameResp = RequestHandler.processGame(new GameRequest(roomId, player.getId(), null, null, GameAction.REFRESH));
                    GameAction action = GameAction.REFRESH;
                    String comment = null;
                    Integer[] cards = null;
                    if (gameResp.getGameState() == GameAction.REFRESH) {
                        continue;
                    }
                    switch (gameResp.getGameState()) {
                        case TURN:
                            if (player.isTurn()) {
                                Card card = player.getCards().get(Util.RND.nextInt(player.getCards().size()));
                                cards = new Integer[]{card.getId()};
                                comment = "{" + card.getId() + "}";
                                action = GameAction.TURN;
                            }
                            break;
                        case ADD:
                            if (!player.isTurn() && !player.isAdded()) {
                                Card card = player.getCards().get(Util.RND.nextInt(player.getCards().size()));
                                cards = new Integer[]{card.getId()};
                                action = GameAction.ADD;
                            }
                            break;
                        case VOTE:
                            if (!player.isTurn() && !player.isVoted()) {
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

    private static int pickVote(Game game, Player player) {
        final List<Integer> tabledCards = game.getTabledCards();
        int choice = Util.RND.nextInt(tabledCards.size());
        if (!tabledCards.get(choice).equals(player.getAddedCard())) {
            return choice;
        } else {
            return pickVote(game, player);
        }
    }

    public static void main(String[] args) throws Exception {
        RoomRequest req = new RoomRequest(-1, 100L, RoomAction.CREATE, 3);
        RoomResponse resp = RequestHandler.processRoom(req);
        Long roomId = resp.getRoomId();
        RoomResponse botResp;
        do {
            RoomRequest botReq = new RoomRequest(-1, resp.getRoomId(), RoomAction.ADD_BOT, null);
            botResp = RequestHandler.processRoom(botReq);
        } while (botResp.getPlayersToWait() != 0);
        while (GameManager.getRoom(roomId).getGame().getState() != GameAction.END) {
            play(roomId);
        }
    }
}