package controller;

import db.dao.IPlaneDAO;
import db.datatable.PlaneDatatable;
import db.entity.Plane;
import ui.filter.PlaneFilter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: PlaneController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@RequestScoped
@Named
public class PlaneController implements Serializable {

    @Inject
    IPlaneDAO iPlaneDAO;

    @Inject
    PlaneDatatable planeDatatable;

    @Inject
    PlaneFilter planeFilter;

    public void remove(Plane plane) {
        try {
            iPlaneDAO.remove(plane);
            planeDatatable.refresh();
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Letadlo bylo úspěšně odstraněn " + plane.getId()));
        } catch (Exception e1) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Letadlo nelze odstranit " + plane.getId()));
        }
    }

    public String cleanPlane() {
        planeFilter.clear();
        planeDatatable.refresh();
        return "list-airplanes";
    }

    public String showPlane() {
        planeFilter.clear();
        planeFilter.setPlaneTypeId(-1);
        return "list-airplanes";
    }

    public String showPlane(int id) {
        planeFilter.clear();
        planeFilter.setPlaneTypeId(id);
        return "list-airplanes";
    }
}
