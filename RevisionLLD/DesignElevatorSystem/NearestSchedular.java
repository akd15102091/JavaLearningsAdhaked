package DesignElevatorSystem;

import java.util.List;

// it is based on distance, not considering direction
public class NearestSchedular implements Schedular{

    @Override
    public Elevator pickElevator(Request request, List<Elevator> elevators) {
        Elevator best = null;
        int dist = Integer.MAX_VALUE;
        for(Elevator e : elevators){
            if(e.getState() != ElevatorState.ACTIVE){
                continue;
            }
            int d = Math.abs(e.getCurrentFloor() - request.getFloor());
            if(d < dist){
                dist = d;
                best = e;
            }
        }

        return best;
    }
    
}
