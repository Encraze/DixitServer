import java.util.List;

/**
 * @author Igor Royd
 */
public class RoomResponse {
    private Integer roomId;
    private String status;
    private Integer joinedPlayers;
    private Integer playersToWait;
    private List<Integer> openRooms;

    public RoomResponse(Integer roomId, String status, Integer joinedPlayers, Integer playersToWait, List<Integer> openRooms) {
        this.roomId = roomId;
        this.status = status;
        this.joinedPlayers = joinedPlayers;
        this.playersToWait = playersToWait;
        this.openRooms = openRooms;
    }

}
