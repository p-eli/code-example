package db.entity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestType.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Entity
@Table(name = "test_type")
public class TestType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Toto pole je povinné")
    @Size(max = 25, min = 1, message = "Název musí mít délku od {min} do {max} znaků")
    private String name;

    @NotNull(message = "Toto pole je povinné")
    @DecimalMin(value = "1", message = "Interval musí být kladné číslo větší než {value}")

    private int checkingInterval;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCheckingInterval() {
        return checkingInterval;
    }

    public void setCheckingInterval(int checkingInterval) {
        this.checkingInterval = checkingInterval;
    }

    @Override
    public String toString() {
        return name;
    }
}
