import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheaterController {
    Map<String,List<Theater>> cityTheaterMap = new HashMap<>();

    public void addTheaters(String city,List<Theater>theaters){
           cityTheaterMap.put(city,theaters);
    }

    public Map<Theater, List<Show>> getShows(String city,String movie){
        Map<Theater, List<Show>>mp = new HashMap<>();

        List<Theater> theaters = cityTheaterMap.get(city);
        for(int i=0;i<theaters.size();i++){
            List<Show> shows = theaters.get(i).getShow();
            for(int j=0;j<shows.size();j++){
                if(shows.get(j).getMovie().getName() == movie){
                    List<Show> showList = mp.getOrDefault(theaters.get(i), new ArrayList<>());
                    showList.add(shows.get(j));
                    mp.put(theaters.get(i), showList);
                }
            }
        }
        return mp;
 }
}
