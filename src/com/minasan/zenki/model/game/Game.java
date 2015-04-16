package com.minasan.zenki.model.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.minasan.zenki.model.Card;
import com.minasan.zenki.model.Player;
import com.minasan.zenki.model.PreviousResult;
import com.minasan.zenki.model.Util;

public class Game {
    static final int MAX_CARDS_IN_HAND = 6;
    
    private long id;
    private int turner;
    Map<Integer, List<Integer>> votes = new HashMap<>();
    Player activePlayer;
    Card activeCard;
    private GameAction state;
    Map<Integer, Player> players;
    List<Integer> playerIds;
    Map<Integer, Integer> addedCards = new HashMap<>();
    List<Card> deck;
    Map<Integer, Integer> score = new HashMap<>();
    Map<Integer, Integer> levelScore = new HashMap<>();
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
        playerIds = new ArrayList<>(players.size());
        for (Player player : players.values()) {
            playerIds.add(player.getId());
        }
        activePlayer = players.get(playerIds.get(turner));
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
        players.get(playerIds.get(turner)).setTurn(false);
        if (turner == players.size() - 1) {
            turner = 0;
        } else {
            turner++;
        }
        activePlayer = players.get(playerIds.get(turner));
        activePlayer.setTurn(true);
        for (Player player : players.values()) {
            player.reset();
            int pick;
            if (deck.size() > 0) {
                pick = Util.RND.nextInt(deck.size());
                Card card = deck.get(pick);
                System.out.print("{" + player.getId() + "} picks [" + card.getId() + "] || ");
                player.getCards().add(card);
                deck.remove(pick);
            }
        }
        System.out.println();
    }

    public long getId() {
        return id;
    }

    public int getTurner() {
        return turner;
    }

    public Map<Integer, List<Integer>> getVotes() {
        return votes;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Card getActiveCard() {
        return activeCard;
    }

    public GameAction getState() {
        return state;
    }

    public void setState(GameAction state) {
        System.out.print("\n" + state.name() + ": ");
        this.state = state;
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public Map<Integer, Integer> getAddedCards() {
        return addedCards;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public Map<Integer, Integer> getScore() {
        return score;
    }

    public Map<Integer, Integer> getLevelScore() {
        return levelScore;
    }

    public List<Integer> getTabledCards() {
        return tabledCards;
    }

    public PreviousResult getPrevRes() {
        return prevRes;
    }

    public int getVotesPerTurn() {
        return votesPerTurn;
    }
}