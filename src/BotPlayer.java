/**
 * Created by Encraze on 21.03.2015.
 */
public class BotPlayer extends Player {

    public BotPlayer(int id) {
        super(id);
        if (this.id > 0) {
            this.id *= -1;
        }
    }
}
