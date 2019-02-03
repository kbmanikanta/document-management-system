package rs.ac.bg.fon.silab.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.silab.dao.TemplateDao;
import rs.ac.bg.fon.silab.entity.Template;
import rs.ac.bg.fon.silab.entity.TemplateItem;
import rs.ac.bg.fon.silab.entity.TemplateState;

import java.util.List;

@Repository
public class TemplateDaoImpl implements TemplateDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void insert(Template template) {
        sessionFactory.getCurrentSession()
                .save(template);
    }

    @Override
    public void delete(Template template) {
        sessionFactory.getCurrentSession()
                .delete(template);
    }

    @Override
    public Template getById(Integer id) {
        return sessionFactory.getCurrentSession()
                .get(Template.class, id);
    }

    @Override
    public List<Template> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Template", Template.class)
                .getResultList();
    }

    @Override
    public List<Template> getByState(TemplateState state) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Template where state=:state", Template.class)
                .setParameter("state", state)
                .getResultList();
    }

    @Override
    public TemplateItem getTemplateItemById(Integer id) {
        return sessionFactory.getCurrentSession()
                .get(TemplateItem.class, id);
    }

}
