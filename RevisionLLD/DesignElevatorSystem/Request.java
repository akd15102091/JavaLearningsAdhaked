package DesignElevatorSystem;

@SuppressWarnings("unused")
public class Request {
    private final RequestType type; // OUTSIDE, INSIDE
    private final int floor; // OUTSIDE -> source floor,  INSIDE -> destination floor
    private final Direction direction; // relavant for outside request
    private final int elevatorId; // relevant for inside request

    public Request(RequestType type, int floor, Direction direction, int elevatorId){
        this.type = type;
        this.floor = floor;
        this.direction = direction;
        this.elevatorId = elevatorId;
    }

    // factory method for outside request
    public static Request outside(int floor, Direction direction){
        return new Request(RequestType.OUTSIDE, floor, direction, -1);
    }

    // factory method for inside request
    public static Request inside(int elevatorId, int destination){
        return new Request(RequestType.INSIDE, destination, null, elevatorId);
    }

    public int getFloor(){
        return this.floor;
    }
    public RequestType getRequestType(){
        return this.type;
    }
    
    public int getElevatorId(){
        return this.elevatorId;
    }

    public Direction getDirection(){
        return this.direction;
    }
}
