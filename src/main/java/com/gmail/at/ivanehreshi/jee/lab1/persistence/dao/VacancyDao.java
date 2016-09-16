package com.gmail.at.ivanehreshi.jee.lab1.persistence.dao;

import com.gmail.at.ivanehreshi.jee.lab1.domain.Vacancy;
import com.gmail.at.ivanehreshi.jee.lab1.persistence.PersistenceUtils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VacancyDao implements Dao<Vacancy, Long> {
    public static final String SELECT_VACANCY =
            "SELECT id, position, estimated_salary, company_id, requirements FROM `vacancy` WHERE `id` = ?";
    public static final String INSERT_VACANCY =
            "INSERT INTO `vacancy`(position, estimated_salary, company_id, requirements) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_VACANCY =
            "UPDATE `vacancy` SET position=?, estimated_salary=?, company_id=?, requirements=? WHERE id=?";
    public static final String DELETE_VACANCY =
            "DELETE FROM `vacancy` WHERE id=?";

    public static final String SELECT_VACANCY_BY_SALARY =
            "SELECT id, position, estimated_salary, company_id, requirements FROM `vacancy` WHERE estimated_salary %s";


    private PersistenceUtils persistenceUtils;
    private CompanyDao companyDao;

    public VacancyDao(PersistenceUtils persistenceUtils) {
        this.persistenceUtils = persistenceUtils;
        this.companyDao = new CompanyDao(persistenceUtils);
    }

    @Override
    public Long create(Vacancy vacancy) {
        Long id = this.persistenceUtils.insert(INSERT_VACANCY,
                vacancy.getPosition(),
                vacancy.getEstimatedSalary(),
                vacancy.getCompany().getId(),
                vacancy.getRequirements());
        vacancy.setId(id);
        return id;
    }

    @Override
    public Vacancy read(Long id) {
        ResultSet rs = persistenceUtils.query(SELECT_VACANCY, id);
        if(rs == null)
            return null;

        return persistenceUtils.withRs(rs, (rsArg) -> {
            if(rsArg.next()) {
                Vacancy vacancy = new Vacancy();
                vacancy.setId(id);
                vacancy.setPosition(rsArg.getString("position"));
                vacancy.setEstimatedSalary(rsArg.getDouble("estimated_salary"));
                vacancy.setCompany(companyDao.read(rsArg.getLong("company_id")));
                vacancy.setRequirements(rsArg.getString("requirements"));

                return vacancy;
            }

            return null;
        });
    }

    @Override
    public void update(Vacancy vacancy) {
        persistenceUtils.update(UPDATE_VACANCY,
                vacancy.getPosition(),
                vacancy.getEstimatedSalary(),
                vacancy.getCompany().getId(),
                vacancy.getRequirements(),
                vacancy.getId());
    }

    @Override
    public void delete(Long id) {
        persistenceUtils.update(DELETE_VACANCY, id);
    }

    public List<Vacancy> findVacanciesWithSalary(String criterion) {
        List<Vacancy> vacancies = new ArrayList<>();

        String sql = String.format(SELECT_VACANCY_BY_SALARY, criterion);
        ResultSet rs = persistenceUtils.query(sql);

        if(rs == null) {
            return vacancies;
        }

        persistenceUtils.withRs(rs, (rsArg) -> {
            while (rs.next()) {
                Vacancy nextVacancy = new Vacancy();
                nextVacancy.setId(rs.getLong("id"));
                nextVacancy.setPosition(rs.getString("position"));
                nextVacancy.setEstimatedSalary(rs.getDouble("estimated_salary"));
                nextVacancy.setCompany(companyDao.read(rs.getLong("company_id")));
                nextVacancy.setRequirements(rs.getString("requirements"));

                vacancies.add(nextVacancy);
            }

            return null;
        });


        return vacancies;
    }
}
