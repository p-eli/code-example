package ui.form;


import db.entity.TestType;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestTypeForm.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class TestTypeForm implements Serializable {

    private Integer testTypeID;

    private TestType testType;


    public Integer getTestTypeId() {
        return testTypeID;
    }

    public void setTestTypeId(final Integer testTypeID) {
        this.testTypeID = testTypeID;
    }

    public TestType getTestType() {
        return this.testType;
    }

    public void setTestType(final TestType testType) {
        this.testType = testType;
    }

    public void clear() {
        testTypeID = -1;
        testType = null;
    }
}

