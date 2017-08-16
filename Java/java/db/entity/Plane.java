
package db.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * Project: Airport
 * File: Plane.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Entity
@Table(name = "plane")
public class Plane implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(max = 25, min = 1, message = "Sériové číslo musí mít délku od {min} do {max} znaků")
    private String serialNumber;

    @NotNull(message = "Toto pole je povinné")
    @Temporal(TemporalType.DATE)
    private Date dateOfManufacture;

    @NotNull
    @ManyToOne()
    private PlaneModel model;

    @NotNull(message = "Toto pole je povinné")
    @DecimalMin(value = "0", message = "Letové hodiny musí být kladné číslo")
    private int flyingHours;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getDateOfManufacture() {
        return dateOfManufacture;
    }

    public void setDateOfManufacture(Date dateOfManufacture) {
        this.dateOfManufacture = dateOfManufacture;
    }

    public int getFlyingHours() {
        return flyingHours;
    }

    public void setFlyingHours(int flyingHours) {
        this.flyingHours = flyingHours;
    }

    public PlaneModel getModel() {
        return model;
    }

    public void setModel(PlaneModel model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return serialNumber;
    }

}

