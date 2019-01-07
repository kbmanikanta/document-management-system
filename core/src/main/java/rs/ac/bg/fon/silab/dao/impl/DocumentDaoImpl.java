package rs.ac.bg.fon.silab.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.silab.dao.DocumentDao;
import rs.ac.bg.fon.silab.entity.Document;

import java.util.List;

@Repository
public class DocumentDaoImpl implements DocumentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void insert(Document document) {
        sessionFactory.getCurrentSession()
                .save(document);
    }

    @Override
    public void delete(Document document) {
        sessionFactory.getCurrentSession()
                .delete(document);
    }

    @Override
    public Document getById(Integer id) {
        return sessionFactory.getCurrentSession()
                .get(Document.class, id);
    }

    @Override
    public List<Document> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Document", Document.class)
                .getResultList();
    }

}
