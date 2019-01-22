package rs.ac.bg.fon.silab.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.silab.entity.Company;
import rs.ac.bg.fon.silab.dao.CompanyDao;

import java.util.List;

@Repository
public class CompanyDaoImpl implements CompanyDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void insert(Company company) {
        sessionFactory.getCurrentSession()
                .save(company);
    }

    @Override
    public void delete(Company company) {
        sessionFactory.getCurrentSession()
                .delete(company);
    }

    @Override
    public Company getById(Integer id) {
        return sessionFactory.getCurrentSession()
                .get(Company.class, id);
    }

    @Override
    public List<Company> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Company", Company.class)
                .getResultList();
    }

    @Override
    public List<Company> getByTaxIdNumber(String taxIdNumber) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Company where taxIdNumber=:taxIdNumber", Company.class)
                .setParameter("taxIdNumber", taxIdNumber)
                .getResultList();
    }

}
