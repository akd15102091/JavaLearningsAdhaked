package DesignSnakeLadder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private int size;
    private Map<Integer, Integer> snakes = new HashMap<>();
    private Map<Integer, Integer> ladders = new HashMap<>();

    public Board(int size, List<Snake> snakes, List<Ladder> ladders) {
        this.size = size;
        this.snakes = new HashMap<>();
        this.ladders = new HashMap<>();
        for (Snake s : snakes) this.snakes.put(s.getStart(), s.getEnd());
        for (Ladder l : ladders) this.ladders.put(l.getStart(), l.getEnd());
    }

    public int getSize() { return size; }

    public int getNewPosition(int position) {
        if (snakes.containsKey(position)) {
            System.out.println("🐍 Snake! " + position + " -> " + snakes.get(position));
            return snakes.get(position);
        }
        if (ladders.containsKey(position)) {
            System.out.println("🪜 Ladder! " + position + " -> " + ladders.get(position));
            return ladders.get(position);
        }
        return position;
    }

}
