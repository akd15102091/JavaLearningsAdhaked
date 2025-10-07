package ConcreteHandlers;

import HandlerInterface.SupportHandler;
import Request.Priority;
import Request.Request;

public class Level1SupportHandler implements SupportHandler {
    private SupportHandler nextHandler;

    @Override
    public void handleRequest(Request request) {
        if(request.getPriority()==Priority.BASIC){
            System.out.println("Request level is BASIC and handled by Level1 Support");
            return ;
        }
        this.nextHandler.handleRequest(request);
    }

    @Override
    public void setNextHandler(SupportHandler nextSupportHandler) {
        this.nextHandler = nextSupportHandler;
    }
    
}
