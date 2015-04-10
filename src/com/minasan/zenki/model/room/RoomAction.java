package com.minasan.zenki.model.room;

import java.util.List;

import com.minasan.zenki.model.BotPlayer;
import com.minasan.zenki.model.Player;
import com.minasan.zenki.model.Util;
import com.minasan.zenki.model.game.GameManager;

public enum RoomAction {
    CREATE {
        @Override
        public RoomResponse process(RoomRequest request) {
            long id = GameManager.createRoom(request.getPlayerId(), request.getRequestValue());
            return new RoomResponse(GameManager.getRoom(id));
        }
    },
    CHANGE_CAPACITY {
        @Override
        public RoomResponse process(RoomRequest request) {
            Room room = GameManager.getRoom(request.getRoomId());
            if (room.getPlayers().get(0).getId() == request.getPlayerId() && request.getRequestValue() >= room.getPlayers().size()) {
                room.setCapacity(request.getRequestValue());
            } else {
                room.setStatus("ERROR: Change capacity unavailable.");
            }
            conditionalStartGame(room, room.getPlayers());
            return new RoomResponse(room);
        }
    },
    ADD_BOT {
        @Override
        public RoomResponse process(RoomRequest request) {
            Room room = GameManager.getRoom(request.getRoomId());
            List<Player> players = room.getPlayers();
            if (players.get(0).getId() == request.getPlayerId() && players.size() < room.getCapacity()) {
                room.getPlayers().add(new BotPlayer(Util.RND.nextInt()));
            }
            conditionalStartGame(room, players);
            return new RoomResponse(room);
        }
    },
    JOIN {
        @Override
        public RoomResponse process(RoomRequest request) {
            Room room = GameManager.getRoom(request.getRoomId());
            List<Player> players =  room.getPlayers();
            if (players.size() < room.getCapacity()) {
                players.add(new Player(request.getPlayerId()));
            }
            conditionalStartGame(room, players);
            return new RoomResponse(room);
        }
    },
    REFRESH {
        @Override
        public RoomResponse process(RoomRequest request) {
            return new RoomResponse(GameManager.getRoom(request.getRoomId()));
        }
    };

    private static void conditionalStartGame(Room room, List<Player> players) {
        if (players.size() == room.getCapacity()) {
            GameManager.begin(room.getId());
        }
    }

    public RoomResponse process(RoomRequest request) {
        return null;
    }
}
