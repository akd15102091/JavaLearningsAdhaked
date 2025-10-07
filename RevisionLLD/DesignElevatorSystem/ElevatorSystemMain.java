package DesignElevatorSystem;

public class ElevatorSystemMain {
    public static void main(String[] args) throws InterruptedException{
        ElevatorController controller = new ElevatorController(3) ;

        // simulate concurrent requests
        controller.submitRequest(Request.outside(2, Direction.UP));
        controller.submitRequest(Request.outside(5, Direction.DOWN));
        controller.submitRequest(Request.inside(0, 7));

        controller.submitRequest(Request.outside(2, Direction.DOWN));
        controller.submitRequest(Request.inside(1, 0));

        Thread.sleep(4000);

        controller.submitRequest(Request.outside(9, Direction.DOWN));
        controller.submitRequest(Request.inside(1, 3));

        Thread.sleep(6000);
        controller.shutdown();

    }
}
