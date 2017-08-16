
package db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestResult.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@Entity
@Table(name = "test_result")
public class TestResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    private Test test;

    @ManyToOne()
    private TestTypeParameter checkingValue;

    @NotNull(message = "Toto pole je povinné")
    private int resultValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public TestTypeParameter getCheckingValue() {
        return checkingValue;
    }

    public void setCheckingValue(TestTypeParameter checkingValue) {
        this.checkingValue = checkingValue;
    }

    public int getResultValue() {
        return resultValue;
    }

    public void setResultValue(int resultValue) {
        this.resultValue = resultValue;
    }

    public boolean isValid() {
        int min = checkingValue.getMin();
        int max = checkingValue.getMax();
        return resultValue >= min && resultValue <= max;
    }
}
