package controller;

import db.dao.ITestTypeDAO;
import db.dao.ITestTypeParameterDAO;
import db.entity.TestType;
import db.entity.TestTypeParameter;
import ui.filter.TestTypeFilter;
import ui.form.TestTypeParameterForm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Project: Airport
 * File: EditTestTypeParameterController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@RequestScoped
@Named
public class EditTestTypeParameterController implements Serializable {
    @Inject
    TestTypeParameterForm testTypeParameterForm;

    @Inject
    ITestTypeParameterDAO iTestTypeParameterDAO;

    @Inject
    TestTypeFilter testTypeFilter;

    @Inject
    ITestTypeDAO iTestTypeDAO;


    public void save() {
        iTestTypeParameterDAO.merge(testTypeParameterForm.getTestTypeParameter());
    }

    public String showTestTypeParameterEdit(int id) {
        testTypeParameterForm.clear();
        testTypeParameterForm.setTestTypeParameterId(id);
        testTypeParameterForm.setTestTypeParameter(iTestTypeParameterDAO.find(id));
        return "new-test-type-parameter";
    }

    public String showTestTypeParameterEdit(int typeId, int id) {
        testTypeParameterForm.clear();
        testTypeParameterForm.setTestTypeParameter(new TestTypeParameter(), iTestTypeDAO.find(typeId));
        return "new-test-type-parameter";
    }

}
