package rs.ac.bg.fon.silab.dao;

import rs.ac.bg.fon.silab.entity.Company;

public interface CompanyDao extends AbstractDao<Company> {

    Company getByTaxIdNumber(String taxIdNumber);

}
