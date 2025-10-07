public class Movie {
    int id;
    String name;
    int durationInMin;
    public Movie(int id, String name, int durationInMin) {
        this.id = id;
        this.name = name;
        this.durationInMin = durationInMin;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    
}
