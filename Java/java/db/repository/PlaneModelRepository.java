package db.repository;

import db.dao.IPlaneModelDAO;
import db.entity.PlaneModel;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Project: Airport
 * File: PlaneModelRepository.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Stateless
@Dependent
public class PlaneModelRepository implements IPlaneModelDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<PlaneModel> queryAll() {
        TypedQuery<PlaneModel> query = entityManager.createQuery("select e from PlaneModel e", PlaneModel.class);
        return query.getResultList();
    }

    public List<PlaneModel> queryFilter(String manufacturer, String type, int capacity, int enginePower) {
        try {
            String where_item = "where ";
            if (manufacturer != null) {
                where_item += "e.manufacturer = :manufacturer";
            }
            if (type != null) {
                if (where_item != "where ")
                    where_item += " and ";
                where_item += "e.type = :type";
            }
            if (capacity != -1) {
                if (where_item != "where ")
                    where_item += " and ";
                where_item += "e.capacity = :capacity";
            }
            if (enginePower != -1) {
                if (where_item != "where ")
                    where_item += " and ";
                where_item += "e.enginePower = :enginePower";
            }

            TypedQuery<PlaneModel> query;
            if (where_item != "where ") {
                query = entityManager
                        .createQuery("select e from PlaneModel e " + where_item, PlaneModel.class);
            } else {
                query = entityManager
                        .createQuery("select e from PlaneModel e ", PlaneModel.class);
            }

            if (manufacturer != null) {
                query.setParameter("manufacturer", manufacturer);
            }
            if (type != null) {
                query.setParameter("type", type);
            }
            if (capacity != -1) {
                query.setParameter("capacity", capacity);
            }
            if (enginePower != -1) {
                query.setParameter("enginePower", enginePower);
            }

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public PlaneModel find(Integer id) {
        return entityManager.find(PlaneModel.class, id);
    }

    public PlaneModel merge(PlaneModel planeModel) {
        return entityManager.merge(planeModel);
    }

    public void persist(PlaneModel planeModel) {
        entityManager.persist(planeModel);
    }

    public void remove(PlaneModel planeModel) {
        PlaneModel attached = find(planeModel.getId());
        entityManager.remove(entityManager.contains(attached) ? attached : entityManager.merge(attached));
    }

}
