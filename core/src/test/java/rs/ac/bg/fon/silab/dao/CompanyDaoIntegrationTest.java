package rs.ac.bg.fon.silab.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.bg.fon.silab.config.CoreTestConfig;
import rs.ac.bg.fon.silab.entity.Company;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CoreTestConfig.class)
@Transactional
class CompanyDaoIntegrationTest {

    @Autowired
    private CompanyDao companyDao;

    @Test
    void testInsert() {
        Company company = new Company("SAP", "123123123");

        companyDao.insert(company);

        assertAll(
                () -> assertNotNull(company.getId(), "Id should not be null."),
                () -> assertEquals(1, companyDao.getAll().size(), "Size of companies list should be 1.")
        );
    }

    @Test
    void testDelete() {
        Company company = new Company("SAP", "123123123");

        companyDao.insert(company);
        companyDao.delete(company);

        assertAll(
                () -> assertNull(companyDao.getById(company.getId()), "Company should be null."),
                () -> assertEquals(0, companyDao.getAll().size(), "Size of companies list should be 0.")
        );
    }

    @Test
    void testGetByIdWhenCompanyExists() {
        Company company = new Company("SAP", "123123123");

        companyDao.insert(company);
        Company companyFromDatabase = companyDao.getById(company.getId());

        assertAll(
                () -> assertEquals("SAP", companyFromDatabase.getName(), "Name should be SAP."),
                () -> assertEquals("123123123", companyFromDatabase.getTaxIdNumber(),
                        "TaxIdNumber should be 123123123.")
        );
    }

    @Test
    void testGetByIdWhenCompanyDoesNotExist() {
        Integer companyId = 10;

        Company companyFromDatabase = companyDao.getById(companyId);

        assertNull(companyFromDatabase, "Company should be null.");
    }

    @Test
    void testGetAll() {
        Company company1 = new Company("SAP", "123123123");
        Company company2 = new Company("Microsoft", "111222333");
        Company company3 = new Company("Google", "101010101");

        companyDao.insert(company1);
        companyDao.insert(company2);
        companyDao.insert(company3);

        List<Company> companiesFromDatabase = companyDao.getAll();

        assertEquals(3, companiesFromDatabase.size(), "Size of companies list should be 3.");
    }

    @Test
    void testGetByTaxIdNumberWhenCompanyExists() {
        String taxIdNumber = "123123123";
        Company company = new Company("SAP", taxIdNumber);

        companyDao.insert(company);
        List<Company> companiesFromDatabase = companyDao.getByTaxIdNumber(taxIdNumber);

        assertAll(
                () -> assertEquals(1, companiesFromDatabase.size(), "Size of companies list should be 1."),
                () -> assertEquals(company.getId(), companiesFromDatabase.get(0).getId(),
                        "Id of the inserted company should be the same as the company id from the database."),
                () -> assertEquals("SAP", companiesFromDatabase.get(0).getName(), "Name should be SAP.")
        );

    }

    @Test
    void testGetByTaxIdNumberWhenCompanyDoesNotExist() {
        String taxIdNumber = "123123123";

        List<Company> companiesFromDatabase = companyDao.getByTaxIdNumber(taxIdNumber);

        assertEquals(0, companiesFromDatabase.size(), "Size of companies list should be 0.");
    }

}
