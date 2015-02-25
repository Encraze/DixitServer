import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Royd
 */
public class Player {
    private int id;
    private List<Card> cards;
    private int addedCard;
    private boolean turn;
    
    public Player(int id) {
        this.id = id;
        cards = new ArrayList<Card>();
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getId() {
        return id;
    }

    public int getAddedCard() {
        return addedCard;
    }

    public void setAddedCard(int addedCard) {
        this.addedCard = addedCard;
    }
}
