package rs.ac.bg.fon.silab.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.silab.dao.TemplateDao;
import rs.ac.bg.fon.silab.entity.Template;

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
}
