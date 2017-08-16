package ui.filter;


import db.entity.PlaneModel;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: PlaneModelFilter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class PlaneModelFilter implements Serializable {
    private String manufacturer = null;
    private String type = null;
    private int capacity = -1;
    private int enginePower = -1;

    public void setAll(PlaneModel model) {
        manufacturer = model.getManufacturer();
        type = model.getType();
        capacity = model.getCapacity();
        enginePower = model.getEnginePower();
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
        type = null;
        capacity = enginePower = -1;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void clear() {
        manufacturer = null;
        type = null;
        capacity = -1;
        enginePower = -1;
    }
}
