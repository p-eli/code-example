package db.repository;

import db.dao.IPlaneDAO;
import db.entity.Plane;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * Project: Airport
 * File: PlaneRepository.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Stateless
@Dependent
public class PlaneRepository implements IPlaneDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Plane> queryAll() {
        TypedQuery<Plane> query = entityManager.createQuery("select e from Plane e", Plane.class);
        return query.getResultList();
    }

    public List<Plane> queryFilter(int planeTypeId, Date dataOfManufactureMin, Date dataOfManufactureMax,
                                   int flyingHoursMin, int flyingHoursMax) {
        String where_item = "where ";
        if (planeTypeId != -1) {
            where_item += "e.model.id = :planeTypeId";
        }
        if (dataOfManufactureMin != null) {
            if (where_item != "where ")
                where_item += " and ";
            where_item += "e.dateOfManufacture >= :dataOfManufactureMin";
        }

        if (dataOfManufactureMax != null) {
            if (where_item != "where ")
                where_item += " and ";
            where_item += "e.dateOfManufacture <= :dataOfManufactureMax";
        }

        if (flyingHoursMin != 0) {
            if (where_item != "where ")
                where_item += " and ";
            where_item += "e.flyingHours >= :flyingHoursMin";
        }

        if (flyingHoursMax != 0) {
            if (where_item != "where ")
                where_item += " and ";
            where_item += "e.flyingHours <= :flyingHoursMax";
        }

        TypedQuery<Plane> query;
        if (where_item != "where ") {
            query = entityManager
                    .createQuery("select e from Plane e " + where_item, Plane.class);
        } else {
            query = entityManager
                    .createQuery("select e from Plane e ", Plane.class);
        }

        if (planeTypeId != -1) {
            query.setParameter("planeTypeId", planeTypeId);
        }
        if (dataOfManufactureMin != null) {
            query.setParameter("dataOfManufactureMin", dataOfManufactureMin);
        }
        if (dataOfManufactureMax != null) {
            query.setParameter("dataOfManufactureMax", dataOfManufactureMax);
        }

        if (flyingHoursMin != 0) {
            query.setParameter("flyingHoursMin", flyingHoursMin);
        }

        if (flyingHoursMax != 0) {
            query.setParameter("flyingHoursMax", flyingHoursMax);
        }


        return query.getResultList();
    }


    public Plane find(Integer id) {
        return entityManager.find(Plane.class, id);
    }

    public Plane merge(Plane plane) {
        return entityManager.merge(plane);
    }

    public void persist(Plane plane) {
        entityManager.persist(plane);
    }

    public void remove(Plane plane) {
        Plane attached = find(plane.getId());
        entityManager.remove(entityManager.contains(attached) ? attached : entityManager.merge(attached));
    }

}

