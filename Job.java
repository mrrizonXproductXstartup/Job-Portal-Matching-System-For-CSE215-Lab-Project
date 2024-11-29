package model;

import java.util.List;

public class Job {
    private String title;
    private List<String> requiredSkills;
    private String location;
    private int minimumExperience;

    public Job(String title, List<String> requiredSkills, String location, int minimumExperience) {
        this.title = title;
        this.requiredSkills = requiredSkills;
        this.location = location;
        this.minimumExperience = minimumExperience;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getRequiredSkills() {
        return requiredSkills;
    }

    public String getLocation() {
        return location;
    }

    public int getMinimumExperience() {
        return minimumExperience;
    }
}
