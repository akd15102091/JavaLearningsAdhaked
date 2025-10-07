package DesignCricInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import DesignCricInfo.enums.BallType;

@SuppressWarnings("unused")
public class Inning {
    private final Team battingTeam;
    private final Team bowlingTeam;
    private final List<Over> overs = new ArrayList<>();
    private final ScoreCard scoreCard;

    public Inning(Team battingTeam, Team bowlingTeam, ScoreCard scoreCard) {
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
        this.scoreCard = scoreCard;
    }

    public void start(int overLimit) {
        AtomicInteger currentBatsmanIndex = new AtomicInteger(0);
        Player striker = battingTeam.getPlayers().get(currentBatsmanIndex.getAndIncrement());
        Player nonStriker = battingTeam.getPlayers().get(currentBatsmanIndex.getAndIncrement());
        Player bowler = bowlingTeam.getPlayers().get(0);

        for (int i = 0; i < overLimit; i++) {
            Over over = new Over();
            while (!over.isComplete()) {
                int run = new Random().nextInt(7); // 0 to 6
                boolean wicket = run == 0 && new Random().nextInt(10) > 7;
                BallType type = BallType.LEGAL;
                Ball ball = new Ball(striker, bowler, run, wicket, type);

                over.addBall(ball);
                scoreCard.recordBall(ball);

                if (wicket) {
                    scoreCard.update(striker.getName() + " is OUT!");
                    if (currentBatsmanIndex.get() < battingTeam.getPlayers().size()) {
                        striker = battingTeam.getPlayers().get(currentBatsmanIndex.getAndIncrement());
                    } else {
                        return; // All out
                    }
                } else if (run % 2 != 0) {
                    Player temp = striker;
                    striker = nonStriker;
                    nonStriker = temp;
                }
            }
            overs.add(over);
            Player temp = striker;
            striker = nonStriker;
            nonStriker = temp;
        }
    }
}
