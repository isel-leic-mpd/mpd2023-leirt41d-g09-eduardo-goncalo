package isel.mpd.moviesdb2.model;

import java.time.LocalDate;
import java.util.stream.Stream;

public class CrewMovie extends Movie {
    private final String job;
    private final String department;

    public CrewMovie(LocalDate start_date,
                     String name, int id,
                     double popularity,
                     Stream<Actor> actors,
                     Stream<Genre> genres,
                     Stream<MovieDetail> recommendations,
                     String job,
                     String department) {
        super(start_date, name, id, popularity,
                actors, genres, recommendations);
        this.job = job;
        this.department = department;
    }

    public String getJob() { return job; }
    public String getDepartment() { return department; }
}
