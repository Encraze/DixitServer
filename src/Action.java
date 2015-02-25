import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Royd
 */
public enum Action {
    TURN {
        @Override
        public Response process(Request request) {
            Game game = GameManager.getGame(request.getGameId());
            if (verifyRequest(request)) {
                Player player = game.players.get(request.getPlayerId());
                Card card = null;
                for (Card checkedCard : player.getCards()) {
                    if (checkedCard.getId() == request.getCards()[0]) {
                        card = checkedCard;
                        break;
                    }
                }
                if (card == null) {
                    System.err.println("You're trying to provide a card you don't own.");
                    return new Response(game);
                }
                if (player.isTurn()) {
                    game.activeCard = card;
                    game.state = Action.ADD;
                    player.getCards().remove(card);
                    System.out.println("Player " + player.getId() + " draws card " + card.getId() + ". Comment: " + request.getComment());
                } else {
                    System.err.println("It's not player's turn");
                }
            } else {
                System.err.println("Incorrect request on " + request.getAction() + ". Current action awaited: " + game.state);
            }
            return new Response(game);
        }
    },
    
    ADD {
        @Override
        public Response process(Request request) {
            Game game = GameManager.getGame(request.getGameId());
            if (verifyRequest(request)) {
                Player player = game.players.get(request.getPlayerId());
                Map<Integer, Integer> addedCards = game.addedCards;
                if (!player.isTurn()) {
                    Card card = null;
                    for (Card checkedCard : player.getCards()) {
                        if (checkedCard.getId() == request.getCards()[0]) {
                            card = checkedCard;
                            break;
                        }
                    }
                    if (card == null) {
                        System.err.println("Player is trying to cheat. Putting a card that's not in hand");
                        return new Response(game);
                    }
                    addedCards.put(card.getId(), player.getId());
                    player.setAddedCard(card.getId());
                    player.getCards().remove(card);
                    System.out.println("Player " + player.getId() + " adds card " + card.getId());
                } else {
                    System.err.println("Player has made a turn and it's not the time to add cards. Let others play");
                }
                if (addedCards.size() == game.players.size() - 1) {
                    List<Integer> tabledCards = new ArrayList<Integer>();
                    tabledCards.addAll(addedCards.keySet());
                    tabledCards.add(game.activeCard.getId());
                    Collections.shuffle(tabledCards);
                    game.tabledCards = tabledCards;
                    game.state = Action.VOTE;
                }
            } else {
                System.err.println("It's time to add cards to the heap. No other action permitted.");
            }
            return new Response(game);
        }
    },
    
    VOTE {
        @Override
        public Response process(Request request) {
            Game game = GameManager.getGame(request.getGameId());
            if (verifyRequest(request)) {
                Player player = game.players.get(request.getPlayerId());
                if (!player.isTurn()) {
                    int playerId = game.players.get(request.getPlayerId()).getId();
                    Map<Integer, List<Integer>> votes = game.votes;
                    if (votes.get(playerId) == null) {
                        votes.put(playerId, new ArrayList<Integer>());
                    }
                    for (int i = 0; i < game.votesPerTurn; i++) {
                        int card = request.getCards()[i];
                        if (card < game.tabledCards.size()) {
                            int votedCard = game.tabledCards.get(card); 
                            votes.get(playerId).add(votedCard);
                            System.out.println("Player " + request.getPlayerId() + " votes for " + votedCard);
                        } else {
                            System.err.println("Player " + request.getPlayerId() + " trying to vote for illegal card " + card);
                        }
                    }
                } else {
                    System.err.println("The player making a turn cannot vote during the round.");
                }
                if (game.votes.size() == game.players.size() - 1) {
                    game.state = Action.CALC;
                    return calc(game);
                }
            }
            return new Response(game);
        }
    },

    CALC,

    REFRESH {
        @Override
        public Response process(Request request) {
            Game game = GameManager.getGame(request.getGameId());
            if (game.players.containsKey(request.getPlayerId())) {
                return new Response(game);
            }
            return null;
        }
    },

    END;

    public Response process(Request request) {
        return null;
    }

    Response calc(Game game) {
        if (game.state == Action.CALC) {
            Map<Integer, Integer> levelScore = game.levelScore;
            for (Map.Entry<Integer, Integer> ls : levelScore.entrySet()) {
                ls.setValue(0);
            }
            int correct = 0;
            Card activeCard = game.activeCard;
            Map<Integer, List<Integer>> votes = game.votes;
            for (List<Integer> list : votes.values()) {
                if (list.contains(activeCard.getId())) {
                    correct++;
                }
            }
            Map<Integer, Player> players = game.players;
            Map<Integer, Integer> score = game.score;
            if (correct == 0 || correct == players.size() - 1) {
                for (Player player : players.values()) {
                    if (!player.isTurn()) {
                        levelScore.put(player.getId(), 2);
                    }
                }
            } else {
                Player activePlayer = game.activePlayer;
                score.put(activePlayer.getId(), score.get(activePlayer.getId()) + 3);
                for (Player player : players.values()) {
                    if (player != activePlayer) {
                        List<Integer> playerVotes = votes.get(player.getId());
                        for (int cardId : playerVotes) {
                            if (cardId == activeCard.getId()) {
                                score.put(player.getId(), score.get(player.getId()) + 3);
                            } else {
                                int winnerId = game.addedCards.get(cardId);
                                if (levelScore.get(winnerId) < 3) {
                                    levelScore.put(winnerId, levelScore.get(winnerId) + 1);
                                }
                            }
                        }
                    }
                }
            }
            for (Map.Entry<Integer, Integer> e : levelScore.entrySet()) {
                score.put(e.getKey(), score.get(e.getKey()) + levelScore.get(e.getKey()));
            }
            System.out.println("Score: " + score);
            for (Map.Entry<Integer, Integer> sc : score.entrySet()) {
                if (sc.getValue() >= 30) {
                    System.out.println("Player: " + sc.getKey() + " Wins!");
                    game.state = Action.END;
                    return new Response(game);
                }
            }
            game.nextTurn();
            game.state = Action.TURN;
            return new Response(game);
        } else {
            System.err.println("Inconsistent state");
            return null;
        }
    }

    boolean verifyRequest(Request request) {
        Game game = GameManager.getGame(request.getGameId());
        if (game.players.containsKey(request.getPlayerId()) && game.state == request.getAction()) {
            return true;
        } else {
            return false;
        }
    }
}
