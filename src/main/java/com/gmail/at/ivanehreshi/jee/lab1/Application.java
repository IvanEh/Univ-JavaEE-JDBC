package com.gmail.at.ivanehreshi.jee.lab1;

import com.gmail.at.ivanehreshi.jee.lab1.persistence.PersistenceUtils;
import com.gmail.at.ivanehreshi.jee.lab1.persistence.dao.CompanyDao;
import com.gmail.at.ivanehreshi.jee.lab1.domain.Company;

public class Application {
    public static final String JDBC_URL = "jdbc:mysql://127.1/";
    public static final String JDBC_DB_NAME = "vacancy";
    public static final String JDBC_USER = "ivaneh";
    public static final String JDBC_PASSWORD = "password";

    PersistenceUtils persistenceUtils;

    public Application() {
        this.persistenceUtils = new PersistenceUtils(JDBC_URL, JDBC_DB_NAME,
                                JDBC_USER, JDBC_PASSWORD);
    }

    public void run() {
        CompanyDao companyDao = new CompanyDao(persistenceUtils);
        long id = companyDao.create(new Company("Microsoft"));
        Company company = companyDao.read(id);
        System.out.println(company.getName());
        companyDao.delete(id);
    }

    public static void main(String[] args) {
        Application application = new Application();
        application.run();
    }

}
