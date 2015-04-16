package com.minasan.zenki.model;

import java.util.Random;

public class Util {
    public static final Random RND = new Random();
    private static long counter;
    
    public static long nextId() {
        return ++counter;
    }
}
