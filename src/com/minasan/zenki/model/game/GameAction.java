package com.minasan.zenki.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.minasan.zenki.model.Card;
import com.minasan.zenki.model.Player;

public enum GameAction {
    TURN {
        @Override
        public GameResponse process(GameRequest request) {
            Game game = GameManager.getRoom(request.getRoomId()).getGame();
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
                    return new GameResponse(game);
                }
                if (player.isTurn()) {
                    game.activeCard = card;
                    System.out.print("\nP: " + player.getId() + " draws [" + card.getId() + "]. Comment: " + request.getComment());
                    player.getCards().remove(card);
                    game.setState(GameAction.ADD);
                } else {
                    System.err.println("It's not player's turn");
                }
            } else {
                System.err.println("Incorrect request on " + request.getAction() + ". Current action awaited: " + game.getState());
            }
            return new GameResponse(game);
        }
    },
    
    ADD {
        @Override
        public GameResponse process(GameRequest request) {
            Game game = GameManager.getRoom(request.getRoomId()).getGame();
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
                        return new GameResponse(game);
                    }
                    addedCards.put(card.getId(), player.getId());
                    player.setAddedCard(card.getId());
                    player.getCards().remove(card);
                    System.out.print("{" + player.getId() + "} -> [" + card.getId() + "] || ");
                } else {
                    System.err.println("Player has made a turn and it's not the time to add cards. Let others play");
                }
                if (addedCards.size() == game.players.size() - 1) {
                    List<Integer> tabledCards = new ArrayList<Integer>();
                    tabledCards.addAll(addedCards.keySet());
                    tabledCards.add(game.activeCard.getId());
                    Collections.shuffle(tabledCards);
                    game.tabledCards = tabledCards;
                    game.setState(GameAction.VOTE);
                }
            } else {
                System.err.println("It's time to add cards to the heap. No other action permitted.");
            }
            return new GameResponse(game);
        }
    },
    
    VOTE {
        @Override
        public GameResponse process(GameRequest request) {
            Game game = GameManager.getRoom(request.getRoomId()).getGame();
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
                            System.out.print("{" + request.getPlayerId() + "} -> [" + votedCard + "] || ");
                        } else {
                            System.err.println("Player " + request.getPlayerId() + " trying to vote for illegal card " + card);
                        }
                    }
                } else {
                    System.err.println("The player making a turn cannot vote during the round.");
                }
                if (game.votes.size() == game.players.size() - 1) {
                    game.setState(GameAction.CALC);
                    return calc(game);
                }
            }
            return new GameResponse(game);
        }
    },

    CALC,

    REFRESH {
        @Override
        public GameResponse process(GameRequest request) {
            Game game = GameManager.getRoom(request.getRoomId()).getGame();
            if (game.players.containsKey(request.getPlayerId())) {
                return new GameResponse(game);
            }
            return null;
        }
    },

    END;

    public GameResponse process(GameRequest request) {
        return null;
    }

    GameResponse calc(Game game) {
        if (game.getState() == GameAction.CALC) {
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
            System.out.println(" " + score);
            for (Map.Entry<Integer, Integer> sc : score.entrySet()) {
                if (sc.getValue() >= 30) {
                    System.out.println("Player: " + sc.getKey() + " Wins!");
                    game.setState(GameAction.END);
                    return new GameResponse(game);
                }
            }
            game.nextTurn();
            game.setState(GameAction.TURN);
            return new GameResponse(game);
        } else {
            System.err.println("Inconsistent state");
            return null;
        }
    }

    boolean verifyRequest(GameRequest request) {
        Game game = GameManager.getRoom(request.getRoomId()).getGame();
        if (game.players.containsKey(request.getPlayerId()) && game.getState() == request.getAction()) {
            return true;
        } else {
            return false;
        }
    }
}
