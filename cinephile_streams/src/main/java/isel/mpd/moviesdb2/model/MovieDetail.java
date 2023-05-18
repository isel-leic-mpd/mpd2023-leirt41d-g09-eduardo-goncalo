package isel.mpd.moviesdb2.model;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class MovieDetail extends Movie{
    private List<String> companies;
    private int runtime;
    private long revenue;
    private long budget;

    public MovieDetail(
        LocalDate start_date,
        String name,
        int id,
        double popularity,
        Stream<Actor> actors,
        Stream<Genre> genres,
        Stream<MovieDetail> recommendations,
        int runtime,
        long budget,
        long revenue,
        List<String> companies) {
        super(start_date, name, id, popularity, actors, genres, recommendations);
        this.budget = budget;
        this.revenue = revenue;
        this.runtime = runtime;
        this.companies = companies;
    }

    private List<String> getCompanies() {
        return companies;
    }

    private String getCompaniesAsString() {
        return
            ", companies=" +
                companies.stream().reduce("", (n,res) -> res.length()== 0 ? n : res + ":" +n);

    }

    public int getRuntime() {
        return runtime;
    }

    public long getRevenue() {
        return revenue;
    }

    public long getBudget() {
        return budget;
    }

}
