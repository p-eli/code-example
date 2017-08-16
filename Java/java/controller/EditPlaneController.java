package controller;

import db.dao.IPlaneDAO;
import db.dao.IPlaneModelDAO;
import db.entity.Plane;
import db.entity.PlaneModel;
import ui.MessagesHelper;
import ui.filter.PlaneModelFilter;
import ui.form.PlaneForm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Project: Airport
 * File: EditPlaneController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@RequestScoped
@Named
public class EditPlaneController implements Serializable {
    @Inject
    PlaneForm planeForm;

    @Inject
    IPlaneDAO iPlaneDAO;

    @Inject
    PlaneModelFilter planeModelFilter;

    @Inject
    IPlaneModelDAO iPlaneModelDAO;

    public boolean validateModel() {
        List<PlaneModel> values = iPlaneModelDAO.queryFilter(planeModelFilter.getManufacturer(), planeModelFilter.getType(), planeModelFilter.getCapacity(), planeModelFilter.getEnginePower());
        if ((values != null) && (values.size() == 1)) {
            planeForm.getPlane().setModel(values.get(0));
            return true;
        }
        return false;
    }


    public void save() {
        boolean newPlane = (planeForm.getPlane().getId() == 0);
        String msg = newPlane ? "Letadlo bylo vytvořeno" : "Letadlo bylo upraveno";
        iPlaneDAO.merge(planeForm.getPlane());
        MessagesHelper.ShowInfo(msg);
    }

    public String showPlaneEdit(int id) {
        planeForm.clear();
        planeForm.setPlaneId(id);
        Plane plane = iPlaneDAO.find(planeForm.getPlaneId());
        planeForm.setPlane(plane);
        planeModelFilter.setAll(plane.getModel());
        return "new-airplanes";
    }

    public String showPlaneEdit() {
        planeForm.clear();
        planeForm.setPlane(new Plane());
        planeModelFilter.clear();
        return "new-airplanes";
    }

}
