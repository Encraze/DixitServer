package com.minasan.zenki.model.game;

import java.util.*;

import com.minasan.zenki.model.Card;
import com.minasan.zenki.model.Player;
import com.minasan.zenki.model.room.Room;

public class GameManager {
    private static final List<Card> defaultDeck = initDefaultDeck();

    private static Map<Long, Room> roomHolder = new HashMap<>();

    public static long createRoom(int starterId, int capacity) {
        Room room = new Room(capacity, starterId);
        long roomId = room.getId();
        roomHolder.put(roomId, room);
        return roomId;
    }

    public static Room getRoom(Long id) {
        return roomHolder.get(id);
    }

    public static Set<Long> getOpenRoomIds() {
        return roomHolder.keySet();
    }

    public static long begin(long roomId) {
        Room room = roomHolder.get(roomId);
        
        int votes = 1;
        Map<Integer, Player> players = new HashMap<>();
        for (Player player : room.getPlayers()) {
            player.reset();
            players.put(player.getId(), player);
        }
        if (players.size() > 7) {
            votes = 2;
        }
        long id = room.getId();
        Game game = new Game(players, defaultDeck, votes, id);
        roomHolder.get(roomId).setGame(game);
        room.setStatus("Game started.");
        return id;
    }

    private static List<Card> initDefaultDeck() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            cards.add(new Card(i));
        }
        return cards;
    }
    
    
}
