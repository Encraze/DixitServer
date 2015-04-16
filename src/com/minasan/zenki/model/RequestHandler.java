package com.minasan.zenki.model;

import com.minasan.zenki.model.game.GameManager;
import com.minasan.zenki.model.game.GameRequest;
import com.minasan.zenki.model.game.GameResponse;
import com.minasan.zenki.model.room.RoomRequest;
import com.minasan.zenki.model.room.RoomResponse;

public class RequestHandler {
    public static GameResponse processGame(GameRequest request) {
        long roomId = request.getRoomId();
        if (playerNotBot(roomId, request.getPlayerId())) {
            Bot.play(roomId);
        }
        return request.getAction().process(request);
    }

    private static boolean playerNotBot(long roomId, int playerId) {
        return !GameManager.getRoom(roomId).getGame().getPlayers().get(playerId).isBot();
    }

    public static RoomResponse processRoom(RoomRequest request) {
        return request.getAction().process(request);
    } 
}
