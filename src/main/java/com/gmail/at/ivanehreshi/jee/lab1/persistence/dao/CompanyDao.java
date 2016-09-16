package com.gmail.at.ivanehreshi.jee.lab1.persistence.dao;

import com.gmail.at.ivanehreshi.jee.lab1.domain.Company;
import com.gmail.at.ivanehreshi.jee.lab1.persistence.PersistenceUtils;

import java.sql.ResultSet;

public class CompanyDao implements Dao<Company, Long> {
    public static final String SELECT_COMPANY =
            "SELECT id, name FROM `company` WHERE `id` = ?";
    public static final String INSERT_COMPANY =
            "INSERT INTO `company`(`name`) VALUES (?)";
    public static final String UPDATE_COMPANY =
            "UPDATE `company` SET `name`=? WHERE id=?";
    public static final String DELETE_COMPANY =
            "DELETE FROM `company` WHERE id=?";

    private PersistenceUtils persistenceUtils;

    public CompanyDao(PersistenceUtils persistenceUtils) {
        this.persistenceUtils = persistenceUtils;
    }

    @Override
    public Long create(Company company) {
        Long id = this.persistenceUtils.insert(INSERT_COMPANY, company.getName());
        company.setId(id);
        return id;
    }

    @Override
    public Company read(Long id) {
        ResultSet rs = persistenceUtils.query(SELECT_COMPANY, id);
        if(rs == null)
            return null;

        return persistenceUtils.withRs(rs, (rsArg) -> {
            if(rsArg.next()) {
                Company company = new Company();
                company.setId(id);
                company.setName(rsArg.getString(2));
                return company;
            }

            return null;
        });
    }

    @Override
    public void update(Company company) {
        persistenceUtils.update(UPDATE_COMPANY, company.getName(), company.getId());
    }

    @Override
    public void delete(Long id) {
        persistenceUtils.update(DELETE_COMPANY, id);
    }
}
