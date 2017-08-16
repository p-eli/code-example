package controller;

import db.dao.IPlaneModelDAO;
import db.datatable.PlaneModelDatatable;
import db.entity.PlaneModel;
import ui.filter.PlaneModelFilter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: PlaneModelController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@RequestScoped
@Named
public class PlaneModelController implements Serializable {

    @Inject
    IPlaneModelDAO iPlaneModelDAO;

    @Inject
    PlaneModelDatatable planeModelDatatable;

    @Inject
    PlaneModelFilter planeModelFilter;

    public void remove(PlaneModel planeModel) {
        try {
            iPlaneModelDAO.remove(planeModel);
            planeModelDatatable.refresh();
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Typ Letadla byl úspěšně odstraněn " + planeModel.getId()));
        } catch (Exception e1) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Typ Letadla nelze odstranit " + planeModel.getId()));
        }
    }

    public String showPlaneModel(int id) {
        planeModelFilter.clear();
        return "list-airplanes-model";
    }

    public String showPlaneModel() {
        planeModelFilter.clear();
        return "list-airplanes-model";
    }
}
