package ui.filter;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

/**
 * Project: Airport
 * File: PlaneFilter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class PlaneFilter implements Serializable {
    private int planeTypeId = -1;
    private int flyingHoursMin = 0;
    private int flyingHoursMax = 0;
    private Date dataOfManufactureMin = null;
    private Date dataOfManufactureMax = null;

    public int getPlaneTypeId() {
        return planeTypeId;
    }

    public void setPlaneTypeId(int planeTypeId) {
        this.planeTypeId = planeTypeId;
    }


    public int getFlyingHoursMin() {
        return flyingHoursMin;
    }

    public void setFlyingHoursMin(int flyingHoursMin) {
        this.flyingHoursMin = flyingHoursMin;
    }

    public int getFlyingHoursMax() {
        return flyingHoursMax;
    }

    public void setFlyingHoursMax(int flyingHoursMax) {
        this.flyingHoursMax = flyingHoursMax;
    }

    public Date getDataOfManufactureMin() {
        return dataOfManufactureMin;
    }

    public void setDataOfManufactureMin(Date dataOfManufactureMin) {
        this.dataOfManufactureMin = dataOfManufactureMin;
    }

    public Date getDataOfManufactureMax() {
        return dataOfManufactureMax;
    }

    public void setDataOfManufactureMax(Date dataOfManufactureMax) {
        this.dataOfManufactureMax = dataOfManufactureMax;
    }


    public void clear() {
        flyingHoursMin = 0;
        flyingHoursMax = 0;
        dataOfManufactureMin = null;
        dataOfManufactureMax = null;
    }
}
