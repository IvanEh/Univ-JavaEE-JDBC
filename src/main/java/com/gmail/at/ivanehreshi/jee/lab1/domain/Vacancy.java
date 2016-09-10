package com.gmail.at.ivanehreshi.jee.lab1.domain;

public class Vacancy {
    private long id;
    private String position;
    private double estimatedSalary;
    private Company company;
    String requirements;

    public Vacancy(long id, String position, double estimatedSalary, Company company, String requirements) {
        this.company = company;
        this.estimatedSalary = estimatedSalary;
        this.id = id;
        this.position = position;
    }

    public Vacancy(String position, double estimatedSalary, Company company, String requirements) {
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

    // Generated
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vacancy vacancy = (Vacancy) o;

        if (id != vacancy.id) return false;
        if (Double.compare(vacancy.estimatedSalary, estimatedSalary) != 0) return false;
        if (position != null ? !position.equals(vacancy.position) : vacancy.position != null) return false;
        if (company != null ? !company.equals(vacancy.company) : vacancy.company != null) return false;
        return requirements != null ? requirements.equals(vacancy.requirements) : vacancy.requirements == null;

    }

    // Generated
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + (position != null ? position.hashCode() : 0);
        temp = Double.doubleToLongBits(estimatedSalary);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (requirements != null ? requirements.hashCode() : 0);
        return result;
    }
}
