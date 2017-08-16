package ui.filter;


import ui.TestStatusEnum;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

/**
 * Project: Airport
 * File: TestFilter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class TestFilter implements Serializable {
    private int planeId = -1;
    private Date dateMin = null;
    private Date dateMax = null;
    private TestStatusEnum status = null;
    private int technician = -1;

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public boolean isForMultiplePlanes() {
        return planeId == -1;
    }

    public Date getDateMin() {
        return dateMin;
    }

    public void setDateMin(Date dateMin) {
        this.dateMin = dateMin;
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
    }

    public TestStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TestStatusEnum status) {
        this.status = status;
    }

    public void clear() {
        dateMin = null;
        dateMax = null;
        status = null;
        technician = -1;
    }

    public int getTechnician() {
        return technician;
    }

    public void setTechnician(int technician) {
        this.technician = technician;
    }
}
