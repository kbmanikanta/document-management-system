package rs.ac.bg.fon.silab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.entity.Company;
import rs.ac.bg.fon.silab.dao.CompanyDao;
import rs.ac.bg.fon.silab.dto.company.CompanyGetDTO;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDTO;
import rs.ac.bg.fon.silab.exception.EntityNotFoundException;
import rs.ac.bg.fon.silab.mapper.CompanyMapper;
import rs.ac.bg.fon.silab.service.CompanyService;
import rs.ac.bg.fon.silab.validator.DTOValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DTOValidator<CompanySaveDTO> dtoValidator;

    @Override
    @Transactional
    public void insert(CompanySaveDTO companySaveDTO) {
        dtoValidator.validate(companySaveDTO);
        companyDao.insert(companyMapper.companySaveDTOToCompany(companySaveDTO));
    }

    @Override
    @Transactional
    public void update(CompanySaveDTO companySaveDTO, Integer id) {
        Company company = companyDao.getById(id);

        if (company != null) {
            dtoValidator.validate(companySaveDTO, id);

            company.setName(companySaveDTO.getName());
            company.setTaxIdNumber(companySaveDTO.getTaxIdNumber());
        } else {
            throw new EntityNotFoundException("company.not.found");
        }
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Company company = companyDao.getById(id);

        if (company != null) {
            companyDao.delete(company);
        } else {
            throw new EntityNotFoundException("company.not.found");
        }
    }

    @Override
    @Transactional
    public CompanyGetDTO getById(Integer id) {
        Company company = companyDao.getById(id);

        if (company != null) {
            return companyMapper.companyToCompanyGetDTO(company);
        }

        throw new EntityNotFoundException("company.not.found");
    }

    @Override
    @Transactional
    public List<CompanyGetDTO> getAll() {
        return companyDao.getAll().stream()
                .map(company -> companyMapper.companyToCompanyGetDTO(company))
                .collect(Collectors.toList());
    }

}
