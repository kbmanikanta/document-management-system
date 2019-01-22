package rs.ac.bg.fon.silab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.dto.company.CompanyGetDto;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDto;
import rs.ac.bg.fon.silab.response.builder.ResponseBuilder;
import rs.ac.bg.fon.silab.service.CompanyService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody CompanySaveDto companySaveDto) {
        companyService.insert(companySaveDto);

        return responseBuilder.createResponse()
                .withStatus(HttpStatus.CREATED)
                .withMessageKey("company.insert.success")
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@RequestBody CompanySaveDto companySaveDto, @PathVariable Integer id) {
        companyService.update(companySaveDto, id);

        return responseBuilder.createResponse()
                .withStatus(HttpStatus.OK)
                .withMessageKey("company.update.success")
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        companyService.delete(id);

        return responseBuilder.createResponse()
                .withStatus(HttpStatus.OK)
                .withMessageKey("company.delete.success")
                .build();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyGetDto getById(@PathVariable Integer id) {
        return companyService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyGetDto> get(@RequestParam(name = "taxIdNumber", required = false) String taxIdNumber) {
        if (taxIdNumber != null) {
            return companyService.getByTaxIdNumber(taxIdNumber);
        }
        return companyService.getAll();
    }

}
