import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieController {
    Map<String,List<Movie>> movies = new HashMap<>();
    List<Movie>moviesList =new ArrayList<>();

    public void addMoviesToList(Movie movie){
        moviesList.add(movie);
    }
    
    public Movie getMovieByName(String name){
        for(Movie movie:moviesList){
            if(movie.getName() == name){
                return movie;
            }
        }
        return null;
    }
    public void addMovie(String city, Movie movie){
        List<Movie>lst = movies.getOrDefault(city, new ArrayList<>());
        lst.add(movie);
        movies.put(city,lst);
    }
}
