/**
 * @author Igor Royd
 */
public class RoomRequest {
    private Integer playerId;
    private Long roomId;
    private RoomAction action;
    private Integer requestValue;

    public RoomRequest(Integer playerId, Long roomId, RoomAction action, Integer requestValue) {
        this.playerId = playerId;
        this.roomId = roomId;
        this.action = action;
        this.requestValue = requestValue;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public RoomAction getAction() {
        return action;
    }

    public void setAction(RoomAction action) {
        this.action = action;
    }

    public Integer getRequestValue() {
        return requestValue;
    }

    public void setRequestValue(Integer requestValue) {
        this.requestValue = requestValue;
    }
}
