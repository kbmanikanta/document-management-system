package rs.ac.bg.fon.silab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.dto.document.DocumentDetailsDto;
import rs.ac.bg.fon.silab.dto.document.DocumentGetDto;
import rs.ac.bg.fon.silab.dto.document.DocumentSaveDto;
import rs.ac.bg.fon.silab.response.builder.ResponseBuilder;
import rs.ac.bg.fon.silab.service.DocumentService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ResponseBuilder responseBuilder;

    @PostMapping
    public ResponseEntity<Object> insert(@RequestBody DocumentSaveDto documentSaveDto) {
        documentService.insert(documentSaveDto);

        return responseBuilder.createResponse()
                .withStatus(HttpStatus.CREATED)
                .withMessageKey("document.insert.success")
                .build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@RequestBody DocumentSaveDto documentSaveDto, @PathVariable Integer id) {
        documentService.update(documentSaveDto, id);

        return responseBuilder.createResponse()
                .withStatus(HttpStatus.OK)
                .withMessageKey("document.update.success")
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        documentService.delete(id);

        return responseBuilder.createResponse()
                .withStatus(HttpStatus.OK)
                .withMessageKey("document.delete.success")
                .build();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentDetailsDto getById(@PathVariable Integer id) {
        return documentService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentGetDto> get() {
        return documentService.getAll();
    }

}
