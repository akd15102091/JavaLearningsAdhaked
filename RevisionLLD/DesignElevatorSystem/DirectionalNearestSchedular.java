package DesignElevatorSystem;

import java.util.List;

public class DirectionalNearestSchedular implements Schedular{

    @Override
    public Elevator pickElevator(Request request, List<Elevator> elevators) {
        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for(Elevator e : elevators){
            if(e.getState() != ElevatorState.ACTIVE) continue;

            // calculate distance from elevator to Request Floor
            int distance = Math.abs(e.getCurrentFloor() - request.getFloor());

            // Case 1 : Elevator is IDLE -> always a good condidate
            if(e.getDirection() == Direction.IDLE){
                if(distance < minDistance){
                    minDistance = distance;
                    best = e;
                }
            }

            // Case 2 : Elevator moving UP and request is UP, and request floow is ahead
            else if(e.getDirection()==Direction.UP && request.getDirection()==Direction.UP && e.getCurrentFloor()<=request.getFloor()){
                if(distance < minDistance){
                    minDistance = distance;
                    best = e;
                }
            }

            // Case 3 : Elevator moving DOWN and request is DOWN, and request floor is behind
            else if(e.getDirection()==Direction.DOWN && request.getDirection()==Direction.DOWN && e.getCurrentFloor()>=request.getFloor()){
                if(distance < minDistance){
                    minDistance = distance;
                    best = e;
                }
            }
        }

        // Fallback: If no elevator matches direction, pick nearest idle/any
        if(best == null){
            for(Elevator e : elevators){
                if(e.getState() != ElevatorState.ACTIVE){
                    continue;
                }
                int d = Math.abs(e.getCurrentFloor() - request.getFloor());
                if(d < minDistance){
                    minDistance = d;
                    best = e;
                }
            }
        }

        System.out.println("OUTSIDE REQ ASSIGNMENT : Assigning Outside request from floor "+ request.getFloor() +" to lift "+best.getElevatorId());

        return best;
    }
    
}
