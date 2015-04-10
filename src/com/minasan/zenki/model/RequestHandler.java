package com.minasan.zenki.model;

import com.minasan.zenki.model.game.GameRequest;
import com.minasan.zenki.model.game.GameResponse;
import com.minasan.zenki.model.room.RoomRequest;
import com.minasan.zenki.model.room.RoomResponse;

public class RequestHandler {
    public static GameResponse processGame(GameRequest request) {
        return request.getAction().process(request);
    }
    
    public static RoomResponse processRoom(RoomRequest request) {
        return request.getAction().process(request);
    } 
}
