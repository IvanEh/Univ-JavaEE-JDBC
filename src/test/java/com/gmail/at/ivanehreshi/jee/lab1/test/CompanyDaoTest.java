package com.gmail.at.ivanehreshi.jee.lab1.test;


import com.gmail.at.ivanehreshi.jee.lab1.domain.Company;
import com.gmail.at.ivanehreshi.jee.lab1.persistence.PersistenceUtils;
import com.gmail.at.ivanehreshi.jee.lab1.persistence.dao.CompanyDao;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CompanyDaoTest {
    PersistenceUtils persistenceUtils;
    CompanyDao companyDao;
    Company exampleCompany;

    @Before
    public void setUp() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setUrl(DbSettings.URL);
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("sa");

        persistenceUtils = new PersistenceUtils(jdbcDataSource);
        persistenceUtils.executeResourceFile("clear-vacancies.sql");
        persistenceUtils.executeResourceFile("vacancy.sql");
        companyDao = new CompanyDao(persistenceUtils);
        exampleCompany = new Company("Example");
    }

    @After
    public void tearDown() {
        persistenceUtils.close();
    }

    @Test
    public void testRead_When_NoRecord_Expect_Null() {
        Company company = companyDao.read(1l);

        Assert.assertNull(company);
    }

    @Test
    public void testCreateRead() {
        companyDao.create(exampleCompany);
        Company retrievedCompany = companyDao.read(exampleCompany.getId());

        Assert.assertEquals(exampleCompany, retrievedCompany);
    }

    @Test
    public void testCreateUpdate() {
        long id = companyDao.create(exampleCompany);

        exampleCompany.setName("Example 2");
        companyDao.update(exampleCompany);

        Company retrievedCompany = companyDao.read(id);

        Assert.assertEquals(exampleCompany, retrievedCompany);
    }

    @Test
    public void testCreateDeleteRead() {
        long id = exampleCompany.getId();

        companyDao.create(exampleCompany);
        companyDao.delete(id);

        Company retrievedCompany = companyDao.read(id);
        Assert.assertNull(retrievedCompany);
    }
}
