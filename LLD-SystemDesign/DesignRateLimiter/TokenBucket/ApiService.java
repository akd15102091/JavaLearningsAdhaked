package DesignRateLimiter.TokenBucket;

//Usage example
public class ApiService {
    private static final RateLimiterManager rateLimiter = new RateLimiterManager(10, 5) ; // 10 tokens, 5 per sec

    public static void handleRequest(String userId, String request){
        if(rateLimiter.allowRequest(userId)){
            System.out.println("Request allowed for user "+ userId+": "+request);
        } else {
            System.out.println("Rate limited exceeded for user "+ userId+": "+request);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        String[] users = {"userA", "userB"} ;
        for(int i=1; i<=20; i++){
            for(String user : users){
                handleRequest(user, "API_CALL_"+i);
            }
            Thread.sleep(100) ;
        }
    }
}
