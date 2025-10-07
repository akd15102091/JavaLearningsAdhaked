import java.util.*;

public class Screen {
    int id;
    List<Seat>seats = new ArrayList<>();
    public Screen(int id, List<Seat> seats) {
        this.id = id;
        this.seats = seats;
    }
}
