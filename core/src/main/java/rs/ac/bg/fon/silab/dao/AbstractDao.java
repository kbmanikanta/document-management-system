package rs.ac.bg.fon.silab.dao;

import java.util.List;

public interface AbstractDao<Entity> {

    void insert(Entity entity);

    void delete(Entity entity);

    Entity getById(Integer id);

    List<Entity> getAll();

}
