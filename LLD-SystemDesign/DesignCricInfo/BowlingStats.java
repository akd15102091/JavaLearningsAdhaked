package DesignCricInfo;

import DesignCricInfo.enums.BallType;

public class BowlingStats {
    private final Player bowler;
    private int ballsBowled = 0;
    private int runsConceded = 0;
    private int wickets = 0;

    public BowlingStats(Player bowler) {
        this.bowler = bowler;
    }

    public void addBall(Ball ball) {
        if (ball.getType() == BallType.LEGAL) ballsBowled++;
        runsConceded += ball.getRuns();
        if (ball.isWicket()) wickets++;
    }

    public int getBallsBowled() { return ballsBowled; }
    public int getRunsConceded() { return runsConceded; }
    public int getWickets() { return wickets; }
    public Player getBowler() { return bowler; }
}
