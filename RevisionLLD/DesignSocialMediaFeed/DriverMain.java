package DesignSocialMediaFeed;

import java.util.List;

import DesignSocialMediaFeed.services.SocialMediaService;

@SuppressWarnings("unused")
public class DriverMain {
    public static void main(String[] args) throws InterruptedException {
        SocialMediaService service = SocialMediaService.getInstance();
        
        User ashwini = service.registerUser("akd123", "Ashwini", "1234567890");
        User nishu = service.registerUser("nk123", "Nishu", "1234567891");
        User ksm = service.registerUser("ksm123", "Khemraj", "1234567892");

        service.sendRequest(ashwini, nishu);
        service.sendRequest(ashwini, ksm);
        service.acceptRequest(nishu, ashwini);
        service.acceptRequest(ksm, ashwini);

        Post p1 = service.createPost(nishu, "Hey, super excited to visit europe.", null);
        Thread.sleep(500);
        Post p4 = service.createPost(ksm, "Hey, played cricket in evening.", null);
        Thread.sleep(500);
        Post p2 = service.createPost(nishu, "Hey, back to india guys.", null);
        Thread.sleep(500);
        Post p3 = service.createPost(ksm, "Hey, watched a movie.", null);
        
        List<Post> feedForAshwini = service.getFeed(ashwini);
        System.out.println("Feed for Ashwini : ");
        for(Post p : feedForAshwini){
            System.out.println(" Posted by : "+p.getAuthor().getName());
            System.out.println("             "+p.getContent()+"\n");
        }

    }
}
