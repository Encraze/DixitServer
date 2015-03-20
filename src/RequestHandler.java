/**
 * @author Igor Royd
 */
public class RequestHandler {
    public static GameResponse processGame(GameRequest request) {
        return request.getAction().process(request);
    }
    
    public static RoomResponse processRoom(RoomRequest request) {
        return request.getAction().process(request);
    } 
}
