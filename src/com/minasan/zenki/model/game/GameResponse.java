package com.minasan.zenki.model.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.minasan.zenki.model.PreviousResult;

public class GameResponse {
    int activePlayer;
    long gameId;
    GameAction gameState;
    Set<Integer> votedPlayerIds;
    Set<Integer> playerIds;
    Map<Integer, Integer> score = new HashMap<>();
    List<Integer> tabledCards;
    PreviousResult prevResult;

    public GameResponse(Game game) {
        activePlayer = game.activePlayer.getId();
        gameId = game.getId();
        gameState = game.getState();
        votedPlayerIds = game.votes.keySet();
        playerIds = game.players.keySet();
        score = game.score;
        tabledCards = game.tabledCards;
        prevResult = game.prevRes;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public long getGameId() {
        return gameId;
    }

    public GameAction getGameState() {
        return gameState;
    }

    public Set<Integer> getVotedPlayerIds() {
        return votedPlayerIds;
    }

    public Set<Integer> getPlayerIds() {
        return playerIds;
    }

    public Map<Integer, Integer> getScore() {
        return score;
    }

    public List<Integer> getTabledCards() {
        return tabledCards;
    }

    public PreviousResult getPrevResult() {
        return prevResult;
    }
}
