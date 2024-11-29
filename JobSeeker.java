package model;

import java.util.List;

public class JobSeeker {
    private String name;
    private String email;
    private int age;
    private List<String> skills;
    private String location;
    private int experience;

    public JobSeeker(String name, String email, int age, List<String> skills, String location, int experience) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.skills = skills;
        this.location = location;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public List<String> getSkills() {
        return skills;
    }

    public String getLocation() {
        return location;
    }

    public int getExperience() {
        return experience;
    }

    public double calculateMatch(Job job) {
        double skillMatch = skills.stream().filter(job.getRequiredSkills()::contains).count() / (double) job.getRequiredSkills().size() * 60;
        double locationMatch = location.equalsIgnoreCase(job.getLocation()) ? 20 : 0;
        double experienceMatch = experience >= job.getMinimumExperience() ? 20 : 0;
        return skillMatch + locationMatch + experienceMatch;
    }
}
