package rs.ac.bg.fon.silab.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.bg.fon.silab.dao.TemplateDao;
import rs.ac.bg.fon.silab.dto.document.DocumentSaveDto;
import rs.ac.bg.fon.silab.dto.document.item.DocumentItemSaveDto;
import rs.ac.bg.fon.silab.entity.Template;
import rs.ac.bg.fon.silab.entity.TemplateItem;
import rs.ac.bg.fon.silab.entity.TemplateState;
import rs.ac.bg.fon.silab.exception.ValidationException;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DocumentSaveDtoValidator extends DtoValidator<DocumentSaveDto> {

    @Autowired
    private TemplateDao templateDao;

    @Override
    protected void customValidation(DocumentSaveDto documentSaveDto) {
        Template template = templateDao.getById(documentSaveDto.getTemplateId());

        if (template == null) {
            throw new ValidationException(getError("document.template.id.not.found",
                    new Object[] { documentSaveDto.getTemplateId() }, "templateId"));
        }

        if (template.getState() == TemplateState.DRAFT) {
            throw new ValidationException(getError("template.state.not.completed", "state"));
        }

        // Check if user has sent all template items from appropriate template
        List<Integer> templateItemIdsFromTemplate = template.getTemplateItems().stream()
                .map(TemplateItem::getId)
                .collect(Collectors.toList());
        List<Integer> templateItemIdsFromDocument = documentSaveDto.getDocumentItems().stream()
                .map(DocumentItemSaveDto::getTemplateItemId)
                .collect(Collectors.toList());

        if (!templateItemIdsFromTemplate.containsAll(templateItemIdsFromDocument)) {
            throw new ValidationException(getError("document.item.template.item.id.not.found", "documentItems"));
        }

        // Check if user hasn't sent document items where mandatory property is true
        List<Integer> mandatoryTemplateItemIdsFromTemplate = template.getTemplateItems().stream()
                .filter(TemplateItem::getMandatory)
                .map(TemplateItem::getId)
                .collect(Collectors.toList());

        if (!templateItemIdsFromDocument.containsAll(mandatoryTemplateItemIdsFromTemplate)) {
            throw new ValidationException(getError("document.item.mandatory", "documentItems"));
        }

        // Check if user has sent single document items where the multiple property is false
        Set<Integer> templateItemIdsFromDocumentTemp = new HashSet<>();
        Set<Integer> multipleTemplateItemIdsFromDocument = templateItemIdsFromDocument.stream()
                .filter(templateItemId -> !templateItemIdsFromDocumentTemp.add(templateItemId))
                .collect(Collectors.toSet());

        List<Integer> multipleTemplateItemIdsFromTemplate = template.getTemplateItems().stream()
                .filter(TemplateItem::getMultiple)
                .map(TemplateItem::getId)
                .collect(Collectors.toList());

        if (!multipleTemplateItemIdsFromTemplate.containsAll(multipleTemplateItemIdsFromDocument)) {
            throw new ValidationException(getError("document.item.not.multiple", "documentItems"));
        }

        // Check if mandatory document item has appropriate value type
        documentSaveDto.getDocumentItems().forEach(documentItem -> {
            TemplateItem templateItem = templateDao.getTemplateItemById(documentItem.getTemplateItemId());

            if (templateItem.getMandatory()) {
                switch (templateItem.getType()) {
                    case INTEGER:
                        if (documentItem.getIntegerValue() == null) {
                            throw new ValidationException(getError("document.item.mandatory", "documentItems"));
                        }
                        break;
                    case DOUBLE:
                        if (documentItem.getDoubleValue() == null) {
                            throw new ValidationException(getError("document.item.mandatory", "documentItems"));
                        }
                        break;
                    case STRING:
                        if (documentItem.getStringValue() == null || documentItem.getStringValue().equals("")) {
                            throw new ValidationException(getError("document.item.mandatory", "documentItems"));
                        }
                        break;
                    case BOOLEAN:
                        if (documentItem.getBooleanValue() == null) {
                            throw new ValidationException(getError("document.item.mandatory", "documentItems"));
                        }
                        break;
                    case DATE:
                        if (documentItem.getDateValue() == null || documentItem.getDateValue().equals("")) {
                            throw new ValidationException(getError("document.item.mandatory", "documentItems"));
                        }
                        break;
                }
            }
        });
    }


    @Override
    protected void customValidation(DocumentSaveDto documentSaveDto, Integer id) {
        customValidation(documentSaveDto);
    }

}
