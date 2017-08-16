package db.dao;

import db.entity.Test;
import db.entity.TestResult;

import java.util.Collection;

/**
 * Project: Airport
 * File: ITestResultDAO.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
public interface ITestResultDAO extends IEntityDAO<TestResult> {
    Collection<TestResult> query(Test test);
}
