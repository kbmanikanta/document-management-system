package rs.ac.bg.fon.silab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.dto.template.TemplateDetailsDto;
import rs.ac.bg.fon.silab.dto.template.TemplateGetDto;
import rs.ac.bg.fon.silab.dto.template.TemplateSaveDto;
import rs.ac.bg.fon.silab.response.builder.ResponseBuilder;
import rs.ac.bg.fon.silab.service.TemplateService;

import java.util.List;

@RestController
@RequestMapping(path = "/templates")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody TemplateSaveDto templateSaveDto) {
        templateService.insert(templateSaveDto);

        return responseBuilder.createResponse()
                .withStatus(HttpStatus.CREATED)
                .withMessageKey("template.insert.success")
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@RequestBody TemplateSaveDto templateSaveDto, @PathVariable Integer id) {
        templateService.update(templateSaveDto, id);

        return responseBuilder.createResponse()
                .withStatus(HttpStatus.OK)
                .withMessageKey("template.update.success")
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        templateService.delete(id);

        return responseBuilder.createResponse()
                .withStatus(HttpStatus.OK)
                .withMessageKey("template.delete.success")
                .build();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TemplateDetailsDto getById(@PathVariable Integer id) {
        return templateService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TemplateGetDto> getAll() {
        return templateService.getAll();
    }

}
