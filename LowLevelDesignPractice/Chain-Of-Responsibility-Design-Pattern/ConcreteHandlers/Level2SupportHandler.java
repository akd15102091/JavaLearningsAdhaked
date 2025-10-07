package ConcreteHandlers;

import HandlerInterface.SupportHandler;
import Request.Priority;
import Request.Request;

public class Level2SupportHandler implements SupportHandler{
    private SupportHandler nextHandler;

    @Override
    public void handleRequest(Request request) {
         if(request.getPriority()==Priority.INTERMEDIATE){
            System.out.println("Request level is INTERMEDIATE and handled by Level2 Support");
            return ;
        }
        this.nextHandler.handleRequest(request);
    }

    @Override
    public void setNextHandler(SupportHandler nextSupportHandler) {
        this.nextHandler = nextSupportHandler;
    }
    
}
