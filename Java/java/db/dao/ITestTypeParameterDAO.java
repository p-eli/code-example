package db.dao;

import db.entity.TestType;
import db.entity.TestTypeParameter;

import java.util.List;

/**
 * Project: Airport
 * File: ITestTypeParameterDAO.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public interface ITestTypeParameterDAO extends IEntityDAO<TestTypeParameter> {
    public List<TestTypeParameter> queryType(int typeId);

    public List<TestTypeParameter> queryFilter(TestType type, String checkingInterval, int min, int max);
}
