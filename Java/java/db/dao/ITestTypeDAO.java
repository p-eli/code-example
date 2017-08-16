package db.dao;

import db.entity.TestType;

import java.util.List;

/**
 * Project: Airport
 * File: ITestTypeDAO.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public interface ITestTypeDAO extends IEntityDAO<TestType> {
    public List<TestType> queryFilter(int checkingInterval);
}
