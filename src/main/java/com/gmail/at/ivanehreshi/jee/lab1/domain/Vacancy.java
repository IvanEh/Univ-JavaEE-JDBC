package com.gmail.at.ivanehreshi.jee.lab1.domain;

public class Vacancy {
    private long id;
    private String position;
    private double estimatedSalary;
    private Company company;
    String requirements;

    public Vacancy(Company company, double estimatedSalary, long id, String position, String requirements) {
        this.company = company;
        this.estimatedSalary = estimatedSalary;
        this.id = id;
        this.position = position;
    }

    public Vacancy(Company company, double estimatedSalary, String position, String requirements) {
        this.company = company;
        this.estimatedSalary = estimatedSalary;
        this.position = position;
    }

    public Vacancy() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public double getEstimatedSalary() {
        return estimatedSalary;
    }

    public void setEstimatedSalary(double estimatedSalary) {
        this.estimatedSalary = estimatedSalary;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
}
