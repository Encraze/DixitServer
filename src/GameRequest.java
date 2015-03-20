/**
 * @author Igor Royd
 */
public class GameRequest {
    private Long gameId;
    private Integer playerId;
    private Integer[] cards;
    private String comment;
    private GameAction action;
    
    public GameRequest(long gameId, int playerId, Integer[] cards, String comment, GameAction action) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.cards = cards;
        this.comment = comment;
        this.action = action;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Integer[] getCards() {
        return cards;
    }

    public void setCards(Integer[] cards) {
        this.cards = cards;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public GameAction getAction() {
        return action;
    }

    public void setAction(GameAction action) {
        this.action = action;
    }
}
