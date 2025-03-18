package Repository;

import Domain.Entitate;

import java.util.List;

public interface IRepository<T extends Entitate> {

    public boolean findById(int id);

    public void add(T entity) throws Exception;

    public void remove(T entity) throws Exception;

    public void removeById(int id) throws RepositoryException;

    public void update(T entity, T newEntity) throws Exception;

    public T findEntityById(int id);

    public List<T> findAll() throws Exception;

    public int getSize();

}