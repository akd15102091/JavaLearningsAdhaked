package HandlerInterface;

import Request.Request;

public interface SupportHandler {
    void handleRequest(Request request) ;
    void setNextHandler(SupportHandler nextSupportHandler) ;
}
