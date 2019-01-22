package rs.ac.bg.fon.silab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.entity.Company;
import rs.ac.bg.fon.silab.dao.CompanyDao;
import rs.ac.bg.fon.silab.dto.company.CompanyGetDto;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDto;
import rs.ac.bg.fon.silab.exception.EntityNotFoundException;
import rs.ac.bg.fon.silab.mapper.CompanyMapper;
import rs.ac.bg.fon.silab.service.CompanyService;
import rs.ac.bg.fon.silab.validator.DtoValidator;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DtoValidator<CompanySaveDto> dtoValidator;

    @Transactional
    @Override
    public void insert(CompanySaveDto companySaveDto) {
        dtoValidator.validate(companySaveDto);
        companyDao.insert(companyMapper.toCompany(companySaveDto));
    }

    @Transactional
    @Override
    public void update(CompanySaveDto companySaveDto, Integer id) {
        Company company = companyDao.getById(id);

        if (company != null) {
            dtoValidator.validate(companySaveDto, id);

            company.setName(companySaveDto.getName());
            company.setTaxIdNumber(companySaveDto.getTaxIdNumber());
        } else {
            throw new EntityNotFoundException("company.not.found");
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Company company = companyDao.getById(id);

        if (company != null) {
            companyDao.delete(company);
        } else {
            throw new EntityNotFoundException("company.not.found");
        }
    }

    @Transactional
    @Override
    public CompanyGetDto getById(Integer id) {
        Company company = companyDao.getById(id);

        if (company != null) {
            return companyMapper.toCompanyGetDto(company);
        }

        throw new EntityNotFoundException("company.not.found");
    }

    @Transactional
    @Override
    public List<CompanyGetDto> getAll() {
        return companyDao.getAll().stream()
                .map(company -> companyMapper.toCompanyGetDto(company))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<CompanyGetDto> getByTaxIdNumber(String taxIdNumber) {
        return companyDao.getByTaxIdNumber(taxIdNumber).stream()
                .map(company -> companyMapper.toCompanyGetDto(company))
                .collect(Collectors.toList());
    }
}
