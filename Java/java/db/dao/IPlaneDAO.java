package db.dao;

import db.entity.Plane;

import java.util.Date;
import java.util.List;

/**
 * Project: Airport
 * File: IPlaneDAO.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public interface IPlaneDAO extends IEntityDAO<Plane> {
    public List<Plane> queryFilter(int planeTypeId,
                                   Date dataOfManufactureMin, Date dataOfManufactureMax,
                                   int flyingHoursMin, int flyingHoursMax
    );
}
