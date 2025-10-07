package DesignCricInfo;

public class BattingStats {
    private final Player batsman;
    private int runs = 0;
    private int ballsFaced = 0;
    private boolean isOut = false;

    public BattingStats(Player batsman) {
        this.batsman = batsman;
    }

    public void addRuns(int run) {
        this.runs += run;
        this.ballsFaced++;
    }

    public void markOut() { this.isOut = true; }

    public int getRuns() { return runs; }
    public int getBallsFaced() { return ballsFaced; }
    public boolean isOut() { return isOut; }
    public Player getBatsman() { return batsman; }
}
