package db.dao;

import db.entity.Plane;
import db.entity.Test;
import ui.TestStatusEnum;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Project: Airport
 * File: ITestDAO.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public interface ITestDAO {
    public void persist(Test test);

    public void merge(Test test);

    public void delete(Test test);

    public Collection<Test> readAll();

    public Collection<Test> readByPlane(Plane plane);

    public Test actualByPlane(Plane plane);

    public List<Test> filterTests(int planeId, Date dateMin, Date dateMax, TestStatusEnum status, int technicianId);

    Test getById(int id);
}
