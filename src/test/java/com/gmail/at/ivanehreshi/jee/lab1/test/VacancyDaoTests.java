package com.gmail.at.ivanehreshi.jee.lab1.test;

import com.gmail.at.ivanehreshi.jee.lab1.domain.Company;
import com.gmail.at.ivanehreshi.jee.lab1.domain.Vacancy;
import com.gmail.at.ivanehreshi.jee.lab1.persistence.PersistenceUtils;
import com.gmail.at.ivanehreshi.jee.lab1.persistence.dao.CompanyDao;
import com.gmail.at.ivanehreshi.jee.lab1.persistence.dao.VacancyDao;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class VacancyDaoTests {
    PersistenceUtils persistenceUtils;
    VacancyDao vacancyDao;
    CompanyDao companyDao;
    Vacancy exampleVacancy;
    Company vacancyCompany;

    @Before
    public void setUp() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl(DbSettings.URL);
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("sa");

        persistenceUtils = new PersistenceUtils(jdbcDataSource);
        persistenceUtils.executeResourceFile("clear-vacancies.sql");
        persistenceUtils.executeResourceFile("vacancy.sql");
        vacancyDao = new VacancyDao(persistenceUtils);
        companyDao = new CompanyDao(persistenceUtils);
        vacancyCompany = new Company("Example");
        exampleVacancy = new Vacancy(vacancyCompany, 1000, "Developer", "No requirements");

    }

    @Test
    public void testRead_When_NoRecord_Expect_Null() {
        Vacancy vacancy = vacancyDao.read(1l);

        Assert.assertNull(vacancy);
    }

    @Test
    public void testCreateRead() {

        companyDao.create(exampleVacancy.getCompany());
        vacancyDao.create(exampleVacancy);

        Vacancy retrievedVacancy = vacancyDao.read(exampleVacancy.getId());

        Assert.assertEquals(exampleVacancy, retrievedVacancy);
        Assert.assertEquals(exampleVacancy.getCompany(), retrievedVacancy.getCompany());
    }

    @Test
    public void testCreateUpdate() {
        companyDao.create(exampleVacancy.getCompany());
        long id = vacancyDao.create(exampleVacancy);

        exampleVacancy.setPosition("Operator");
        vacancyDao.update(exampleVacancy);

        Vacancy retrievedVacancy = vacancyDao.read(id);

        Assert.assertEquals(exampleVacancy, retrievedVacancy);
    }

    @Test
    public void testCreateDeleteRead() {
        companyDao.create(exampleVacancy.getCompany());
        long id = exampleVacancy.getId();

        vacancyDao.create(exampleVacancy);
        vacancyDao.delete(id);

        Vacancy retrievedVacancy = vacancyDao.read(id);
        Assert.assertNull(retrievedVacancy);
    }

    @Test
    public void testFindVacanciesWithSalary_When_Db_Empty() {
        List<Vacancy> vacancies = vacancyDao.findVacanciesWithSalary(">1000");
        Assert.assertTrue(vacancies.isEmpty());

        vacancies = vacancyDao.findVacanciesWithSalary("<=1000");
        Assert.assertTrue(vacancies.isEmpty());
    }

    @Test
    public void testFindVacanciesWithSalary() {
        companyDao.create(vacancyCompany);

        Vacancy vacancy1 = new Vacancy(vacancyCompany, 300, "Administrator", "");
        Vacancy vacancy2 = new Vacancy(vacancyCompany, 1000, "Developer", "");
        Vacancy vacancy3 = new Vacancy(vacancyCompany, 1100, "Manager", "");

        vacancyDao.create(vacancy1);
        vacancyDao.create(vacancy2);
        vacancyDao.create(vacancy3);

        List<Vacancy> vacancies;

        vacancies = vacancyDao.findVacanciesWithSalary("<=1000");
        Assert.assertEquals(2, vacancies.size());
        Assert.assertTrue(vacancies.contains(vacancy1));
        Assert.assertTrue(vacancies.contains(vacancy2));

        vacancies = vacancyDao.findVacanciesWithSalary(">1000");
        Assert.assertEquals(1, vacancies.size());
        Assert.assertTrue(vacancies.contains(vacancy3));
    }
}
