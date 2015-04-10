package com.minasan.zenki.model.room;

import java.util.Set;

import com.minasan.zenki.model.game.GameManager;

public class RoomResponse {
    private Long roomId;
    private String status;
    private Integer playersInTheRoom;
    private Integer playersToWait;
    private Set<Long> openRooms;

    public RoomResponse(Room room) {
        roomId = room.getId();
        playersInTheRoom = room.getPlayers().size();
        playersToWait = room.getCapacity() - playersInTheRoom;
        status = room.getStatus();
        openRooms = GameManager.getOpenRoomIds();
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPlayersInTheRoom() {
        return playersInTheRoom;
    }

    public Integer getPlayersToWait() {
        return playersToWait;
    }

    public Set<Long> getOpenRooms() {
        return openRooms;
    }
}
