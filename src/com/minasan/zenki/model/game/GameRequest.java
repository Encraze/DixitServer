package com.minasan.zenki.model.game;

public class GameRequest {
    private Long roomId;
    private Integer playerId;
    private Integer[] cards;
    private String comment;
    private GameAction action;
    
    public GameRequest(long roomId, int playerId, Integer[] cards, String comment, GameAction action) {
        this.roomId = roomId;
        this.playerId = playerId;
        this.cards = cards;
        this.comment = comment;
        this.action = action;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Integer[] getCards() {
        return cards;
    }

    public void setCards(Integer[] cards) {
        this.cards = cards;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public GameAction getAction() {
        return action;
    }

    public void setAction(GameAction action) {
        this.action = action;
    }
}
