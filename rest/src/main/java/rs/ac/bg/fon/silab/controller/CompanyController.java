package rs.ac.bg.fon.silab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.dto.company.CompanyGetDTO;
import rs.ac.bg.fon.silab.dto.company.CompanySaveDTO;
import rs.ac.bg.fon.silab.response.builder.ResponseBuilder;
import rs.ac.bg.fon.silab.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping(path = "/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody CompanySaveDTO companySaveDTO) {
        companyService.insert(companySaveDTO);

        return responseBuilder.createResponse()
                .withStatus(HttpStatus.CREATED)
                .withMessageKey("company.insert.success")
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@RequestBody CompanySaveDTO companySaveDTO, @PathVariable Integer id) {
        companyService.update(companySaveDTO, id);

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
    public CompanyGetDTO getById(@PathVariable Integer id) {
        return companyService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyGetDTO> getAll() {
        return companyService.getAll();
    }

}
