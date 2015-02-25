/**
 * @author Igor Royd
 */
public class Request {
    long gameId;
    int playerId;
    int[] cards;
    String comment;
    Action action;
    
    public Request(long gameId, int playerId, int[] cards, String comment, Action action) {
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

    public int[] getCards() {
        return cards;
    }

    public void setCards(int[] cards) {
        this.cards = cards;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
