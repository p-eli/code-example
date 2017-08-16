package ui.filter;


import db.entity.TestType;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestTypeFilter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class TestTypeFilter implements Serializable {
    private int testTypeId = -1;
    private int checkingInterval = -1;

    public int getTestTypeId() {
        return testTypeId;
    }

    public void setTestTypeId(int testTypeId) {
        this.testTypeId = testTypeId;
    }

    public void setAll(TestType model) {
        checkingInterval = model.getCheckingInterval();
    }

    public void setCheckingInterval(int checkingInterval) {
        this.checkingInterval = checkingInterval;
    }

    public int getCheckingInterval() {
        return checkingInterval;
    }

    public void clear() {
        checkingInterval = -1;
    }
}
