
package db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Project: Airport
 * File: Test.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Entity
@Table(name = "test")
public class Test implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Toto pole je povinné")
    @Temporal(TemporalType.DATE)
    private Date date = new Date();


    @NotNull(message = "Toto pole je povinné")
    @ManyToOne()
    private User technician;

    @NotNull(message = "Toto pole je povinné")
    @ManyToOne()
    private Plane plane;

    @NotNull(message = "Toto pole je povinné")
    @ManyToOne()
    private TestType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getTechnician() {
        return technician;
    }

    public void setTechnician(User technician) {
        this.technician = technician;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public TestType getType() {
        return type;
    }

    public void setType(TestType type) {
        this.type = type;
    }
}
