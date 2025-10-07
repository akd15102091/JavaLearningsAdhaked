import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookMyShow {

    MovieController movieController;
    TheaterController theaterController;

    public BookMyShow(){
        this.movieController = new MovieController();
        this.theaterController = new TheaterController();
    }

    public void addMovies(){
        Movie movies = new Movie(1, "DDLJ", 150);
        Movie movies2 = new Movie(1, "Pushpa", 180);
        Movie movies3 = new Movie(1, "Bahubali", 200);

        movieController.addMoviesToList(movies);
        movieController.addMoviesToList(movies2);
        movieController.addMoviesToList(movies3);

        movieController.addMovie("Jaipur", movies);
        movieController.addMovie("Jaipur", movies2);
        movieController.addMovie("Bengaluru", movies3);
    }

    public List<Seat> createSeats(){
        List<Seat> seats = new ArrayList<>();
        for(int i=1;i<=10;i++){
            if(i<=5){
                Seat seat = new Seat(i, 1, SeatType.PREMIUM, false);
                seats.add(seat);
            }
            else{
                Seat seat = new Seat(i, 2, SeatType.RECLINER, false);
                seats.add(seat);
            }
        }
       return seats;
    }

    public List<Screen> createScreens(){                                                                                                                                                                                                                                                                                                                                                                                                  
        List<Seat>seats = createSeats();
        Screen screen = new Screen(10, seats);
        List<Screen> screens = new ArrayList<>();
        screens.add(screen);
        return screens;
    }

    public List<Show> createShow(Screen screen){
        List<Show> shows = new ArrayList<>();
        Show show = new Show(1, movieController.getMovieByName("DDLJ"), screen, 8);
        Show show2 = new Show(2, movieController.getMovieByName("DDLJ"), screen, 12);
        shows.add(show);
        shows.add(show2);
        return shows;

    }

    public void addTheaters() {
        Theater pvrTheater = new Theater();
        Theater inoxTheater = new Theater();
        pvrTheater.setId(1);
        pvrTheater.setAddress("Jaipur");
        pvrTheater.setScreens(createScreens());
        pvrTheater.setShow(createShow(pvrTheater.getScreens().get(0)));

        inoxTheater.setId(1);
        inoxTheater.setAddress("Jaipur");
        inoxTheater.setScreens(createScreens());
        inoxTheater.setShow(createShow(pvrTheater.getScreens().get(0)));

        List<Theater> theaters = new ArrayList<>();
        theaters.add(pvrTheater);
        theaters.add(inoxTheater);
        theaterController.addTheaters("Jaipur", theaters);

    }

    public void initialize(){
        addMovies();
        addTheaters();

    }

    public void createBooking(String city, String movie){
        Map<Theater,List<Show>> lst = theaterController.getShows(city,movie);
        Map.Entry<Theater,List<Show>> entry = lst.entrySet().iterator().next();
        List<Show> runningShows = entry.getValue();
        Show interestedShow = runningShows.get(0);
        int seatNumber =1;

    }
    public static void main(String[] args) {
        BookMyShow bookMyShow = new BookMyShow();
        bookMyShow.initialize();

        bookMyShow.createBooking("Jaipur","DDLJ");
    }
}