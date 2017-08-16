package db.dao;

import db.entity.PlaneModel;

import java.util.List;

/**
 * Project: Airport
 * File: IPlaneModelDAO.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public interface IPlaneModelDAO extends IEntityDAO<PlaneModel> {
    public List<PlaneModel> queryFilter(String manufacturer, String type, int capacity, int enginePower);
}
