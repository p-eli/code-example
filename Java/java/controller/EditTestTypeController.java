package controller;

import db.dao.ITestTypeDAO;
import db.entity.TestType;
import ui.form.TestTypeForm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: EditTestTypeController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@RequestScoped
@Named
public class EditTestTypeController implements Serializable {
    @Inject
    TestTypeForm testTypeForm;

    @Inject
    ITestTypeDAO iTestTypeDAO;


    public void save() {
        iTestTypeDAO.merge(testTypeForm.getTestType());
    }

    public String showTestTypeEdit(int id) {
        testTypeForm.clear();
        testTypeForm.setTestTypeId(id);
        testTypeForm.setTestType(iTestTypeDAO.find(id));
        return "new-test-type";
    }

    public String showTestTypeEdit() {
        testTypeForm.clear();
        testTypeForm.setTestType(new TestType());
        return "new-test-type";
    }

}
