package db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestTypeParameter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Entity
@Table(name = "test_type_parameter")
public class TestTypeParameter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    private TestType type;

    @NotNull
    @Size(max = 50, min = 1, message = "Kontrolovaná hodnota musí mít délku od {min} do {max} znaků")
    private String checkingValue;

    @NotNull(message = "Toto pole je povinné")
    private int min;

    @NotNull(message = "Toto pole je povinné")
    private int max;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TestType getType() {
        return type;
    }

    public void setType(TestType type) {
        this.type = type;
    }

    public String getCheckingValue() {
        return checkingValue;
    }

    public void setCheckingValue(String checkingValue) {
        this.checkingValue = checkingValue;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public String toString() {
        String s = checkingValue;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
