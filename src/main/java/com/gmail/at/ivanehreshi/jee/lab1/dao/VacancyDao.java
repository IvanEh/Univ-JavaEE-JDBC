package com.gmail.at.ivanehreshi.jee.lab1.dao;

import com.gmail.at.ivanehreshi.jee.lab1.Dao;
import com.gmail.at.ivanehreshi.jee.lab1.PersistenceUtils;
import com.gmail.at.ivanehreshi.jee.lab1.domain.Company;
import com.gmail.at.ivanehreshi.jee.lab1.domain.Vacancy;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.gmail.at.ivanehreshi.jee.lab1.dao.CompanyDao.SELECT_COMPANY;

public class VacancyDao implements Dao<Vacancy, Long> {
    public static final String SELECT_VACANCY =
            "SELECT id, position, estimated_salary, company_id, requirements FROM `vacancy` WHERE `id` = ?";
    public static final String INSERT_COMPANY =
            "INSERT INTO `vacancy`(position, estimated_salary, company_id, requirements) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_COMPANY =
            "UPDATE `vacancy` SET position=?, estimated_salary=?, company_id=?, requirements=? WHERE id=?";
    public static final String DELETE_COMPANY =
            "DELETE FROM `vacancy` WHERE id=?";

    private PersistenceUtils persistenceUtils;
    private CompanyDao companyDao;

    public VacancyDao(PersistenceUtils persistenceUtils) {
        this.persistenceUtils = persistenceUtils;
        this.companyDao = new CompanyDao(persistenceUtils);
    }

    @Override
    public Long create(Vacancy vacancy) {
        return this.persistenceUtils.insert(INSERT_COMPANY,
                vacancy.getPosition(),
                vacancy.getEstimatedSalary(),
                vacancy.getCompany().getId(),
                vacancy.getRequirements());
    }

    @Override
    public Vacancy read(Long id) {
        ResultSet rs = persistenceUtils.query(SELECT_COMPANY, id);
        if(rs == null)
            return null;

        Vacancy vacancy = new Vacancy();
        try {
            if(rs.next()) {
                vacancy.setId(id);
                vacancy.setPosition(rs.getString("position"));
                vacancy.setEstimatedSalary(rs.getDouble("estimate_salary"));
                vacancy.setCompany(companyDao.read(rs.getLong("company_id")));
                vacancy.setRequirements(rs.getString("requirements"));

                return vacancy;
            }
        } catch (SQLException e) {
            return null;
        }

        return null;
    }

    @Override
    public void update(Vacancy vacancy) {
        persistenceUtils.update(UPDATE_COMPANY,
                vacancy.getPosition(),
                vacancy.getEstimatedSalary(),
                vacancy.getCompany().getId(),
                vacancy.getRequirements(),
                vacancy.getEstimatedSalary(),
                vacancy.getId());
    }

    @Override
    public void delete(Long id) {
        persistenceUtils.update(DELETE_COMPANY, id);
    }
}
