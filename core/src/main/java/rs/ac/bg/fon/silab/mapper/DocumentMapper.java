package rs.ac.bg.fon.silab.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dao.TemplateDao;
import rs.ac.bg.fon.silab.dto.document.DocumentDetailsDto;
import rs.ac.bg.fon.silab.dto.document.DocumentGetDto;
import rs.ac.bg.fon.silab.dto.document.DocumentSaveDto;
import rs.ac.bg.fon.silab.dto.document.item.DocumentItemGetDto;
import rs.ac.bg.fon.silab.dto.document.item.DocumentItemSaveDto;
import rs.ac.bg.fon.silab.entity.Document;
import rs.ac.bg.fon.silab.entity.DocumentItem;
import rs.ac.bg.fon.silab.entity.TemplateItemType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocumentMapper {

    @Autowired
    private TemplateMapper templateMapper;

    @Autowired
    private TemplateDao templateDao;

    public DocumentGetDto toDocumentGetDto(Document document) {
        return new DocumentGetDto(
                document.getName(),
                document.getId(),
                templateMapper.toTemplateGetDto(document.getTemplate())
        );
    }

    public DocumentDetailsDto toDocumentDetailsDto(Document document) {
        return new DocumentDetailsDto(
                document.getName(),
                document.getId(),
                templateMapper.toTemplateGetDto(document.getTemplate()),
                toDocumentItemGetDtos(document.getDocumentItems())
        );
    }

    public Document toDocument(DocumentSaveDto documentSaveDto) {
        return new Document(
                documentSaveDto.getName(),
                toDocumentItems(documentSaveDto.getDocumentItems()),
                templateDao.getById(documentSaveDto.getTemplateId())
        );
    }

    public List<DocumentItemGetDto> toDocumentItemGetDtos(List<DocumentItem> documentItems) {
        return documentItems.stream().map(this::toDocumentItemGetDto).collect(Collectors.toList());
    }

    public List<DocumentItem> toDocumentItems(List<DocumentItemSaveDto> documentItemSaveDtos) {
        return documentItemSaveDtos.stream().map(this::toDocumentItem).collect(Collectors.toList());
    }

    public DocumentItemGetDto toDocumentItemGetDto(DocumentItem documentItem) {
        return new DocumentItemGetDto(
                documentItem.getIntegerValue(),
                documentItem.getDoubleValue(),
                documentItem.getStringValue(),
                documentItem.getBooleanValue(),
                documentItem.getDateValue() == null ?
                        null : new SimpleDateFormat("dd/MM/yyyy").format(documentItem.getDateValue()),
                templateMapper.toTemplateItemDto(documentItem.getTemplateItem())
        );
    }

    public DocumentItem toDocumentItem(DocumentItemSaveDto documentItemSaveDto) {
        TemplateItemType templateItemType = templateDao.getTemplateItemById(documentItemSaveDto.getTemplateItemId()).getType();

        try {
            return new DocumentItem(
                    templateItemType == TemplateItemType.INTEGER ? documentItemSaveDto.getIntegerValue() : null,
                    templateItemType == TemplateItemType.DOUBLE ? documentItemSaveDto.getDoubleValue() : null,
                    templateItemType == TemplateItemType.STRING && documentItemSaveDto.getStringValue() != null
                            && !documentItemSaveDto.getStringValue().equals("") ?
                            documentItemSaveDto.getStringValue() : null,
                    templateItemType == TemplateItemType.BOOLEAN ? documentItemSaveDto.getBooleanValue() : null,
                    templateItemType == TemplateItemType.DATE && documentItemSaveDto.getDateValue() != null
                            && !documentItemSaveDto.getDateValue().equals("") ?
                            new SimpleDateFormat("dd/MM/yyyy").parse(documentItemSaveDto.getDateValue()) : null,
                    templateDao.getTemplateItemById(documentItemSaveDto.getTemplateItemId())
            );
        } catch (ParseException e) {
            throw new RuntimeException();
        }
    }

}
