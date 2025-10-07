package DesignCricInfo;

import DesignCricInfo.enums.BallType;
import DesignCricInfo.enums.DeliveryType;

@SuppressWarnings("unused")
public class Ball {
    private final Player batsman;
    private final Player bowler;
    private final int runs;
    private final boolean isWicket;
    private final BallType type;

    public Ball(Player batsman, Player bowler, int runs, boolean isWicket, BallType type) {
        this.batsman = batsman;
        this.bowler = bowler;
        this.runs = runs;
        this.isWicket = isWicket;
        this.type = type;
    }

    public Player getBatsman() { return batsman; }
    public Player getBowler() { return bowler; }
    public int getRuns() { return runs; }
    public boolean isWicket() { return isWicket; }
    public BallType getType() { return type; }
}
