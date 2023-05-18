package isel.mpd.moviesdb2.dto;

import java.util.List;
import java.util.stream.Collectors;

public class MovieDetailDto extends MovieDto{
    private int runtime;
    private long revenue;
    private long budget;
    private List<CompanyDto> production_companies;

    @Override
    public String toString() {
        return "{ " +
                showContent()
                + ", runtime=" + runtime
                + ", company=" + production_companies.get(0).getName()
                + "}";
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

    public List<String> getCompanies() {
        if (production_companies == null) return List.of();
        return production_companies.stream()
                .map(pc -> pc.getName())
                .collect(Collectors.toList());
    }
}
