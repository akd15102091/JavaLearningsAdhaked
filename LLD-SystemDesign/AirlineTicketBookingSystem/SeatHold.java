package AirlineTicketBookingSystem;

import java.time.LocalDateTime;
import java.util.List;

public class SeatHold {
    public String holdId;
    public long userId;
    public long flightInstanceId;
    public List<String> seatNumbers;
    public LocalDateTime expiresAt;

    public SeatHold(String holdId, long userId, long flightInstanceId,
                    List<String> seats, LocalDateTime expires) {
        this.holdId = holdId; this.userId = userId;
        this.flightInstanceId = flightInstanceId;
        this.seatNumbers = seats; this.expiresAt = expires;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
