package DesignElevatorSystem;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SuppressWarnings("unused")
public class Elevator implements Runnable{
    private int id;
    private volatile int currentFloor = 0;
    private Direction direction = Direction.IDLE;
    private ElevatorState state = ElevatorState.ACTIVE;

    private BlockingQueue<Integer> requests = new LinkedBlockingQueue<>();

    public Elevator(int id){
        this.id = id;
    }

    public void addRequest(int floor){
        if(this.state == ElevatorState.ACTIVE){
            this.requests.offer(floor);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer target = this.requests.take(); // blocks until a request arrives
                moveTo(target);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Elevator "+id+" shutting down.");
        }
    }

    private void moveTo(int target) throws InterruptedException{
        while (this.currentFloor != target) {
            if(currentFloor < target){
                this.direction = Direction.UP;
                currentFloor++;
            }
            else{
                this.direction = Direction.DOWN;
                currentFloor--;
            }
            System.out.println("Elevator "+id+" at floor "+this.currentFloor+" moving "+this.direction);
            Thread.sleep(500); // simulate arrival
        }
        System.out.println("Elevator "+id+" opening doors at floor "+this.currentFloor);
        direction = Direction.IDLE;
    }

    public ElevatorState getState(){
        return this.state;
    }

    public int getCurrentFloor(){
        return this.currentFloor;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public int getElevatorId(){
        return this.id;
    }

    
}
