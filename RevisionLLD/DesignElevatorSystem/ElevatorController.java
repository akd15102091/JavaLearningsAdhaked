package DesignElevatorSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorController {
    private List<Elevator> elevators;
    private Schedular schedular;
    private ExecutorService pool = Executors.newCachedThreadPool();

    public ElevatorController(int numElevators){
        this.elevators = new ArrayList<>();
        for(int i=0; i<numElevators; i++){
            Elevator e = new Elevator(i);
            this.elevators.add(e);
            pool.submit(e); // each elevator runs in its own thread
        }
        this.schedular = new DirectionalNearestSchedular();
    }
    
    public void submitRequest(Request req){
        if(req.getRequestType() == RequestType.OUTSIDE){
            Elevator chosen = this.schedular.pickElevator(req, elevators);
            if(chosen != null){
                chosen.addRequest(req.getFloor());
            }
        } else {
            elevators.get(req.getElevatorId()).addRequest(req.getFloor());
        }
    }

    public void shutdown(){
        pool.shutdown();
    }
}
