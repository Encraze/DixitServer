package com.minasan.zenki.model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    protected int id;
    private List<Card> cards;
    private int addedCard;
    private boolean turn;
    private boolean added = false;
    private boolean voted = false;
    
    public Player(int id) {
        this.id = id;
        cards = new ArrayList<>();
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getId() {
        return id;
    }

    public int getAddedCard() {
        return addedCard;
    }

    public void setAddedCard(int addedCard) {
        this.addedCard = addedCard;
    }
    
    public boolean isBot() {
        return false;
    }
    
    public boolean isAdded() {
        return added;
    }
    
    public boolean isVoted() {
        return voted;
    }
    
    public void reset() {
        added = false;
        voted = false;
    }
}
