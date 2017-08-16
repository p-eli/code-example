package controller;

import db.dao.ITestTypeParameterDAO;
import db.datatable.TestTypeParameterDatatable;
import db.entity.TestTypeParameter;
import ui.filter.TestTypeParameterFilter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestTypeParameterController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@RequestScoped
@Named
public class TestTypeParameterController implements Serializable {

    @Inject
    ITestTypeParameterDAO iTestTypeParameterDAO;

    @Inject
    TestTypeParameterDatatable testTypeParameterDatatable;

    @Inject
    TestTypeParameterFilter testTypeParameterFilter;


    public void remove(TestTypeParameter testTypeParameter) {
        try {
            iTestTypeParameterDAO.remove(testTypeParameter);
            testTypeParameterDatatable.refresh();
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Parametr testu byl úspěšně odstraněn " + testTypeParameter.getId()));
        } catch (Exception e1) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Parametr testu nelze odstranit " + testTypeParameter.getId()));
        }
    }

    public String showTestTypeParameter() {
        testTypeParameterFilter.clear();
        testTypeParameterFilter.setTestTypeId(-1);
        return "list-test-type-parameter";
    }

    public String showTestTypeParameter(int id) {
        testTypeParameterFilter.setTestTypeId(id);
        return "list-test-type-parameter";
    }
}
