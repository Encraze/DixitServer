package com.minasan.zenki.model.room;

import java.util.ArrayList;
import java.util.List;

import com.minasan.zenki.model.BotPlayer;
import com.minasan.zenki.model.Player;
import com.minasan.zenki.model.Util;
import com.minasan.zenki.model.game.Game;

public class Room {
    private long id;
    private int capacity;
    private List<Player> players;
    private String status;
    private Game game;

    public Room(int capacity, int starterId) {
        id = Util.nextId();
        this.capacity = capacity;
        players = new ArrayList<>(capacity);
        if (starterId < 0) {
            players.add(new BotPlayer(starterId));
        } else {
            players.add(new Player(starterId));
        }
        status = "Waiting for players.";
    }

    public long getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
