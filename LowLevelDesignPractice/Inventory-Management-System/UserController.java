import java.util.ArrayList;
import java.util.List;

public class UserController {
    List<User> users = new ArrayList<>();

    public void createUser(User user){
        users.add(user);
    }

    public User getUser(int id){
        for(User user: users){
            if(user.getUserId() ==id){
                return user;
            }
        }
        return null;
    }
}
