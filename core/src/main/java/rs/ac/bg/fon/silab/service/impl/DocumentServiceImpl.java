package rs.ac.bg.fon.silab.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.silab.dao.DocumentDao;
import rs.ac.bg.fon.silab.dao.TemplateDao;
import rs.ac.bg.fon.silab.dto.document.DocumentDetailsDto;
import rs.ac.bg.fon.silab.dto.document.DocumentGetDto;
import rs.ac.bg.fon.silab.dto.document.DocumentSaveDto;
import rs.ac.bg.fon.silab.entity.Document;
import rs.ac.bg.fon.silab.exception.EntityNotFoundException;
import rs.ac.bg.fon.silab.mapper.DocumentMapper;
import rs.ac.bg.fon.silab.service.DocumentService;
import rs.ac.bg.fon.silab.validator.DtoValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDao documentDao;

    @Autowired
    private TemplateDao templateDao;

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private DtoValidator<DocumentSaveDto> dtoValidator;

    @Transactional
    @Override
    public void insert(DocumentSaveDto documentSaveDto) {
        dtoValidator.validate(documentSaveDto);
        documentDao.insert(documentMapper.toDocument(documentSaveDto));
    }

    @Transactional
    @Override
    public void update(DocumentSaveDto documentSaveDto, Integer id) {
        Document document = documentDao.getById(id);

        if (document != null) {
            dtoValidator.validate(documentSaveDto, id);

            document.setName(documentSaveDto.getName());
            document.setDocumentItems(documentMapper.toDocumentItems(documentSaveDto.getDocumentItems()));
            document.setTemplate(templateDao.getById(documentSaveDto.getTemplateId()));
        } else {
            throw new EntityNotFoundException("document.not.found");
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Document document = documentDao.getById(id);

        if (document != null) {
            documentDao.delete(document);
        } else {
            throw new EntityNotFoundException("document.not.found");
        }
    }

    @Transactional
    @Override
    public DocumentDetailsDto getById(Integer id) {
        Document document = documentDao.getById(id);

        if (document != null) {
            return documentMapper.toDocumentDetailsDto(document);
        }

        throw new EntityNotFoundException("document.not.found");
    }

    @Transactional
    @Override
    public List<DocumentGetDto> getAll() {
        return documentDao.getAll().stream()
                .map(document -> documentMapper.toDocumentGetDto(document))
                .collect(Collectors.toList());
    }

}
