package ui.form;

import db.entity.Test;
import db.entity.TestResult;
import db.entity.User;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

/**
 * Project: Airport
 * File: FilledTestForm.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class FilledTestForm implements Serializable {

    private Test test = new Test();
    private int typeId;
    private int planeId;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private boolean update = false;

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    private Collection<TestResult> paramList;


    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public Collection<TestResult> getParamList() {
        return paramList;
    }

    public void setParamList(Collection<TestResult> paramList) {
        this.paramList = paramList;
    }

    public void clear() {
        this.paramList = null;
        planeId = -1;
        typeId = -1;
        update = false;
        user = null;
        test = new Test();
    }
}

