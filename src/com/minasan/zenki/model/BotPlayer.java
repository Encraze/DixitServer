package com.minasan.zenki.model;

public class BotPlayer extends Player {

    public BotPlayer(int id) {
        super(id);
        if (this.id > 0) {
            this.id *= -1;
        }
    }
}
