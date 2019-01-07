package rs.ac.bg.fon.silab.dao;

import rs.ac.bg.fon.silab.entity.Template;
import rs.ac.bg.fon.silab.entity.TemplateItem;

public interface TemplateDao extends AbstractDao<Template> {

    TemplateItem getTemplateItemById(Integer id);

}
