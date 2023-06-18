package isel.mpd.moviesdb2.model;

import java.util.Optional;
import java.util.stream.Stream;

public class MovieInfo {
    private final String name;
    private final int id;
    private final Actor mainActor;
    private final String job;

    public MovieInfo(CrewMovie crewMovie) {
        name = crewMovie.getName();
        id = crewMovie.getId();
        Optional<Actor> mainActor = crewMovie.getActors().findFirst();
        this.mainActor = mainActor.orElse(null);
        job = crewMovie.getJob();
    }

//    Stream<T> res = streams[0];
//		for (int i = 1; i < streams.length; ++i) {
//        res = res.toList().stream(); // Regenerate 'res'
//        res = sortedIntersection(cmp, res, streams[i]);
//    }

    public String getName() { return name; }

    public int getId() { return id; }

    public Actor getMainActor() { return mainActor; }

    public String getJob() { return job; }

    @Override
    public String toString() {
        String mainActorName = "error";
        if (mainActor != null) mainActorName = mainActor.getName();
        return "[ "
                + name + ", "
                + mainActorName + ", "
                + job
                + " ]";
    }
}
