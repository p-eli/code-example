package controller;

import db.dao.IPlaneModelDAO;
import db.entity.PlaneModel;
import ui.MessagesHelper;
import ui.form.PlaneModelForm;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: EditPlaneModelController.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@RequestScoped
@Named
public class EditPlaneModelController implements Serializable {
    @Inject
    PlaneModelForm planeModelForm;

    @Inject
    IPlaneModelDAO iPlaneModelDAO;


    public void save() {
        boolean update = planeModelForm.getPlaneModel().getId() != 0;
        String msg = (update ? "Typ letadla byl upraven." : "Typ letadla byl vytvořen.");
        iPlaneModelDAO.merge(planeModelForm.getPlaneModel());
        MessagesHelper.ShowInfo(msg);
    }

    public String showPlaneModelEdit(int id) {
        planeModelForm.clear();
        planeModelForm.setPlaneModelId(id);
        planeModelForm.setPlaneModel(iPlaneModelDAO.find(id));
        return "new-airplanes-model";
    }

    public String showPlaneModelEdit() {
        planeModelForm.clear();
        planeModelForm.setPlaneModel(new PlaneModel());
        return "new-airplanes-model";
    }

}
