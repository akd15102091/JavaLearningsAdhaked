package DesignHotelManagementSystem;

// Factory Pattern for Room Creation
public class RoomFactory {
    public static Room createRoom(int roomNumber, RoomType type) {
        return new Room(roomNumber, type);
    }
}
