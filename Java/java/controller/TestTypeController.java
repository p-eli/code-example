package controller;

import db.dao.ITestTypeDAO;
import db.datatable.TestTypeDatatable;
import db.entity.TestType;
import ui.filter.TestTypeFilter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestTypeController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@RequestScoped
@Named
public class TestTypeController implements Serializable {

    @Inject
    ITestTypeDAO iTestTypeDAO;

    @Inject
    TestTypeDatatable testTypeDatatable;

    @Inject
    TestTypeFilter testTypeFilter;

    public void remove(TestType testType) {
        try {
            iTestTypeDAO.remove(testType);
            testTypeDatatable.refresh();
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(String.format("Šablona testu #%d byla úspěšně odstraněna ", testType.getId())));
        } catch (Exception e1) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(String.format("Šablonu testu #%d nelze odstranit ", testType.getId())));
        }
    }

    public String showTestType() {
        testTypeFilter.clear();
        testTypeFilter.setTestTypeId(-1);
        return "list-test-types";
    }

    public String showTestType(int id) {
        testTypeFilter.clear();
        testTypeFilter.setTestTypeId(id);
        return "list-test-types";
    }
}
