package DesignElevatorSystem;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Elevator implements Runnable {
    private int id;
    private int currentFloor;
    @SuppressWarnings("unused")
    private int capacity;
    private ElevatorState state;
    private Lock lock;
    private PriorityBlockingQueue<Request> requestQueue;

    public Elevator(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.currentFloor = 0;
        this.state = ElevatorState.IDLE;
        this.lock = new ReentrantLock();
        this.requestQueue = new PriorityBlockingQueue<>();
    }

    public void addRequest(Request request) {
        lock.lock();
        try {
            requestQueue.add(request);
        } finally {
            lock.unlock();
        }
    }

    private void move() {
        while (!requestQueue.isEmpty()) {
            Request request = requestQueue.poll();
            if (request != null) {
                moveToFloor(request.floor);
            }
        }
        state = ElevatorState.IDLE;
    }

    private void moveToFloor(int destination) {
        while (currentFloor != destination) {
            if (currentFloor < destination) {
                state = ElevatorState.MOVING_UP;
                currentFloor++;
            } else {
                state = ElevatorState.MOVING_DOWN;
                currentFloor--;
            }
            System.out.println("Elevator " + id + " at floor: " + currentFloor);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Elevator " + id + " reached floor: " + destination);
    }

    @Override
    public void run() {
        while (true) {
            move();
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorState getState() {
        return state;
    }

    
}