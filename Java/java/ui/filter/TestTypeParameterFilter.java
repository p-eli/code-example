package ui.filter;


import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Project: Airport
 * File: TestTypeParameterFilter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@SessionScoped
@Named
public class TestTypeParameterFilter implements Serializable {
    private int testTypeId = -1;

    public int getTestTypeId() {
        return testTypeId;
    }

    public void setTestTypeId(int testTypeId) {
        this.testTypeId = testTypeId;
    }

    public void clear() {
    }
}
