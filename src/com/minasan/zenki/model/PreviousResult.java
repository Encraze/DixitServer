package com.minasan.zenki.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.minasan.zenki.model.game.Game;

public class PreviousResult {
    int activePlayerId;
    Map<Integer, List<Integer>> votes;
    int activeCardId;
    Map<Integer, Integer> addedCards;
    Map<Integer, Integer> levelScore;
    
    public PreviousResult(Game game) {
        activePlayerId = game.getActivePlayer().getId();
        votes = new HashMap<>(game.getVotes());
        activeCardId = game.getActiveCard().getId();
        addedCards = new HashMap<>(game.getAddedCards());
        levelScore = new HashMap<>(game.getLevelScore());
    }
}
