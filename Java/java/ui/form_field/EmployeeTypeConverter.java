package ui.form_field;

import db.entity.EmployeeType;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Project: Airport
 * File: EmployeeTypeConverter.java
 * Authors: Jakub Pelikán (xpelik14),
 *          Zbyněk Moravec (xmorav27),
 *          Petr Vizina (xvizin00)
 */
@ManagedBean
@ApplicationScoped
@Named
public class EmployeeTypeConverter {

    public EmployeeType[] getTypes() {
        return EmployeeType.values();
    }

}
