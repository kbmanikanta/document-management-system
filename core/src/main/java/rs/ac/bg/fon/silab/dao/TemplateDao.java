package rs.ac.bg.fon.silab.dao;

import rs.ac.bg.fon.silab.entity.Template;
import rs.ac.bg.fon.silab.entity.TemplateItem;
import rs.ac.bg.fon.silab.entity.TemplateState;

import java.util.List;

public interface TemplateDao extends AbstractDao<Template> {

    TemplateItem getTemplateItemById(Integer id);

    List<Template> getByState(TemplateState state);
}
