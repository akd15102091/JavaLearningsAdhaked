public class User {
    String name;
    String DL;
    public String getName() {
        return name;
    }
    public User(String name, String dL) {
        this.name = name;
        DL = dL;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDL() {
        return DL;
    }
    public void setDL(String dL) {
        DL = dL;
    }
   
}
