package db.dao;

import java.util.List;

/**
 * Project: Airport
 * File: IEntityDAO.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public interface IEntityDAO<T> {
    public T find(Integer id);

    public T merge(T obj);

    public void persist(T obj);

    public void remove(T obj);

    public List<T> queryAll();

}
