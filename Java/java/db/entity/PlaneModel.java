/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Project: Airport
 * File: PlaneModel.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Entity
@Table(name = "plane_model")
public class PlaneModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Toto pole je povinné")
    @Size(min = 1, max = 25, message = "Délka výrobce musí být v rozmezí od {min} do {max} znaků")
    private String manufacturer;

    @NotNull(message = "Toto pole je povinné")
    @Size(min = 1, max = 25, message = "Délka typu musí být v rozmezí od {min} do {max} znaků")
    private String type;

    @NotNull(message = "Toto pole je povinné")
    @DecimalMin(value = "0", message = "Kapacita musí být kladné číslo")
    private int capacity;

    @NotNull(message = "Toto pole je povinné")
    @DecimalMin(value = "0", message = "Výkon musí být kladné číslo")
    private int enginePower;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    @Override
    public String toString() {
        return type;
    }
}
