package isel.mpd.moviesdb2.dto;

import java.util.Optional;

public class CrewDto {
    private int id;
    private String name;
    private String Department;

    @Override
    public String toString() {
        return "{ "
                + "name=" + name
                + ", id = " + id
                + ", Department=" + Department
                + " }";
    }

    public String getName() {
        return name;
    }


    public int getId() {
        return id;
    }


    public Optional<String> getDepartment() {

        return Department == null || Department.length() == 0 ? Optional.empty() :
                Optional.of(Department);
    }
}
