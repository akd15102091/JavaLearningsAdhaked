package DesignCricInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import DesignCricInfo.enums.MatchResult;
import DesignCricInfo.enums.MatchStatus;
import DesignCricInfo.enums.MatchType;

@SuppressWarnings("unused")
public class Match {
    private final Team teamA;
    private final Team teamB;
    private MatchStatus status;
    private final ScoreCard scoreCard = new ScoreCard();

    public Match(Team teamA, Team teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
        this.status = MatchStatus.SCHEDULED;
    }

    public void start() {
        this.status = MatchStatus.LIVE;
        System.out.println("Match Started: " + teamA.getName() + " vs " + teamB.getName());

        Inning innings1 = new Inning(teamA, teamB, scoreCard);
        innings1.start(5);
        System.out.println("\nInnings 1 Completed\n");
        scoreCard.printScore();

        Inning innings2 = new Inning(teamB, teamA, scoreCard);
        innings2.start(5);
        System.out.println("\nInnings 2 Completed\n");
        scoreCard.printScore();

        this.status = MatchStatus.COMPLETED;
    }
}
