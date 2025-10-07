package ConcreteHandlers;

import HandlerInterface.SupportHandler;
import Request.Priority;
import Request.Request;

public class Level3SupportHandler implements SupportHandler{
    private SupportHandler nextHandler;

    @Override
    public void handleRequest(Request request) {
        if(request.getPriority()==Priority.CRITICAL){
            System.out.println("Request level is CRITICAL and handled by Level3 Support");
            return ;
        }
    }

    @Override
    public void setNextHandler(SupportHandler nextSupportHandler) {
        this.nextHandler = nextSupportHandler ;
    }
}
