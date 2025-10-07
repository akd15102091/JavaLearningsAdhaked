package DesignMeetingSchedular;

import java.time.LocalDateTime;

public class TimeSlot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime(){ return this.startTime; }
    public LocalDateTime getEndTime(){ return this.endTime; }

    public boolean isOverlaps(TimeSlot slot){ // if overlaps -> return true, otherwise return false
        return !(this.startTime.isAfter(slot.getEndTime()) || this.endTime.isBefore(slot.getStartTime())) ;
    }
}
