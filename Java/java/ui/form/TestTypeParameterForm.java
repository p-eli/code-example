package ui.form;


import db.entity.TestType;
import db.entity.TestTypeParameter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestTypeParameterForm.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class TestTypeParameterForm implements Serializable {

    private Integer testTypeParameterID;

    private TestTypeParameter testTypeParameter;


    public Integer getTestTypeParameterId() {
        return testTypeParameterID;
    }

    public void setTestTypeParameterId(final Integer testTypeParameterID) {
        this.testTypeParameterID = testTypeParameterID;
    }

    public TestTypeParameter getTestTypeParameter() {
        return this.testTypeParameter;
    }

    public void setTestTypeParameter(final TestTypeParameter testTypeParameter, TestType testType) {
        this.testTypeParameter = testTypeParameter;
        this.testTypeParameter.setType(testType);
    }

    public void setTestTypeParameter(final TestTypeParameter testTypeParameter) {
        this.testTypeParameter = testTypeParameter;
    }

    public void clear() {
        testTypeParameterID = -1;
        testTypeParameter = null;
    }
}

