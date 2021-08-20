package database.Dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T> {

    public void save(T entity);

    public void update(T entity);

    //public T findById(Id id);

    public List<T> findAll();
}

