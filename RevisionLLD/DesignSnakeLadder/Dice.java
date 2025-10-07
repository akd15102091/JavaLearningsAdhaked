package DesignSnakeLadder;

import java.util.Random;

public class Dice {
    private int min, max;
    private Random random = new Random();
    public Dice(int min, int max) { 
        this.min = min; 
        this.max = max; 
    }
    public int roll() { 
        return random.nextInt((max - min) + 1) + min; 
    }
}
