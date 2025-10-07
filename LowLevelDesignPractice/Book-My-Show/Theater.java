import java.util.ArrayList;
import java.util.List;

public class Theater {
    int id;
    String address;
    List<Screen>screens = new ArrayList<>();
    List<Show>show = new ArrayList<>();
    
    public void setId(int id) {
        this.id = id;
    }
  
    public List<Show> getShow() {
        return show;
    }

    public void setAddress(String address) {
        this.address = address;
    }
   
    public void setScreens(List<Screen> screens) {
        this.screens = screens;
    }
   
    public void setShow(List<Show> show) {
        this.show = show;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    
}
