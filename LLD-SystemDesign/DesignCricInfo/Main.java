package DesignCricInfo;

import java.util.Arrays;
import java.util.List;

import DesignCricInfo.enums.PlayerRole;

public class Main {
    public static void main(String[] args) {
        List<Player> teamAPlayers = Arrays.asList(
                new Player("Rohit", PlayerRole.BATSMAN),
                new Player("Gill", PlayerRole.BATSMAN),
                new Player("Virat", PlayerRole.BATSMAN),
                new Player("Iyer", PlayerRole.BATSMAN),
                new Player("Pant", PlayerRole.WICKET_KEEPER),
                new Player("Hardik", PlayerRole.ALL_ROUNDER),
                new Player("Jadeja", PlayerRole.ALL_ROUNDER),
                new Player("Kuldeep", PlayerRole.BOWLER),
                new Player("Shami", PlayerRole.BOWLER),
                new Player("Bumrah", PlayerRole.BOWLER),
                new Player("Siraj", PlayerRole.BOWLER)
        );

        List<Player> teamBPlayers = Arrays.asList(
                new Player("Warner", PlayerRole.BATSMAN),
                new Player("Head", PlayerRole.BATSMAN),
                new Player("Smith", PlayerRole.BATSMAN),
                new Player("Labuschagne", PlayerRole.BATSMAN),
                new Player("Carey", PlayerRole.WICKET_KEEPER),
                new Player("Marsh", PlayerRole.ALL_ROUNDER),
                new Player("Maxwell", PlayerRole.ALL_ROUNDER),
                new Player("Starc", PlayerRole.BOWLER),
                new Player("Hazlewood", PlayerRole.BOWLER),
                new Player("Zampa", PlayerRole.BOWLER),
                new Player("Cummins", PlayerRole.BOWLER)
        );

        Team teamA = new Team("India", teamAPlayers);
        Team teamB = new Team("Australia", teamBPlayers);

        Match match = new Match(teamA, teamB);
        match.start();
    }
}
