package rs.ac.bg.fon.silab.service;

import rs.ac.bg.fon.silab.dto.document.DocumentDetailsDto;
import rs.ac.bg.fon.silab.dto.document.DocumentGetDto;
import rs.ac.bg.fon.silab.dto.document.DocumentSaveDto;

public interface DocumentService extends AbstractService<DocumentSaveDto, DocumentGetDto, DocumentDetailsDto> {}
