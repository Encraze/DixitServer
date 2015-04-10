package com.minasan.zenki.model;

public class Card {

    private int id;
    
    public Card(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }
    
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        return id == ((Card)o).id;
    }
    
    public int hashCode() {
        return 7 * id;
    }
    
    public String toString() {
        return "["+id+"]";
    }
}
