package DesignCricInfo;

import java.util.HashMap;
import java.util.Map;

public class ScoreCard implements ScoreObserver{
    private final Map<Player, BattingStats> battingStatsMap = new HashMap<>();
    private final Map<Player, BowlingStats> bowlingStatsMap = new HashMap<>();

    public void recordBall(Ball ball) {
        battingStatsMap.putIfAbsent(ball.getBatsman(), new BattingStats(ball.getBatsman()));
        bowlingStatsMap.putIfAbsent(ball.getBowler(), new BowlingStats(ball.getBowler()));

        BattingStats bs = battingStatsMap.get(ball.getBatsman());
        BowlingStats bowStats = bowlingStatsMap.get(ball.getBowler());

        bs.addRuns(ball.getRuns());
        bowStats.addBall(ball);

        if (ball.isWicket()) bs.markOut();
    }

    @Override
    public void update(String message) {
        System.out.println("[ScoreCard Update] " + message);
    }

    public void printScore() {
        System.out.println("\n=== Batting Score ===");
        for (BattingStats bs : battingStatsMap.values()) {
            System.out.println(bs.getBatsman().getName() + ": " + bs.getRuns() + "(" + bs.getBallsFaced() + ") " + (bs.isOut() ? "OUT" : "NOT OUT"));
        }

        System.out.println("\n=== Bowling Score ===");
        for (BowlingStats bs : bowlingStatsMap.values()) {
            System.out.println(bs.getBowler().getName() + ": " + bs.getWickets() + " wickets, " + bs.getRunsConceded() + " runs in " + bs.getBallsBowled() + " balls");
        }
    }
}
