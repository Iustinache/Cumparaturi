package Repository;

import Domain.Entitate;

import java.util.ArrayList;
import java.util.List;

public class RepoMemory<T extends Entitate> implements IRepository<T> {
    protected List<T> entities = new ArrayList<T>();

    public RepoMemory() {
        entities = new ArrayList<T>();
    }

    @Override
    public boolean findById(int id) {
        for (T e : entities) {
            if (e.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(T entity) throws RepositoryException {
        if (findById(entity.getId())) {
            throw new DuplicateEntityException("Deja exista o entitate cu id-ul " + entity.getId() + "\n");
        }
        entities.add(entity);
    }

    @Override
    public void remove(T entity) throws Exception {
        if (!findById(entity.getId()))
            throw new Exception("Nu exista entitatea cu id-ul dat");

        entities.remove(entity);
    }

    @Override
    public void removeById(int id) {
        T entityToFind = findEntityById(id);
        // de adaugat exceptia
        entities.remove(entityToFind);
    }

    @Override
    public void update(T entity, T newEntity) throws Exception {
        int index = entities.indexOf(entity);
        if(index == -1)
            throw new Exception("Nu exista entitatea cu id-ul dat");
        entities.set(index, newEntity);
    }

    @Override
    public T findEntityById(int id) {
        for(T en: entities)
            if (en.getId() == id)
                return en;
        return null;
    }

    @Override
    public List<T> findAll() throws Exception{
        if(entities.isEmpty())
            throw new Exception("Nu exista nicio entitate stocata");
        return entities;
    }

    @Override
    public int getSize() {
        return entities.size();
    }

}
