package controller;

import auth.AuthenticationBean;
import db.datatable.*;
import db.entity.*;
import ui.MessagesHelper;
import ui.filter.PlaneFilter;
import ui.form.FilledTestForm;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class TestCreateController implements Serializable {

    @Inject
    TestDatatable testDatatable;

    @Inject
    TestTypeDatatable testTypeDatatable;

    @Inject
    TestResultDatatable testResultDatatable;

    @Inject
    PlaneDatatable planeDatatable;

    @Inject
    PlaneModelDatatable planeModelDatatable;

    @Inject
    PlaneFilter filter;

    @Inject
    FilledTestForm form;

    @Inject
    TestController testController;

    @Inject
    TestTypeParameterDatatable testTypeParameterDatatable;


    private List<TestResult> loadTestItems(int typeId) {
        if (form.isUpdate()) {
            return new ArrayList<TestResult>(testResultDatatable.getResults(form.getTest()));
        }

        List<TestResult> list = new ArrayList<TestResult>();

        for (TestTypeParameter param : testTypeParameterDatatable.getFor(typeId)) {
            TestResult res = new TestResult();
            res.setCheckingValue(param);
            list.add(res);
        }
        return list;
    }

    public String save() {

        Test test = form.getTest();
        test.setTechnician(getAuthBean().getUser());

        Plane plane = new Plane();
        plane.setId(form.getPlaneId());
        test.setPlane(plane);

        TestType type = new TestType();
        type.setId(form.getTypeId());
        test.setType(type);
        if (form.isUpdate()) {
            testDatatable.merge(test, form.getParamList());
            MessagesHelper.ShowInfo("Test upraven");
        } else {
            testDatatable.persist(test, form.getParamList());
            MessagesHelper.ShowInfo("Test vytvořen");
        }
        return testController.showTests();
    }

    public String create() {
        clear();
        return "fill-test";
    }

    public String create(Plane plane) {
        clear();
        form.setPlaneId(plane.getId());
        filter.setPlaneTypeId(plane.getModel().getId());
        return "fill-test";
    }

    public void selectTest() {
        form.setParamList(loadTestItems(form.getTypeId()));
    }

    private AuthenticationBean getAuthBean() {
        return (AuthenticationBean) (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("authenticationBean"));
    }

    private void clear() {
        form.clear();
        form.setUser(getAuthBean().getUser());
        filter.clear();
        filter.setPlaneTypeId(-1);

    }

    public String update(Test test) {
        clear();
        form.setUpdate(true);
        form.setPlaneId(test.getPlane().getId());
        form.setTypeId(test.getType().getId());
        form.setUser(test.getTechnician());
        form.setTest(test);
        filter.setPlaneTypeId(test.getPlane().getModel().getId());
        prerender();
        return "fill-test";
    }

    public void prerender() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            if (form.getPlaneId() < 0) {
                List<PlaneModel> models = planeModelDatatable.getAllValues();
                if (models.size() > 0) {
                    filter.setPlaneTypeId(models.get(0).getId());
                }
            }
        }
        selectTest();
    }

}