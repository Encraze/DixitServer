/**
 * @author Igor Royd
 */
//TODO: IMPLEMENTATION
public enum RoomAction {
    CREATE {
        @Override
        public RoomResponse process(RoomRequest request) {
            return null;
        }
    },
    CHANGE_CAPACITY {
        @Override
        public RoomResponse process(RoomRequest request) {
            return null;
        }
    },
    ADD_BOT {
        @Override
        public RoomResponse process(RoomRequest request) {
            return null;
        }
    },
    START {
        @Override
        public RoomResponse process(RoomRequest request) {
            return null;
        }
    },
    REFRESH {
        @Override
        public RoomResponse process(RoomRequest request) {
            return null;
        }
    };

    public RoomResponse process(RoomRequest request) {
        return null;
    }
}
