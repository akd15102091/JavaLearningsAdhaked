package DesignCricInfo;

import DesignCricInfo.enums.PlayerRole;

public class Player {
    private final String name;
    private final PlayerRole role;

    public Player(String name, PlayerRole role) {
        this.name = name;
        this.role = role;
    }

    public String getName() { return name; }
    public PlayerRole getRole() { return role; }
}
