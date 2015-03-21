import java.util.List;

/**
 * @author Igor Royd
 */
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
            if (room.getPlayers().get(0).getId() == request.getPlayerId()) {
                room.setCapacity(request.getRequestValue());
            }
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
            if (players.size() == room.getCapacity()) {
                GameManager.begin(room.getId());
            }
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
            if (players.size() == room.getCapacity()) {
                GameManager.begin(room.getId());
            }
            return new RoomResponse(room);
        }
    },
    REFRESH {
        @Override
        public RoomResponse process(RoomRequest request) {
            return new RoomResponse(GameManager.getRoom(request.getRoomId()));
        }
    };

    public RoomResponse process(RoomRequest request) {
        return null;
    }
}
